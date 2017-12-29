package src;

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
	
	public Algo2(String pathDB, String pathMiss, int numOfDatas, String pathOut) {
		this.pathOut = pathOut;
		this.res = new HashSet<Row>();
		this.avgPoints = new ArrayList<AvgSamplePoint>();
		IOfiles readDB = new IOfiles(pathDB);
		this.DB = new HashMap<String, ArrayList<Row>>();
		String headersFile = readDB.readLine();
		String[] sRow;
		String nextRow = readDB.readLine();
		while (!(nextRow!=null && nextRow.equals(""))) {
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
				}
				if (!flag) {
					ArrayList<Row> ar = new ArrayList<Row>();
					ar.add(r);
					DB.put(r.getWiFi(i).getMac(), ar);
				}
			}
			nextRow = readDB.readLine();
		}
		readDB.close();
		
		//reading pathMiss file
		IOfiles readMISS = new IOfiles(pathMiss);
		this.MISS = new ArrayList<Row>();
		nextRow = readMISS.readLine();
		while (nextRow != null) {
			sRow = nextRow.split(",");
			int countWifi = Integer.parseInt(sRow[5]);
			Row r = new Row();
			for (int i = 0; i < countWifi; i++) {
				WiFi w = new WiFi(sRow[1], sRow[7 + (4 * i)], sRow[6 + (4 * i)], sRow[8 + (4 * i)], 
						 sRow[9 + (4 * i)], sRow[0]);
				r.add(w);
			}
			this.MISS.add(r);
			nextRow = readMISS.readLine();
		}
		readMISS.close();
		
		ArrayList<Row> temp;
		ArrayList<Data> datas;
		for (int i=0;i<MISS.size();i++) {
			for(int j = 0;j<MISS.get(i).size();j++) {
				if(DB.get(MISS.get(i).getWiFi(j).getMac()) != null) {
					res.addAll(DB.get(MISS.get(i).getWiFi(j).getMac()));
				}
			}
			temp = new ArrayList<Row>();
			temp.addAll(res);
			HashMap<String, WiFi> row = new HashMap<String, WiFi>();
			datas=new ArrayList<Data>();
			for(int k = 0;k<temp.size();k++) {
				for(int l = 0; l<temp.get(k).size();l++)
					row.put(temp.get(k).getWiFi(l).getMac(), temp.get(k).getWiFi(l));
				datas.add(new Data(row,MISS.get(i).getRow()));
			}
			//if(!datas.isEmpty()) //with NAN or without?
				avgPoints.add(new AvgSamplePoint(datas, numOfDatas, MISS.get(i).getRow()));
			res.clear();
		}
		System.out.println("the count of avgPoints is: "+avgPoints.size());
	}
	
	public void writeCsv() {
		IOfiles writeFinalAlgo2 = new IOfiles(pathOut);
		for(int i =0;i<avgPoints.size();i++) {
			writeFinalAlgo2.writeLine(avgPoints.get(i).toString());
		}
		writeFinalAlgo2.close();
	}

}
