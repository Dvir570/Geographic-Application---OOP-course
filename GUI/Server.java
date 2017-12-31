package GUI;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.io.File;
import com.sun.net.httpserver.HttpServer;

import src.*;

/**
 * A web-server that reverses strings. Uses com.sun.net.httpserver package.
 * 
 * @see https://stackoverflow.com/a/3732328/827927
 */
public class Server {
	
	ArrayList<Row> dataBase = new ArrayList<Row>();
	public static void main(String[] args) throws Exception {
		int port = 8001;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		// select a folder
		server.createContext("/DBupdate", request -> {
			UpdateByDbServer.dbUpdate(request);
		});
		server.createContext("/home", request -> {
			UpdateByDbServer.home(request);
		});

		server.createContext("/WiggleUpdate", request -> {
			UpdateByWiggleServer.wiggleUpdate(request);
		});
		server.createContext("/DBclear", request -> {
			IOfiles DB=new IOfiles("output files\\result.csv");
			DB.write("", false);
			DB.close();
			String output = "DB cleared successfully";
			request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
			request.getResponseHeaders().set("Content-Type", "text/plain");
			request.sendResponseHeaders(200 /* OK */, 0);
			try (OutputStream os = request.getResponseBody()) {
				os.write(output.getBytes());
			} catch (Exception ex) {
				System.out.println("Error while sending response to client");
				ex.printStackTrace();
			}
		});
		System.out
				.println("WebServer is up. " + "To enter the web, go to http://127.0.0.1:" + port + "/home/updateDBbyDB.html");
		server.start();

	}
}