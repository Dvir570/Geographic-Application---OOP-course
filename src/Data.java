package src;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * present part of line in the DB(CSVS) for algo2 to calculate the value pi 
 */
public class Data {


	private static final int POWER = 2;
	private static final double SIG_DIFF = 0.4;
	private static final int DIFF_NO_SIG = 100;
	private static final int MIN_DIFF = 3;
	private static final int NO_SIGNAL = -120;
	private static final int NORM = 10000;

	private HashMap<String, WiFi> wifis;
	private double pi = 1;

	public Data(HashMap<String, WiFi> wifis, ArrayList<WiFi> input) {
		this.wifis = wifis;
		ArrayList<Integer> diff = new ArrayList<Integer>();
		ArrayList<Double> w = new ArrayList<Double>();
		for(int i = 0; i<input.size();i++) {
			WiFi wifi = wifis.get(input.get(i).getMac());
			if(wifi!=null) {
				if (wifi.getSignal().equals("") || Double.parseDouble(wifi.getSignal()) < (-120)) {
					wifi.setSignal(NO_SIGNAL + "");
					diff.add(DIFF_NO_SIG);
					w.add(NORM / (Math.pow(diff.get(diff.size()-1), SIG_DIFF) * Math.pow(Double.parseDouble(input.get(i).getSignal()), POWER)));
					this.pi *= w.get(w.size()-1);
					continue;
				}
				int dTemp = Math.abs((int)Double.parseDouble(wifi.getSignal()) - (int)Double.parseDouble(input.get(i).getSignal()));
				dTemp = dTemp < MIN_DIFF ? MIN_DIFF : dTemp;
				diff.add(dTemp);
				w.add(NORM / (Math.pow(diff.get(diff.size()-1), SIG_DIFF) * Math.pow(Double.parseDouble(input.get(i).getSignal()), POWER)));
				pi *= w.get(w.size()-1);
			}
		}
	}

	public double getPi() {
		return pi;
	}
	public ArrayList<WiFi> getWifis() {
		return new ArrayList<WiFi>(this.wifis.values());
	}
}
