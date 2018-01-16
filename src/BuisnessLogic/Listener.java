package src.BuisnessLogic;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.*;

public class Listener {
	private WatchService watchServise;
	private Map<WatchKey, Path> keys;
	private ArrayList<String> paths;

	public Listener() throws IOException {
		this.watchServise = FileSystems.getDefault().newWatchService();
		this.keys = new ConcurrentHashMap<>();
		paths = new ArrayList<String>();
	}

	public void startListening() throws IOException {
		ExecutorService service = Executors.newCachedThreadPool();

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

							synchronized (paths) {
								if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
									File f = event.context().toAbsolutePath().toFile();
									if(f.isDirectory())
										paths.remove(path.toFile().getPath());
								}
								Database.resetDatabase(paths);
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
		synchronized (paths) {
			this.paths.add(directoryPath);
		}
		Path directory = Paths.get(directoryPath);
		WatchKey watchKey = directory.register(watchServise, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		synchronized (keys) {
			keys.put(watchKey, directory);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	public void close() throws IOException {
		this.watchServise.close();
	}

	private static ArrayList<File> getCSVs(File folder) {
		ArrayList<File> ans = new ArrayList<File>();

		for (File currentF : folder.listFiles()) {
			if (currentF.isDirectory())
				ans.addAll(getCSVs(currentF));
			else if (currentF.getName().contains(".csv"))
				ans.add(currentF);
		}

		return ans;
	}

}
