import java.util.ArrayList;
/**
 *Filter by option you choose and create the kml file.
 */
public class DisplayMap {
	ArrayList<WiFi> toDisplay;

	public DisplayMap() {
		this.toDisplay = new ArrayList<WiFi>();
	}

	/**
	 * filter by place giving coordinates
	 * @param lon longtitude
	 * @param lat latitude
	 * @param alt altitude
	 * @param radius extends all WiFi objects around coordinates by the given radius
	 */
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

	/**
	 * filter by giving  the date and time yyyy/MM/dd hh:mm:ss
	 * @param dateTime filtered by the dateTime given 
	 */
	public void displayByTime(String dateTime) {
		this.toDisplay.clear();
		for (int i = 0; i < ResultFile.result.size(); i++) {
			WiFi w = ResultFile.result.get(i).getWiFi(0);//index 0  has the strongest signal!
			if (w.getTime().equals(dateTime))
				this.toDisplay.add(w);
		}
		KML kml = new KML();
		kml.makeKML(this.toDisplay);
	}

	/**
	 * filter by the model your looking for
	 * @param model id device
	 */
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
