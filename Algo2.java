import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Algo2 {
	private HashMap<String, ArrayList<Row>> DB;
	private ArrayList<Row> MISS;
	private Set<Row> res;
	
	public Algo2(String pathDB, String pathMiss, int NumOfWifis, String PathOut) {
		IOcsv readDB = new IOcsv(pathDB);
		this.DB = new HashMap<String, ArrayList<Row>>();
		String headersFile = readDB.readCsvLine();
		String[] sRow;
		String nextRow = readDB.readCsvLine();
		while (nextRow != null) {
			System.out.println(nextRow);
			sRow = nextRow.split(",");
			int countWifi = Integer.parseInt(sRow[5]);
			Row r = new Row();
			for (int i = 0; i < countWifi; i++) {
				WiFi w = new WiFi(sRow[1], sRow[7 + (4 * i)], sRow[6 + (4 * i)], sRow[8 + (4 * i)], sRow[3], sRow[2],
						sRow[4], sRow[9 + (4 * i)], sRow[0]);
				r.add(w);
			}
			for (int i = 0; i < r.size(); i++) {
				boolean flag = false;
				for (String mac : DB.keySet()) { // runs over the keys by mac
					if (r.getWiFi(i).getMac().equals(mac)) {
						DB.get(mac).add(r);
						flag = true;
					}
					if (!flag) {
						ArrayList<Row> ar = new ArrayList<Row>();
						ar.add(r);
						DB.put(mac, ar);
					}
				}
			}
			nextRow = readDB.readCsvLine();
		}
		readDB.close();
		
		//reading pathMiss file
		IOcsv readMISS = new IOcsv(pathMiss);
		this.MISS = new ArrayList<Row>();
		nextRow = readMISS.readCsvLine();
		while (nextRow != null) {
			System.out.println(nextRow);
			sRow = nextRow.split(",");
			int countWifi = Integer.parseInt(sRow[5]);
			Row r = new Row();
			for (int i = 0; i < countWifi; i++) {
				WiFi w = new WiFi(sRow[1], sRow[7 + (4 * i)], sRow[6 + (4 * i)], sRow[8 + (4 * i)], 
						 sRow[9 + (4 * i)], sRow[0]);
				r.add(w);
			}
			this.MISS.add(r);
			nextRow = readMISS.readCsvLine();
		}
		readMISS.close();
		
	}
	
	

}
