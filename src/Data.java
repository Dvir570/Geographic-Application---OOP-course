import java.util.ArrayList;

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

	private ArrayList<WiFi> wifis;
	private double pi = 1;

	public Data(ArrayList<WiFi> wifis, ArrayList<WiFi> input) {
		this.wifis = wifis;
		int[] diff = new int[Math.min(input.size(), wifis.size())];
		double[] w = new double[Math.min(input.size(), wifis.size())];
		for (int i = 0; i < Math.min(input.size(), wifis.size()); i++) {
			if (wifis.get(i).getSignal() == null || Double.parseDouble(wifis.get(i).getSignal()) < (-120)) {
				wifis.get(i).setSignal(NO_SIGNAL + "");
				diff[i] = DIFF_NO_SIG;
				w[i] = NORM
						/ (Math.pow(diff[i], SIG_DIFF) * Math.pow(Double.parseDouble(input.get(i).getSignal()), POWER));
				pi *= w[i];
				continue;
			}
			diff[i] = Math.abs((int)Double.parseDouble(wifis.get(i).getSignal()) - (int)Double.parseDouble(input.get(i).getSignal()));
			diff[i] = diff[i] < MIN_DIFF ? MIN_DIFF : diff[i];
			w[i] = NORM / (Math.pow(diff[i], SIG_DIFF) * Math.pow(Double.parseDouble(input.get(i).getSignal()), POWER));
			pi *= w[i];
		}

	}

	public double getPi() {
		return pi;
	}
	public ArrayList<WiFi> getWifis() {
		return wifis;
	}
}
