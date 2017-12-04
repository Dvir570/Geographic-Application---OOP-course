import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Filter by option you choose and create the kml file.
 */
public class DisplayMap {
	ArrayList<WiFi> toDisplay;

	public DisplayMap() {
		this.toDisplay = new ArrayList<WiFi>();
	}

	private void SortbyMac() {
		if (toDisplay.size() == 0)
			return;

		ArrayList<Row> ar = new ArrayList<Row>();
		// r.add(toDisplay.get(0));
		for (int i = 1; i < toDisplay.size(); i++) {
			for (int j = 0; j < ar.size(); j++) {
				if (toDisplay.get(i).getMac() == ar.get(j).getWiFi(0).getMac()) {
					ar.get(j).add((toDisplay.get(i)));
					break;
				} else if (j == (ar.size() - 1)) {
					Row r = new Row();
					r.add(toDisplay.get(i));
					ar.add(r);
				}
			}
		}

		for (int i = 0; i < ar.size(); i++) {
			Collections.sort(ar.get(i).getRow(), new Comparator<WiFi>() { // StackOverflow:
				// https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
				@Override
				public int compare(WiFi w1, WiFi w2) {
					if (Integer.parseInt(w2.getSignal()) > Integer.parseInt(w1.getSignal()))
						return 1;
					else if (Integer.parseInt(w2.getSignal()) < Integer.parseInt(w1.getSignal()))
						return -1;
					return 0;
				}
			});
			toDisplay = new ArrayList<WiFi>();
			for (i = 0; i < ar.size(); i++) {
				toDisplay.add(ar.get(i).getWiFi(0));
			}
		}

	}

	/**
	 * filter by place giving coordinates
	 * 
	 * @param lon
	 *            longtitude
	 * @param lat
	 *            latitude
	 * @param alt
	 *            altitude
	 * @param radius
	 *            extends all WiFi objects around coordinates by the given radius
	 */
	public void displayByPlace(double lon, double lat, double alt, double radius) {
		this.toDisplay.clear();
		for (int i = 0; i < ResultFile.result.size(); i++) {
			WiFi w = ResultFile.result.get(i).getWiFi(0); // index 0 has the strongest signal!
			if (Double.parseDouble(w.getLat()) <= (lat + radius) && Double.parseDouble(w.getLat()) >= (lat - radius)
					&& Double.parseDouble(w.getLon()) <= (lon + radius)
					&& Double.parseDouble(w.getLon()) >= (lon - radius))
				this.toDisplay.add(w);
		}
		KML kml = new KML();
		kml.makeKML(this.toDisplay);
	}

	/**
	 * filter by giving the date and time yyyy/MM/dd hh:mm:ss
	 * 
	 * @param dateTime
	 *            filtered by the dateTime given
	 */
	public void displayByTime(String dateTime) {
		this.toDisplay.clear();
		for (int i = 0; i < ResultFile.result.size(); i++) {
			WiFi w = ResultFile.result.get(i).getWiFi(0);// index 0 has the strongest signal!
			if (w.getTime().equals(dateTime))
				this.toDisplay.add(w);
		}
		KML kml = new KML();
		kml.makeKML(this.toDisplay);
	}

	/**
	 * filter by the model your looking for
	 * 
	 * @param model
	 *            id device
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
