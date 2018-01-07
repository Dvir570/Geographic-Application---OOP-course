package GUI;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import src.AlgorithmII;
import src.AvgSamplePoint;

public class AlgorithmIIServer {

	public static AvgSamplePoint algo2(HttpExchange request) throws IOException {
		String input = request.getRequestURI().getQuery();
		String[] inputArray = input.split("%");
		AlgorithmII alg;
		if (inputArray[0].equals("S")) {
			alg= new AlgorithmII(Server.dataBase, inputArray[1]);
		}
		else {
			String[] macsSignals= inputArray[1].split(",");
			alg= new AlgorithmII(Server.dataBase, macsSignals);
		}
		return alg.getAvgPoint();
	}
	
}
