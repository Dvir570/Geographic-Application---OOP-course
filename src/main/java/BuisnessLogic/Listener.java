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
	private Map<Connection, String> remoteDatabases;
	Object locker;

	public Listener() throws IOException {
		this.locker = new Object();
		this.watchServise = FileSystems.getDefault().newWatchService();
		this.keys = new ConcurrentHashMap<>();
		this.remoteDatabases = new ConcurrentHashMap<>();
		paths = new ArrayList<String>();
	}

	public void startListening() throws IOException {
		ExecutorService service = Executors.newCachedThreadPool();

		service.submit(new Runnable() {
			@Override
			public void run() {
				while (Thread.interrupted() == false) {
					try {
						for (Connection con : remoteDatabases.keySet()) {
							Statement st = con.createStatement();
							ResultSet newRs = st.executeQuery(
									"SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'oop_course_ariel' AND TABLE_NAME = 'ex4_db'");
							if (newRs.next()) {
								if (!newRs.getString(1).equals(remoteDatabases.get(con))) {
									remoteDatabases.replace(con, remoteDatabases.get(con), newRs.getString(1));
									synchronized (locker) {
										Database.resetDatabase(paths, remoteDatabases);
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
								Database.resetDatabase(paths, remoteDatabases);
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

	public boolean sqlRegister(String url, String user, String password) throws IOException {

		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement st = con.createStatement();
			st.setQueryTimeout(5);
			ResultSet rs = st.executeQuery(
					"SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'oop_course_ariel' AND TABLE_NAME = 'ex4_db'");
			if (rs.next()) {
				synchronized (locker) {
					remoteDatabases.put(con, rs.getString(1));
				}
				System.out.println("**** Update: " + rs.getString(1));
			}
			Sql.test_ex4_db(con);
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Sql.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return false;

		}
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
