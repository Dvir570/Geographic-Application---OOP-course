import java.util.ArrayList;

public class AvgMacPoint {
	private ArrayList<WiFi> commonMac;
	private double avgLon=0;
	private double avgLat=0;
	private double avgAlt=0;
	private String mac;
	private String SSID;

	public AvgMacPoint(ArrayList<WiFi> commonMac) {
		this.commonMac = commonMac;
		if (this.commonMac.isEmpty())
			return;
		this.mac=commonMac.get(0).getMac();
		this.SSID=commonMac.get(0).getSSID();
		
		//ALGORITHM ONE
		this.avgAlt = avgAlt();
		this.avgLon = avgLon();
		this.avgLat = avgLat();
	}

	private double avgAlt() {
		double sumWeight = 0;
		double sumWeightAlt = 0;
		double weight = 0;
		for (int i = 0; i < commonMac.size(); i++) {
			weight = Math.pow(Double.parseDouble(commonMac.get(i).getSignal()), -2.0);
			sumWeight = sumWeight + weight;
			sumWeightAlt = sumWeightAlt + (weight * Double.parseDouble(commonMac.get(i).getAlt()));
		}
		return sumWeightAlt / sumWeight;
	}

	private double avgLon() {
		double sumWeight = 0;
		double sumWeightLon = 0;
		double weight = 0;
		for (int i = 0; i < commonMac.size(); i++) {
			weight = Math.pow(Double.parseDouble(commonMac.get(i).getSignal()), -2.0);
			sumWeight = sumWeight + weight;
			sumWeightLon = sumWeightLon + (weight * Double.parseDouble(commonMac.get(i).getLon()));
		}

		return sumWeightLon / sumWeight;
	}

	private double avgLat() {
		double sumWeight = 0;
		double sumWeightLat = 0;
		double weight = 0;
		for (int i = 0; i < commonMac.size(); i++) {
			weight = Math.pow(Double.parseDouble(commonMac.get(i).getSignal()), -2.0);
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
	
	public String getSSID() {
		return SSID;
	}

	public String getMac() {
		return mac;
	}
	
	public String toString() {
		return this.mac+","+this.SSID+","+this.commonMac.get(0).getFreq()+","+this.commonMac.get(0).getSignal()+","+this.avgLon+","+
				this.avgLat+","+this.avgAlt+","+this.commonMac.get(0).getTime()+",Approx. w-center algo1";
	}
}
