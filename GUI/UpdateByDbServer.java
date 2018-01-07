package GUI;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;

import src.Database;
import src.IOfiles;
import src.Row;
import src.WiFi;

public class UpdateByDbServer {
	public static void dbUpdate(HttpExchange request) throws IOException {
		String input = request.getRequestURI().getQuery();
		String output = null;
		File csvF = new File(input);
		if (!input.toLowerCase().contains(".csv") || !csvF.exists()) {
			output = !csvF.exists() ? "This file doesn't exist! try again"
					: "This file isn't a csv file! please try again.";
			System.out.println(output);
			request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
			request.getResponseHeaders().set("Content-Type", "text/plain");
			request.sendResponseHeaders(200, 0);
			try (OutputStream os = request.getResponseBody()) {
				os.write(output.getBytes(StandardCharsets.UTF_8));
			} catch (Exception ex) {
				System.out.println("Cannot send response to client");
				ex.printStackTrace();
			}
			return;
		}
		Server.listener.directoryRegister(input);
		System.out.println("The input is: " + input);
		IOfiles readFromUserDB = new IOfiles(csvF.getPath());

		readFromUserDB.readLine();
		String nextRow = readFromUserDB.readLine();
		String[] sRow;
		while (nextRow != null && !nextRow.equals("")) {
			sRow = nextRow.split(",");
			int countWifi = Integer.parseInt(sRow[5]);
			Row r = new Row();
			for (int i = 0; i < countWifi; i++) {
				WiFi w = new WiFi(sRow[1], sRow[7 + (4 * i)], sRow[6 + (4 * i)], sRow[8 + (4 * i)], sRow[3], sRow[2],
						sRow[4], sRow[9 + (4 * i)], sRow[0]);
				r.add(w);
			}
			Database.database.add(r);
			nextRow = readFromUserDB.readLine();
		}
		readFromUserDB.close();
		output = "The csv file has been added successfully in server";
		System.out.println("   The output is: " + output);

		request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		request.getResponseHeaders().set("Content-Type", "text/plain");
		request.sendResponseHeaders(200 /* OK */, 0);
		try (OutputStream os = request.getResponseBody()) {
			os.write(output.getBytes());
		} catch (Exception ex) {
			System.out.println("Error while sending response to client");
			ex.printStackTrace();
		}
	}

	public static void home(HttpExchange request) throws IOException {
		String output = null;

		try {
			String fileName = request.getRequestURI().getPath().replaceAll("/home/", "");
			System.out.println("Got new file-request: " + fileName);
			Path path = Paths.get("output files", "GUI", fileName);
			if (Files.exists(path)) {
				String contentType = (fileName.endsWith(".html") ? "text/html"
						: fileName.endsWith(".js") ? "text/javascript"
								: fileName.endsWith(".css") ? "text/css" : "text/plain");
				request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
				request.getResponseHeaders().set("Content-Type", contentType);
				request.sendResponseHeaders(200, 0);
				try (OutputStream os = request.getResponseBody()) {
					os.write(Files.readAllBytes(path));
				}
				return;
			} else {
				output = "File " + path + " not found!";
			}
		} catch (Throwable ex) {
			output = "Sorry, an error occured: " + ex;
		}
		System.out.println(output);
		request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		request.getResponseHeaders().set("Content-Type", "text/plain");
		request.sendResponseHeaders(200, 0);
		try (OutputStream os = request.getResponseBody()) {
			os.write(output.getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			System.out.println("Cannot send response to client");
			ex.printStackTrace();
		}
	}

}
