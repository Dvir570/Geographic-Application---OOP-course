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

import src.*;

class UpdateByWiggleServer {

	/**
	 * @return ArrayList of all .csv files in folder param.
	 */
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

	public static void wiggleUpdate(HttpExchange request) throws IOException {
		String input = request.getRequestURI().getQuery();
		String output = null;
		File csvF = new File(input);
		if ((!input.toLowerCase().contains(".csv") && !csvF.isDirectory()) || !csvF.exists()) {
			output = !csvF.exists() ? "This file doesn't exist! try again"
					: "This file isn't a directory or a csv file! please try again.";
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
		System.out.println("The input is: " + input);
		ArrayList<File> csvFiles = new ArrayList<File>();
		if (csvF.isDirectory())
			csvFiles = getCSVs(csvF);
		else
			csvFiles.add(csvF);
		Row allWifis = new Row(csvFiles);
		ResultFile result = new ResultFile("output files\\result.csv");
		//result.insertRows(allWifis.getRow());
		result.rowsGroupByTimeModel(allWifis.getRow());
		result.top10fromAnyGroup();
		Server.dataBase.addAll(ResultFile.result);
		output = "The csv file has been added successfully in server";
		System.out.println("   The output is: " + output);

		request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		request.getResponseHeaders().set("Content-Type", "text/plain");
		request.sendResponseHeaders(200 /* OK */, 0);
		try (OutputStream os = request.getResponseBody()) {
			os.write(output.getBytes());
			os.close();
		} catch (Exception ex) {
			System.out.println("Error while sending response to client");
			ex.printStackTrace();
		}
	}

}
