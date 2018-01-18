package BuisnessLogic;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener {
	private WatchService watchServise;
	private Map<WatchKey, Path> keys;
	private ArrayList<String> paths;
	private ArrayList<RemoteTable> remoteTables;
	Object locker;

	public Listener() throws IOException {
		this.locker = new Object();
		this.watchServise = FileSystems.getDefault().newWatchService();
		this.keys = new ConcurrentHashMap<>();
		this.remoteTables = new ArrayList<RemoteTable>();
		paths = new ArrayList<String>();
	}

	public void startListening() throws IOException {
		ExecutorService service = Executors.newCachedThreadPool();

		service.submit(new Runnable() {
			@Override
			public void run() {
				while (Thread.interrupted() == false) {
					try {
						for (int i = 0; i<remoteTables.size();i++) {
							Statement st = remoteTables.get(i).getConnection().createStatement();
							ResultSet newRs = st.executeQuery(
									"SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = '"+remoteTables.get(i).getDatabaseName()+"' AND TABLE_NAME = '"+remoteTables.get(i).getTableName()+"'");
							if (newRs.next()) {
								if (!newRs.getString(1).equals(remoteTables.get(i).getLastUpdate())) {
									remoteTables.get(i).setLastUpdate(newRs.getString(1));
									synchronized (locker) {
										Database.resetDatabase(paths, remoteTables);
									}
								}
							}
						}
					} catch (SQLException e) {
						break;
					}

				}
			}
		});

		service.submit(new Runnable() {
			@Override
			public void run() {
				while (Thread.interrupted() == false) {
					WatchKey key;
					try {
						key = watchServise.poll(10, TimeUnit.MILLISECONDS);
					} catch (InterruptedException | ClosedWatchServiceException e) {
						break;
					}
					if (key != null) {
						Path path;
						synchronized (keys) {
							path = keys.get(key);
						}
						for (WatchEvent<?> i : key.pollEvents()) {
							WatchEvent<Path> event = cast(i);
							WatchEvent.Kind<Path> kind = event.kind();
							System.out.println(event.context());

							synchronized (locker) {
								if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
									File f = event.context().toAbsolutePath().toFile();
									if (f.isDirectory())
										paths.remove(path.toFile().getPath());
								}
								Database.resetDatabase(paths, remoteTables);
							}
						}
						if (key.reset() == false) {
							synchronized (keys) {
								keys.remove(key);
								if (keys.isEmpty())
									break;
							}
						}
					}
				}
			}
		});
	}

	public void directoryRegister(String directoryPath) throws IOException {
		synchronized (locker) {
			this.paths.add(directoryPath);
		}
		Path directory = Paths.get(directoryPath);
		WatchKey watchKey = directory.register(watchServise, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		synchronized (keys) {
			keys.put(watchKey, directory);
		}
	}

	public boolean sqlRegister(String url, String user, String password, String database, String table) throws IOException {
		RemoteTable rt = new RemoteTable(url, user, password, database, table);
		try {
			rt.setConnection(DriverManager.getConnection(url, user, password));
			Statement st = rt.getConnection().createStatement();
			st.setQueryTimeout(5);
			ResultSet rs = st.executeQuery(
					"SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = '"+database+"' AND TABLE_NAME = '"+table+"'");
			if (rs.next()) {
				rt.setLastUpdate(rs.getString(1));
				System.out.println("**** Update: " + rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		synchronized (locker) {
			remoteTables.add(rt);
		}
		
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(new Runnable() {
			@Override
			public void run() {
				rt.importRemoteTable();
			}
		});
		return true;
	}

	@SuppressWarnings("unchecked")
	private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	public void close() throws IOException {
		this.watchServise.close();
	}
}
