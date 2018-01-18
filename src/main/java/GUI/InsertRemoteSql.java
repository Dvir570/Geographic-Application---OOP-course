package GUI;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;

import BuisnessLogic.IOfiles;

public class InsertRemoteSql {
	public static void insertConnection(HttpExchange request) throws IOException {
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(new Runnable() {
			@Override
			public void run() {
				String input = request.getRequestURI().getQuery();
				String output = "";
				String[] inputArray = new String[3];
				inputArray = input.split("%");
				if (inputArray[1].equals("") || inputArray[1].equals("") || inputArray[2].equals(""))
					output = "Empty fields";
				else
					try {
						if (Server.listener.sqlRegister(inputArray[0], inputArray[1], inputArray[2]))
							output = "Connection success";
						else
							output = "Connection faild";
					} catch (IOException e) {
						output = "Connection faild";
					}
				request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
				request.getResponseHeaders().set("Content-Type", "text/plain");
				try {
					request.sendResponseHeaders(200 /* OK */, 0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try (OutputStream os = request.getResponseBody()) {
					os.write(output.getBytes());
				} catch (Exception ex) {
					System.out.println("Error while sending response to client");
					ex.printStackTrace();
				}
			}
		});
	}
}
