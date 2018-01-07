package src;

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

	public void directoryRegister(String Path) throws IOException {
		File f = new File(Path);
		if(f.isDirectory()) {
			ArrayList<File> files = getCSVs(f);
			for(int i =0;i<files.size();i++) {
				synchronized (paths) {
					this.paths.add(files.get(i).getPath());
				}
				Path file = Paths.get(files.get(i).getPath());
				WatchKey watchKey = file.register(watchServise, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
				synchronized (keys) {
					keys.put(watchKey, file);
				}
			}
		}else {
			synchronized (paths) {
				this.paths.add(f.getPath());
			}
			Path file = Paths.get(f.getPath());
			WatchKey watchKey = file.register(watchServise, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			synchronized (keys) {
				keys.put(watchKey, file);
			}
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
