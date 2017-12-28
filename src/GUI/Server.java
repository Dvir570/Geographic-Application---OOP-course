package GUI;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import com.sun.net.httpserver.HttpServer;

/**
 * A web-server that reverses strings. Uses com.sun.net.httpserver package.
 * @see https://stackoverflow.com/a/3732328/827927
 */
public class Server {
    public static void main(String[] args) throws Exception {
    	int port = 8001;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/reverse", request -> {
        	String input = request.getRequestURI().getQuery();
        	System.out.println("The input is: "+input);
        	String output = new StringBuilder(input).reverse().toString(); 
        	System.out.println("   The output is: "+output);
        	
        	request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        	request.getResponseHeaders().set("Content-Type", "text/plain");
            request.sendResponseHeaders(200 /* OK */, 0);
            try (OutputStream os = request.getResponseBody()) {
            	os.write(output.getBytes());
            }
        });
        
        server.createContext("/file", request -> {
        	String fileName = request.getRequestURI().getPath().replaceAll("/file/", "");
        	System.out.println("Got new file-request: "+fileName);
        	Path path = Paths.get("client", "lesson5", fileName);
        	String output = null;
        	try {
        		output = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        	} catch (Exception ex) {
        		output = "Error: "+ex;
        	}
        	
        	request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        	request.getResponseHeaders().set("Content-Type", "text/html");
            request.sendResponseHeaders(200, 0);
            try (OutputStream os = request.getResponseBody()) {
            	os.write(output.getBytes());
            }
        });
        System.out.println("ReversingServer is up. "+
        		"To reverse the string abc, go to http://127.0.0.1:"+port+"/reverse?dvir");
        server.start();
    }
}