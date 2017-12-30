package GUI;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;

import src.IOfiles;

class UpdateByWiggleServer {

	

	public static void wiggleUpdate(HttpExchange request) throws IOException {
		String input = request.getRequestURI().getQuery();
    	String output = null;
    	File csvF = new File(input);
    	if(!input.toLowerCase().contains(".csv") || !csvF.exists()) {
    		output = !csvF.exists() ? "This file doesn't exist! try again":"This file isn't a csv file! please try again.";
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
    	System.out.println("The input is: "+input);
    	IOfiles insertToDB = new IOfiles("output files\\result.csv");
    	IOfiles readFromUserDB = new IOfiles(csvF.getPath());
    	File DB = new File("output files\\result.csv");
    	if(DB.exists())
    		readFromUserDB.readLine(); //read headers
    	String newLine = readFromUserDB.readLine();
    	while(newLine != null) {
    		insertToDB.writeLine(newLine, DB.exists());
    		newLine = readFromUserDB.readLine();
    	}
    	insertToDB.close();
    	readFromUserDB.close();
//    	ArrayList<File> csvFiles = new ArrayList<File>();
//    	csvFiles.add(csvF);
//    	Row allWifis = new Row(csvFiles);
//    	ResultFile result = new ResultFile();
//    	result.insertRows(allWifis.getRow());
		output="The csv file has updated successfully in your DB";
    	System.out.println("   The output is: "+output);
    	

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

}
