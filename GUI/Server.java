package GUI;


import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import com.sun.net.httpserver.HttpServer;

import src.IOfiles;

/**
 * A web-server that reverses strings. Uses com.sun.net.httpserver package.
 * @see https://stackoverflow.com/a/3732328/827927
 */
public class Server {
    public static void main(String[] args) throws Exception {
    	int port = 8001;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
//        server.createContext("/home", request -> {
//        	String fileName = request.getRequestURI().getPath().replaceAll("/file/", "");
//        	String output = "";
//        	//IOfiles homeCSS = new IOfiles("output files//GUI//assets//css//main.css");
//        	//String line = homeCSS.readLine();
////        	while(line!=null) {
////        		output+=line;
////        		line=homeCSS.readLine();
////        	}
////        	homeCSS.close();
//        	
//        	IOfiles homeHTML = new IOfiles("output files//GUI//index.html");
//        	String line = homeHTML.readLine();
//        	while(line!=null) {
//        		output+=line;
//        		line=homeHTML.readLine();
//        	}
//        	homeHTML.close();
//        	//String output = new StringBuilder(input).reverse().toString(); 
//        	System.out.println("   The output is: "+output);
//        	String contentType = (
//
//            		fileName.endsWith(".html")? "text/html":
//
//               		fileName.endsWith(".js")? "text/javascript":
//
//                   	fileName.endsWith(".css")? "text/css":
//
//                   	"text/plain"
//
//            		);
//        	request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
//        	request.getResponseHeaders().set("Content-Type",contentType );
//        	
//            request.sendResponseHeaders(200 /* OK */, 0);
//            try (OutputStream os = request.getResponseBody()) {
//            	os.write(output.getBytes());
//            }
//        });
//        
        server.createContext("/file", request -> {
			String output = null;

			try {
				String fileName = request.getRequestURI().getPath().replaceAll("/file/", "");
				System.out.println("Got new file-request: "+fileName);
				Path path = Paths.get("output files","GUI", fileName);
				if (Files.exists(path)) {
					String contentType = (
							fileName.endsWith(".html")? "text/html":
								fileName.endsWith(".js")? "text/javascript":
									fileName.endsWith(".css")? "text/css":
										"text/plain"
							);
					request.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
					request.getResponseHeaders().set("Content-Type", contentType);
					request.sendResponseHeaders(200, 0);
					try (OutputStream os = request.getResponseBody()) {
						os.write(Files.readAllBytes(path));
					}
					return;
				} else {
					output = "File "+path+" not found!";
				}
			} catch (Throwable ex) {
				output = "Sorry, an error occured: "+ex;
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
		});

                	
        	
        System.out.println("WebServer is up. "+
        		"To enter the web, go to http://127.0.0.1:"+port+"/file/index.html");
        server.start();
		
	}
}