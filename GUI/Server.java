package GUI;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import src.*;

/**
 * A web-server that reverses strings. Uses com.sun.net.httpserver package.
 * 
 * @see https://stackoverflow.com/a/3732328/827927
 */
public class Server {

	public static Set<Row> dataBase = new HashSet<Row>();
	private static Set<Row> backUpDataBase = new HashSet<Row>();

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
		server.createContext("/saveFilter", request -> {
			String input = request.getRequestURI().getQuery();
			String output = "";
			String[] inputArray = new String[2];
			inputArray = input.split("%");
			File filterPath = new File(inputArray[0]);

			if (filterPath.exists())
				output = "File already exists";
			else if (!filterPath.getPath().contains(".txt"))
				output = "Bad File format";
			else {
				IOfiles writer = new IOfiles(filterPath.getPath());
				writer.writeLine(inputArray[1]);
				writer.close();
				output = "Filter has been recorded at " + filterPath.getPath();
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

		server.createContext("/uploadFilter", request -> {
			String input = request.getRequestURI().getQuery();
			String output;
			File filePath = new File(input);
			if (!filePath.getPath().contains(".txt"))
				output = "Bad file format";
			else if (filePath.exists()) {
				IOfiles reader = new IOfiles(filePath.getPath());
				output = "Filter has been uploaded succesfully" + "%" + reader.readLine();;
			} else
				output = "File doesnt exists";

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
			backUpDataBase.addAll(dataBase);
			String output = "";
			filterDB(request);
			output = "Database filterred succsesfully";
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
		server.createContext("/restoreDB", request -> {
			dataBase.clear();
			dataBase.addAll(backUpDataBase);
			String output = "Database resorred succsesfully ";
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
				"WebServer is up. " + "To enter the web, go to http://127.0.0.1:" + port + "/home/updateDBbyWiggle.html");
		server.start();

	}

	private static void filterDB(HttpExchange request) {
		String filter = request.getRequestURI().getQuery();
		boolean time1;
		boolean id1;
		boolean pos1;
		boolean time2 = false;
		boolean id2 = false;
		boolean pos2 = false;
		String[] temp = new String[] { filter };
		if (filter.contains("&&") || filter.contains("||")) {

			if (filter.contains("&&"))
				temp = filter.split("&&");
			else {
				temp = filter.replace('|', '%').split("%%");
				filter = filter.replace('|', '%');
			}
			time2 = temp[1].contains("Time(");
			id2 = temp[1].contains("ID(");
			pos2 = temp[1].contains("Pos(");
		}
		String[] filterArray = new String[2];
		time1 = temp[0].contains("Time(");
		id1 = temp[0].contains("ID(");
		pos1 = temp[0].contains("Pos(");

		if (time1 && id2) {
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
			} else if (filter.contains("%%")) {
				filterArray = filter.split("%%");
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
		} else if (time1 && time2) {
			if (filter.contains("&&")) {
				filterArray = filter.split("&&");
				int openT1 = filterArray[0].lastIndexOf("(");
				int closeT1 = filterArray[0].indexOf(")");
				boolean notT1 = filterArray[0].contains("!");
				int openT2 = filterArray[1].lastIndexOf("(");
				int closeT2 = filterArray[1].indexOf(")");
				boolean notT2 = filterArray[1].contains("!");
				filterArray[0] = filterArray[0].substring(openT1 + 1, closeT1);
				filterArray[1] = filterArray[1].substring(openT2 + 1, closeT2);
				String[] date1Array = new String[2];
				String[] date2Array = new String[2];
				date1Array = filterArray[0].split(",");
				date2Array = filterArray[0].split(",");
				ArrayList<Row> db = new ArrayList<Row>();
				db.addAll(dataBase);
				db = DisplayMap.displayByTime(db, notT1, date1Array[0], date1Array[1]);
				db = DisplayMap.displayByTime(db, notT2, date2Array[0], date2Array[1]);
				dataBase.clear();
				dataBase.addAll(db);
			} else if (filter.contains("%%")) {
				filterArray = filter.split("%%");
				int openT1 = filterArray[0].lastIndexOf("(");
				int closeT1 = filterArray[0].indexOf(")");
				boolean notT1 = filterArray[0].contains("!");
				int openT2 = filterArray[1].lastIndexOf("(");
				int closeT2 = filterArray[1].indexOf(")");
				boolean notT2 = filterArray[1].contains("!");
				filterArray[0] = filterArray[0].substring(openT1 + 1, closeT1);
				filterArray[1] = filterArray[1].substring(openT2 + 1, closeT2);
				String[] date1Array = new String[2];
				String[] date2Array = new String[2];
				date1Array = filterArray[0].split(",");
				date2Array = filterArray[0].split(",");
				ArrayList<Row> db1 = new ArrayList<Row>();
				db1.addAll(dataBase);
				ArrayList<Row> db2 = new ArrayList<Row>();
				db2.addAll(dataBase);
				db1 = DisplayMap.displayByTime(db1, notT1, date1Array[0], date1Array[1]);
				db2 = DisplayMap.displayByTime(db1, notT2, date2Array[0], date2Array[1]);
				dataBase.clear();
				dataBase.addAll(db1);
				dataBase.addAll(db2);
			}

		}

		else if (time1 && pos2) {
			if (filter.contains("&&")) {
				filterArray = filter.split("&&");
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
				String[] posArray = new String[4];
				posArray = filterArray[1].split(",");
				ArrayList<Row> db = new ArrayList<Row>();
				db.addAll(dataBase);
				db = DisplayMap.displayByTime(db, notT, dateArray[0], dateArray[1]);
				db = DisplayMap.displayByPlace(db, notP, Double.parseDouble(posArray[0]),
						Double.parseDouble(posArray[1]), Double.parseDouble(posArray[2]),
						Double.parseDouble(posArray[3]));
				dataBase.clear();
				dataBase.addAll(db);
			} else if (filter.contains("%%")) {
				filterArray = filter.split("%%");
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
				String[] posArray = new String[4];
				posArray = filterArray[1].split(",");
				ArrayList<Row> db1 = new ArrayList<Row>();
				db1.addAll(dataBase);
				ArrayList<Row> db2 = new ArrayList<Row>();
				db2.addAll(dataBase);
				db1 = DisplayMap.displayByTime(db1, notT, dateArray[0], dateArray[1]);
				db2 = DisplayMap.displayByPlace(db2, notP, Double.parseDouble(posArray[0]),
						Double.parseDouble(posArray[1]), Double.parseDouble(posArray[2]),
						Double.parseDouble(posArray[3]));
				dataBase.clear();
				dataBase.addAll(db1);
				dataBase.addAll(db2);

			}
		} else if (pos1 && pos2) {
			if (filter.contains("&&")) {
				filterArray = filter.split("&&");
				int openP1 = filterArray[0].lastIndexOf("(");
				int closeP1 = filterArray[0].indexOf(")");
				boolean notP1 = filterArray[0].contains("!");
				int openP2 = filterArray[1].lastIndexOf("(");
				int closeP2 = filterArray[1].indexOf(")");
				boolean notP2 = filterArray[1].contains("!");
				filterArray[0] = filterArray[0].substring(openP1 + 1, closeP1);
				filterArray[1] = filterArray[1].substring(openP2 + 1, closeP2);
				String[] pos1Array = new String[4];
				String[] pos2Array = new String[4];
				pos1Array = filterArray[0].split(",");
				pos2Array = filterArray[1].split(",");
				ArrayList<Row> db = new ArrayList<Row>();
				db.addAll(dataBase);
				db = DisplayMap.displayByPlace(db, notP1, Double.parseDouble(pos1Array[0]),
						Double.parseDouble(pos1Array[1]), Double.parseDouble(pos1Array[2]),
						Double.parseDouble(pos1Array[3]));
				db = DisplayMap.displayByPlace(db, notP2, Double.parseDouble(pos2Array[0]),
						Double.parseDouble(pos2Array[1]), Double.parseDouble(pos2Array[2]),
						Double.parseDouble(pos2Array[3]));
				dataBase.clear();
				dataBase.addAll(db);
			} else if (filter.contains("%%")) {
				filterArray = filter.split("%%");
				int openP1 = filterArray[0].lastIndexOf("(");
				int closeP1 = filterArray[0].indexOf(")");
				boolean notP1 = filterArray[0].contains("!");
				int openP2 = filterArray[1].lastIndexOf("(");
				int closeP2 = filterArray[1].indexOf(")");
				filterArray[0] = filterArray[0].substring(openP1 + 1, closeP1);
				filterArray[1] = filterArray[1].substring(openP2 + 1, closeP2);
				String[] pos1Array = new String[4];
				String[] pos2Array = new String[4];
				pos1Array = filterArray[0].split(",");
				pos2Array = filterArray[1].split(",");
				ArrayList<Row> db1 = new ArrayList<Row>();
				ArrayList<Row> db2 = new ArrayList<Row>();
				db1.addAll(dataBase);
				db2.addAll(dataBase);
				db1 = DisplayMap.displayByPlace(db1, notP1, Double.parseDouble(pos1Array[0]),
						Double.parseDouble(pos1Array[1]), Double.parseDouble(pos1Array[2]),
						Double.parseDouble(pos1Array[3]));

				db2 = DisplayMap.displayByPlace(db2, notP1, Double.parseDouble(pos2Array[0]),
						Double.parseDouble(pos2Array[1]), Double.parseDouble(pos2Array[2]),
						Double.parseDouble(pos2Array[3]));
				dataBase.clear();
				dataBase.addAll(db1);
				dataBase.addAll(db2);
			}
		} else if (id1 && pos2) {
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
				filterArray = filter.split("%%");
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
		} else if (id1 && id2) {
			if (filter.contains("&&")) {
				filterArray = filter.split("&&");
				int openid1 = filterArray[0].lastIndexOf("(");
				int closeid1 = filterArray[0].indexOf(")");
				boolean notid1 = filterArray[0].contains("!");
				int openid2 = filterArray[1].lastIndexOf("(");
				int closeid2 = filterArray[1].indexOf(")");
				boolean notid2 = filterArray[1].contains("!");
				filterArray[0] = filterArray[0].substring(openid1 + 1, closeid1);
				filterArray[1] = filterArray[1].substring(openid2 + 1, closeid2);
				ArrayList<Row> db = new ArrayList<Row>();
				db.addAll(dataBase);
				db = DisplayMap.displayByModel(db, notid1, filterArray[0]);
				db = DisplayMap.displayByModel(db, notid2, filterArray[1]);
				dataBase.clear();
				dataBase.addAll(db);
			} else if (filter.contains("%%")) {
				filterArray = filter.split("%%");
				int openid1 = filterArray[0].lastIndexOf("(");
				int closeid1 = filterArray[0].indexOf(")");
				boolean notid1 = filterArray[0].contains("!");
				int openid2 = filterArray[1].lastIndexOf("(");
				int closeid2 = filterArray[1].indexOf(")");
				boolean notid2 = filterArray[1].contains("!");
				filterArray[0] = filterArray[0].substring(openid1 + 1, closeid1);
				filterArray[1] = filterArray[1].substring(openid2 + 1, closeid2);
				ArrayList<Row> db1 = new ArrayList<Row>();
				ArrayList<Row> db2 = new ArrayList<Row>();
				db1.addAll(dataBase);
				db2.addAll(dataBase);
				db1 = DisplayMap.displayByModel(db1, notid1, filterArray[0]);
				db2 = DisplayMap.displayByModel(db2, notid2, filterArray[1]);
				dataBase.clear();
				dataBase.addAll(db1);
				dataBase.addAll(db2);
			}
		}

		else {
			ArrayList<Row> db = new ArrayList<Row>();
			db.addAll(dataBase);
			if (filter.contains("Time")) {
				boolean notT = filter.contains("!");
				String[] timeArray = new String[2];
				timeArray = filter.split(",");
				timeArray[0] = timeArray[0].substring(timeArray[0].lastIndexOf("(") + 1, timeArray[0].length());
				timeArray[1] = timeArray[1].substring(0, timeArray[1].indexOf(")"));
				db = DisplayMap.displayByTime(db, notT, timeArray[0], timeArray[1]);
			} else if (filter.contains("ID")) {
				boolean notID = filter.contains("!");
				filter = filter.substring(filter.lastIndexOf("(") + 1, filter.indexOf(")"));
				db = DisplayMap.displayByModel(db, notID, filter);
			} else if (filter.contains("Pos")) {
				boolean notP = filter.contains("!");
				filter = filter.substring(filter.lastIndexOf("(") + 1, filter.indexOf(")"));
				String[] posArray = new String[4];
				posArray = filter.split(",");
				db = DisplayMap.displayByPlace(db, notP, Double.parseDouble(posArray[0]),
						Double.parseDouble(posArray[1]), Double.parseDouble(posArray[2]),
						Double.parseDouble(posArray[3]));
			}
			dataBase.clear();
			dataBase.addAll(db);
		}
	}
}