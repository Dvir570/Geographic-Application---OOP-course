
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Algo2 {
	private HashMap<String, ArrayList<Row>> DB;
	private ArrayList<Row> MISS;
	private Set<Row> res;
	private ArrayList<AvgSamplePoint> avgPoints;
	private String pathOut;
	
	public Algo2(String pathDB, String pathMiss, int numOfWifis, String pathOut) {
		this.pathOut = pathOut;
		this.res = new HashSet<Row>();
		this.avgPoints = new ArrayList<AvgSamplePoint>();
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
		
		ArrayList<Row> temp;
		ArrayList<Data> datas;
		for (int i=0;i<MISS.size();i++) {
			for(int j = 0;j<MISS.get(i).size();j++) {
				if(DB.get(MISS.get(i).getWiFi(j).getMac()) != null) {
					res.addAll(DB.get(MISS.get(i)));
				}
			}
			temp = new ArrayList<Row>();
			temp.addAll(res);
			datas=new ArrayList<Data>();
			for(int k = 0;k<temp.size();k++) {
				datas.add(new Data(temp.get(k).getRow(),MISS.get(i).getRow()));
			}
			if(!datas.isEmpty())
				avgPoints.add(new AvgSamplePoint(datas, 3, MISS.get(i).getRow()));
			res.clear();
		}
		System.out.println("avgpoints size: "+avgPoints.size());
	}
	
	public void writeCsv() {
		IOcsv writeFinalAlgo2 = new IOcsv(pathOut);
		for(int i =0;i<avgPoints.size();i++) {
			writeFinalAlgo2.writeCsvLine(avgPoints.get(i).toString());
		}
		writeFinalAlgo2.close();
	}

}
