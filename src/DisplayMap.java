import java.util.ArrayList;

public class DisplayMap {
	ArrayList<WiFi> toDisplay;

	public DisplayMap() {
		this.toDisplay = new ArrayList<WiFi>();
	}

	public void displayByPlace(double lon, double lat, double alt, double radius) {
		this.toDisplay.clear();
		for (int i = 0; i < ResultFile.result.size(); i++) {
			WiFi w = ResultFile.result.get(i).getWiFi(0); //index 0  has the strongest signal!
			if (Double.parseDouble(w.getLat()) <= (lat + radius) && Double.parseDouble(w.getLat()) >= (lat - radius)
					&& Double.parseDouble(w.getLon()) <= (lon + radius) && Double.parseDouble(w.getLon()) >= (lon - radius)
					&& Double.parseDouble(w.getAlt()) <= (alt + radius) && Double.parseDouble(w.getAlt()) >= (alt - radius))
				this.toDisplay.add(w);
		}
		KML kml = new KML();
		kml.makeKML(this.toDisplay);
	}

	public void displayByTime(String time) {
		this.toDisplay.clear();
		for (int i = 0; i < ResultFile.result.size(); i++) {
			WiFi w = ResultFile.result.get(i).getWiFi(0);//index 0  has the strongest signal!
			if (w.getTime().equals(time))
				this.toDisplay.add(w);
		}
		KML kml = new KML();
		kml.makeKML(this.toDisplay);
	}

	public void displayByModel(String model) {
		this.toDisplay.clear();
		for (int i = 0; i < ResultFile.result.size(); i++) {
			WiFi w = ResultFile.result.get(i).getWiFi(0);
			if (w.getModel().equals(model))
				this.toDisplay.add(w);
		}
		KML kml = new KML();
		kml.makeKML(this.toDisplay);
	}
}
