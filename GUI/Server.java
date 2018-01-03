package GUI;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import com.sun.net.httpserver.HttpServer;

import src.*;

/**
 * A web-server that reverses strings. Uses com.sun.net.httpserver package.
 * 
 * @see https://stackoverflow.com/a/3732328/827927
 */
public class Server {

	public static Set<Row> dataBase = new HashSet<Row>();

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
			dataBase.clear();
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
		server.createContext("/DBsaveCSV", request -> {
			String resPath = "output files\\result.csv";
			ResultFile result = new ResultFile(resPath);
			ResultFile.result.clear();
			ResultFile.result.addAll(dataBase);
			result.writeDB();
			result.close();
			String output;
			output = "DB saved successfully as csv at " + resPath;
			System.out.println(output);
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
		server.createContext("/DBsaveKML", request -> {
			String resPath = "output files\\result.kml";
			ArrayList<WiFi> toKml = new ArrayList<WiFi>();
			ArrayList<Row> temp = new ArrayList<Row>();
			temp.addAll(dataBase);
			for (int i = 0; i < temp.size(); i++) {
				toKml.addAll(temp.get(i).getWifis());
			}
			DisplayMap dm = new DisplayMap(toKml);
			dm.SortbyMac();
			KML.makeKML(dm.getToDisplay(), resPath);
			String output = "DB saved successfully as kml at " + resPath;
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

		server.createContext("/numOfRecords", request -> {
			String output = dataBase.size() + "";
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
		server.createContext("/numOfRouters", request -> {
			ArrayList<WiFi> wifis = new ArrayList<WiFi>();
			ArrayList<Row> temp = new ArrayList<Row>();
			temp.addAll(dataBase);
			for (int i = 0; i < temp.size(); i++) {
				wifis.addAll(temp.get(i).getWifis());
			}
			DisplayMap dm = new DisplayMap(wifis);
			dm.SortbyMac();
			String output = dm.getToDisplay().size() + "";
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
		server.createContext("/FilterBy", request -> {
			String filter = request.getRequestURI().getQuery();
			String output = "";
			boolean time;
			boolean id;
			boolean pos;
			String[] filterArray = new String[2];
			time = filter.contains("Time(");
			id = filter.contains("ID(");
			pos = filter.contains("Position(");
			ArrayList<Row> DBFinal = new ArrayList<Row>();
			if (time && id) {
				if (filter.contains("&&")) {
					filterArray = filter.split("&&");
					filterArray[0] = filterArray[0].substring(5, filterArray[0].length() - 1);
					filterArray[1] = filterArray[1].substring(3, filterArray[1].length() - 1);
					String[] dateArray = new String[2];
					dateArray = filterArray[0].split(",");
					ArrayList<Row> db = new ArrayList<Row>();
					db.addAll(dataBase);
					db = DisplayMap.displayByTime(db, dateArray[0], dateArray[1]);
					db = DisplayMap.displayByModel(db, filterArray[1]);
				} else
					filterArray = filter.split("||");
			}

			if (time && pos) {
				if (filter.contains("&&"))
					filterArray = filter.split("&&");
				else
					filterArray = filter.split("||");
			}

			if (id && pos) {
				if (filter.contains("&&"))
					filterArray = filter.split("&&");
				else
					filterArray = filter.split("||");
			}

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

		System.out.println(
				"WebServer is up. " + "To enter the web, go to http://127.0.0.1:" + port + "/home/updateDBbyDB.html");
		server.start();

	}
}