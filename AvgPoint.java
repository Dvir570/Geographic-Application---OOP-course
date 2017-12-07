import java.util.ArrayList;

public class AvgPoint {
	private ArrayList<WiFi> commonMac;
	private double avgLon;
	private double avgLat;
	private double avgAlt;

	public AvgPoint(ArrayList<WiFi> commonMac) {
		this.commonMac = commonMac;
		this.avgAlt = avgAlt();
		this.avgLon = avgLon();
		this.avgLat = avgLat();
	}

	private double avgAlt() {
		double sumWeight = 0.000001;
		double sumWeightAlt = 0;
		double weight = 0;
		for (int i = 0; i < commonMac.size(); i++) {
			weight = Math.pow(Integer.parseInt(commonMac.get(i).getSignal()), -2);
			sumWeight = sumWeight + weight;
			sumWeightAlt = sumWeightAlt + (weight * Double.parseDouble(commonMac.get(i).getAlt()));
		}
		return sumWeightAlt / sumWeight;
	}

	private double avgLon() {
		double sumWeight = 0.000001;
		double sumWeightLon = 0;
		double weight = 0;
		for (int i = 0; i < commonMac.size(); i++) {
			weight = Math.pow(Integer.parseInt(commonMac.get(i).getSignal()), -2);
			sumWeight = sumWeight + weight;
			sumWeightLon = sumWeightLon + (weight * Double.parseDouble(commonMac.get(i).getLon()));
		}

		return sumWeightLon / sumWeight;
	}

	private double avgLat() {
		double sumWeight = 0.000001;
		double sumWeightLat = 0;
		double weight = 0;
		for (int i = 0; i < commonMac.size(); i++) {
			weight = Math.pow(Integer.parseInt(commonMac.get(i).getSignal()), -2);
			sumWeight = sumWeight + weight;
			sumWeightLat = sumWeightLat + (weight * Double.parseDouble(commonMac.get(i).getLat()));
		}
		return sumWeightLat / sumWeight;
	}

	public double getAvgLon() {
		return this.avgLon;
	}

	public double getAvgLat() {
		return this.avgLat;
	}

	public double getAvgAlt() {
		return this.avgAlt;
	}
	
	
}
