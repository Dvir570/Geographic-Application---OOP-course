package src.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import src.BuisnessLogic.Row;
import src.BuisnessLogic.WiFi;

public class AlgorithmII {
	private HashMap<String, ArrayList<Row>> DB;
	private Row MISS;
	private Set<Row> res;
	private AvgSamplePoint avgPoint;

	public AlgorithmII(Set<Row> dbRows, String missLoc) {
		this.res = new HashSet<Row>();

		this.DB = new HashMap<String, ArrayList<Row>>();
		ArrayList<Row> temp = new ArrayList<Row>(dbRows);
		for (int j = 0; j < temp.size(); j++) { // ****
			Row r = temp.get(j);
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
		}

		this.MISS = new Row();
		String[] sRow = missLoc.split(",");
		int countWifi = Integer.parseInt(sRow[5]);
		for (int i = 0; i < countWifi; i++) {
			WiFi w = new WiFi(sRow[1], sRow[7 + (4 * i)], sRow[6 + (4 * i)], sRow[8 + (4 * i)], sRow[9 + (4 * i)],
					sRow[0]);
			MISS.add(w);
		}

		temp.clear();
		ArrayList<Data> datas;

		for (int j = 0; j < MISS.size(); j++) {
			if (DB.get(MISS.getWiFi(j).getMac()) != null) {
				res.addAll(DB.get(MISS.getWiFi(j).getMac()));
			}
		}

		temp.addAll(res);
		HashMap<String, WiFi> row = new HashMap<String, WiFi>();
		datas = new ArrayList<Data>();
		for (int k = 0; k < temp.size(); k++) {
			for (int l = 0; l < temp.get(k).size(); l++)
				row.put(temp.get(k).getWiFi(l).getMac(), temp.get(k).getWiFi(l));
			datas.add(new Data(row, MISS.getWifis()));
		}
		// if(!datas.isEmpty()) //with NAN or without?
		this.avgPoint = new AvgSamplePoint(datas, datas.size(), MISS.getWifis());
	}

	public AlgorithmII(Set<Row> dbRows, String[] macsSignals) {

		this.res = new HashSet<Row>();

		this.DB = new HashMap<String, ArrayList<Row>>();
		ArrayList<Row> temp = new ArrayList<Row>(dbRows);
		for (int j = 0; j < temp.size(); j++) { // ****
			Row r = temp.get(j);
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
		}

		this.MISS = new Row();
		for (int k = 0; k < macsSignals.length / 2; k++) {
			WiFi w = new WiFi();
			w.setMac(macsSignals[2 * k]);
			w.setSignal(macsSignals[2 * k + 1]);
			MISS.add(w);
		}

		temp.clear();
		ArrayList<Data> datas;

		for (int j = 0; j < MISS.size(); j++) {
			if (DB.get(MISS.getWiFi(j).getMac()) != null) {
				res.addAll(DB.get(MISS.getWiFi(j).getMac()));
			}
		}

		temp.addAll(res);
		HashMap<String, WiFi> row = new HashMap<String, WiFi>();
		datas = new ArrayList<Data>();
		for (int k = 0; k < temp.size(); k++) {
			for (int l = 0; l < temp.get(k).size(); l++)
				row.put(temp.get(k).getWiFi(l).getMac(), temp.get(k).getWiFi(l));
			datas.add(new Data(row, MISS.getWifis()));
		}
		// if(!datas.isEmpty()) //with NAN or without?
		this.avgPoint = new AvgSamplePoint(datas, datas.size(), MISS.getWifis());
	}

	public AvgSamplePoint getAvgPoint() {
		return avgPoint;
	}

}
