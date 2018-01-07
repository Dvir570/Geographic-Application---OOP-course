package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AlgorithmI {

	private AvgMacPoint avgMacPoint;
	private HashMap<String, ArrayList<WiFi>> DB;

	public AlgorithmI(Set<Row> DB, String mac) {
		ArrayList<Row> temp = new ArrayList<Row>(DB);
		ArrayList<WiFi> wifis = new ArrayList<WiFi>();
		for (int i = 0; i < temp.size(); i++) {
			wifis.addAll(temp.get(i).getWifis());
		}
		ArrayList<WiFi> commonMac = new ArrayList<WiFi>();
		for (int i = 0; i < wifis.size(); i++) {
			if (wifis.get(i).getMac().equals(mac))
				commonMac.add(wifis.get(i));
		}
		if (commonMac.isEmpty())
			this.avgMacPoint = null;
		else
			this.avgMacPoint = new AvgMacPoint(commonMac);
	}

	public AvgMacPoint getAvgMacPoint() {
		return avgMacPoint;
	}

}
