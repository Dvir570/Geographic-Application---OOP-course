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
			boolean time1;
			boolean id1;
			boolean pos1;
			boolean time2 = false;
			boolean id2 = false;
			boolean pos2 = false;
			if(filter.contains("&&") || filter.contains("||")) {
				String[] temp = filter.split("||&&");
				time2 = temp[1].contains("Time(");
				id2 = temp[1].contains("ID(");
				pos2 = temp[1].contains("Pos(");
			}
			String[] filterArray = new String[2];
			time1 = filter.contains("Time(");
			id1 = filter.contains("ID(");
			pos1 = filter.contains("Pos(");

			if (time1 && id1) {
				if (filter.contains("&&")) {
					filterArray = filter.split("&&");
					int openT = filterArray[0].lastIndexOf("(");
					int closeT = filterArray[0].indexOf(")");
					boolean notT = filterArray[0].contains("!");
					int openID = filterArray[1].lastIndexOf("(");
					int closeID = filterArray[1].indexOf(")");
					boolean notID = filterArray[1].contains("!");
					filterArray[0] = filterArray[0].substring(openT + 1, closeT);
					filterArray[1] = filterArray[1].substring(openID + 1, closeID);
					String[] dateArray = new String[2];
					dateArray = filterArray[0].split(",");
					ArrayList<Row> db = new ArrayList<Row>();
					db.addAll(dataBase);
					db = DisplayMap.displayByTime(db, notT, dateArray[0], dateArray[1]);
					db = DisplayMap.displayByModel(db, notID, filterArray[1]);
					dataBase.clear();
					dataBase.addAll(db);
				} else if (filter.contains("||")) {
					filterArray = filter.split("||");
					int openT = filterArray[0].lastIndexOf("(");
					int closeT = filterArray[0].indexOf(")");
					boolean notT = filterArray[0].contains("!");
					int openID = filterArray[1].lastIndexOf("(");
					int closeID = filterArray[1].indexOf(")");
					boolean notID = filterArray[1].contains("!");
					filterArray[0] = filterArray[0].substring(openT + 1, closeT);
					filterArray[1] = filterArray[1].substring(openID + 1, closeID);
					String[] dateArray = new String[2];
					dateArray = filterArray[0].split(",");
					ArrayList<Row> db1 = new ArrayList<Row>();
					db1.addAll(dataBase);
					ArrayList<Row> db2 = new ArrayList<Row>();
					db2.addAll(dataBase);
					db1 = DisplayMap.displayByTime(db1, notT, dateArray[0], dateArray[1]);
					db2 = DisplayMap.displayByModel(db2, notID, filterArray[1]);
					dataBase.clear();
					dataBase.addAll(db1);
					dataBase.addAll(db2);
				}	
			} 
			else if ()
			else if (time1 && pos1) {
				if (filter.contains("&&")) {
					filterArray = filter.split("&&");
					int openT = filter.lastIndexOf("(");
					int closeT = filterArray[0].indexOf(")");
					boolean notT = filterArray[0].contains("!");
					int openP = filter.lastIndexOf("(");
					int closeP = filter.indexOf(")");
					boolean notP = filterArray[0].contains("!");
					filterArray[0] = filterArray[0].substring(openT + 1, closeT);
					filterArray[1] = filterArray[1].substring(openP + 1, closeP);
					String[] dateArray = new String[2];
					ArrayList<Row> db = new ArrayList<Row>();
					db.addAll(dataBase);
					db = DisplayMap.displayByTime(db, notT, dateArray[0], dateArray[1]);
					db = DisplayMap.displayByModel(db, notP, filterArray[1]);
					dataBase.clear();
					dataBase.addAll(db);
				} else if (filter.contains("||")) {
					filterArray = filter.split("||");
					int openT = filterArray[0].lastIndexOf("(");
					int closeT = filterArray[0].indexOf(")");
					boolean notT = filterArray[0].contains("!");
					int openP = filterArray[1].lastIndexOf("(");
					int closeP = filterArray[1].indexOf(")");
					boolean notP = filterArray[1].contains("!");
					filterArray[0] = filterArray[0].substring(openT + 1, closeT);
					filterArray[1] = filterArray[1].substring(openP + 1, closeP);
					String[] dateArray = new String[2];
					dateArray = filterArray[0].split(",");
					ArrayList<Row> db1 = new ArrayList<Row>();
					db1.addAll(dataBase);
					ArrayList<Row> db2 = new ArrayList<Row>();
					db2.addAll(dataBase);
					db1 = DisplayMap.displayByTime(db1, notT, dateArray[0], dateArray[1]);
					db2 = DisplayMap.displayByModel(db2, notP, filterArray[1]);
					dataBase.clear();
					dataBase.addAll(db1);
					dataBase.addAll(db2);
				}
			}

			else if (id1 && pos1) {
				String[] posArray = new String[4];
				if (filter.contains("&&")) {
					filterArray = filter.split("&&");
					int openID = filterArray[0].lastIndexOf("(");
					int closeID = filterArray[0].indexOf(")");
					boolean notID = filterArray[0].contains("!");
					int openP = filterArray[1].lastIndexOf("(");
					int closeP = filterArray[1].indexOf(")");
					boolean notP = filterArray[1].contains("!");
					filterArray[0] = filterArray[0].substring(openID + 1, closeID);
					filterArray[1] = filterArray[1].substring(openP + 1, closeP);
					posArray = filterArray[1].split(",");
					ArrayList<Row> db = new ArrayList<Row>();
					db.addAll(dataBase);
					db = DisplayMap.displayByPlace(db, notID, Double.parseDouble(posArray[0]),
							Double.parseDouble(posArray[1]), Double.parseDouble(posArray[2]),
							Double.parseDouble(posArray[3]));
					db = DisplayMap.displayByModel(db, notP, filterArray[0]);
					dataBase.clear();
					dataBase.addAll(db);
				} else {
					filterArray = filter.split("||");
					int openID = filterArray[0].lastIndexOf("(");
					int closeID = filterArray[0].indexOf(")");
					boolean notID = filterArray[0].contains("!");
					int openP = filterArray[1].lastIndexOf("(");
					int closeP = filterArray[1].indexOf(")");
					boolean notP = filterArray[1].contains("!");
					filterArray[0] = filterArray[0].substring(openID + 1, closeID);
					filterArray[1] = filterArray[1].substring(openP + 1, closeP);
					posArray = filterArray[1].split(",");
					ArrayList<Row> db1 = new ArrayList<Row>();
					ArrayList<Row> db2 = new ArrayList<Row>();
					db1.addAll(dataBase);
					db2.addAll(dataBase);
					db1 = DisplayMap.displayByModel(db1, notID, filterArray[0]);
					db2 = DisplayMap.displayByPlace(db2, notP, Double.parseDouble(posArray[0]),
							Double.parseDouble(posArray[1]), Double.parseDouble(posArray[2]),
							Double.parseDouble(posArray[3]));
					dataBase.clear();
					dataBase.addAll(db1);
					dataBase.addAll(db2);
				}
			}
			/*
			 * else { ArrayList<Row> db = new ArrayList<Row>(); db.addAll(dataBase); if
			 * (filter.contains("Time")) { String[] timeArray =new String[2];
			 * timeArray=filter.split(","); timeArray[0]=timeArray[0].substring(5,
			 * timeArray[0].length());
			 * timeArray[1]=timeArray[1].substring(0,timeArray[1].length()-1);
			 * db=DisplayMap.displayByTime(db, timeArray[0],timeArray[1]); } if
			 * (filter.contains("ID")) { filter.substring(3, filter.length()-1);
			 * db=DisplayMap.displayByModel(db,filter); } dataBase.addAll(db); }
			 */

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