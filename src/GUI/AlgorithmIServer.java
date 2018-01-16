package src.GUI;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import src.Algorithms.AlgorithmI;
import src.Algorithms.AvgMacPoint;
import src.BuisnessLogic.Database;

public class AlgorithmIServer {
	
	public static AvgMacPoint algo1(HttpExchange request) throws IOException {
		String mac = request.getRequestURI().getQuery();
		AlgorithmI alg =new AlgorithmI(Database.database,mac);
		return alg.getAvgMacPoint();
	}
}
