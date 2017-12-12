import java.util.ArrayList;

public class Data {
	private static final int POWER = 2;
	private static final double SIG_DIFF = 0.4;
	private static final int NORM = 10000;
	private ArrayList<WiFi> wifis;
	
	private double pi = 1;
	
	public Data(ArrayList<WiFi> wifis, ArrayList<WiFi> input) {
		this.wifis = wifis;
		int[] diff = new int[wifis.size()];
		double[] w = new double[wifis.size()];
		for(int i = 0; i<wifis.size() ; i++) {
			diff[i] = Math.abs(Integer.parseInt(wifis.get(i).getSignal()) - Integer.parseInt(input.get(i).getSignal()));
			w[i] = NORM/(Math.pow(diff[i], SIG_DIFF)*Math.pow(Integer.parseInt(input.get(i).getSignal()), POWER));
			pi*=w[i];
		}
		
	}
}
