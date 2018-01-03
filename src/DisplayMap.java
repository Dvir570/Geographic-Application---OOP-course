package src;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.Comparator;

/**
 * Filter by option you choose and create the kml file.
 */
public class DisplayMap {
	private ArrayList<WiFi> toDisplay;

	public DisplayMap(ArrayList<WiFi> toDisplay) {
		this.toDisplay = toDisplay;
	}

	public DisplayMap() {
		this.toDisplay = new ArrayList<WiFi>();
	}

	public void SortbyMac() {
		if (toDisplay.size() == 0)
			return;

		ArrayList<Row> ar = new ArrayList<Row>();
		Row r = new Row();
		ar.add(r);
		r.add(toDisplay.get(0));
		for (int i = 1; i < toDisplay.size(); i++) {
			for (int j = 0; j < ar.size(); j++) {

				if (toDisplay.get(i).getMac().equals(ar.get(j).getWiFi(0).getMac())) {
					ar.get(j).add(toDisplay.get(i));
					break;
				} else if (j == (ar.size() - 1)) {
					r = new Row();
					r.add(toDisplay.get(i));
					ar.add(r);
				}
			}
		}
		System.out.println("number of points: " + ar.size());

		toDisplay = new ArrayList<WiFi>();
		for (int i = 0; i < ar.size(); i++) {
			Collections.sort(ar.get(i).getWifis(), new Comparator<WiFi>() { // StackOverflow://https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
				@Override
				public int compare(WiFi w1, WiFi w2) {
					if (Integer.parseInt(w2.getSignal()) > Integer.parseInt(w1.getSignal()))
						return 1;
					else if (Integer.parseInt(w2.getSignal()) < Integer.parseInt(w1.getSignal()))
						return -1;
					return 0;
				}
			});
			toDisplay.add(ar.get(i).getWiFi(0));
		}
	}

	/**
	 * filter by place giving coordinates
	 * @param notID 
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
	public static ArrayList<Row> displayByPlace(ArrayList<Row> DB, boolean not, double lon1, double lon2, double lat1, double lat2) {
		ArrayList<Row> ar = new ArrayList<Row>();
		for (int i = 0; i < DB.size(); i++) {
			Row r = DB.get(i);
			WiFi w = r.getWiFi(0);
			
			if (not) {
			if (!((Double.parseDouble(w.getLat()) >= Math.min(lat1, lat2))
					&& (Double.parseDouble(w.getLat()) <= Math.max(lat1, lat2))
					&& (Double.parseDouble(w.getLon()) >= Math.min(lon1, lon2))
					&& (Double.parseDouble(w.getLon()) <= Math.max(lon1, lon2))))
				ar.add(r);}
			else if (((Double.parseDouble(w.getLat()) >= Math.min(lat1, lat2))
					&& (Double.parseDouble(w.getLat()) <= Math.max(lat1, lat2))
					&& (Double.parseDouble(w.getLon()) >= Math.min(lon1, lon2))
					&& (Double.parseDouble(w.getLon()) <= Math.max(lon1, lon2))))
				ar.add(r);
		}
		return ar;
	}

	/**
	 * filter by giving the date and time yyyy/MM/dd hh:mm:ss
	 * 
	 * @param dateTime
	 *            filtered by the dateTime given
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<Row> displayByTime(ArrayList<Row> DB, boolean not, String startTime, String endTime) {
		Date temp = new Date();

		String[] dArray = startTime.split("-T:");
		Date st = new Date(Integer.parseInt(dArray[0]), Integer.parseInt(dArray[1]), Integer.parseInt(dArray[2]),
				Integer.parseInt(dArray[3]), Integer.parseInt(dArray[4]));
		dArray = endTime.split("-T:");
		Date et = new Date(Integer.parseInt(dArray[0]), Integer.parseInt(dArray[1]), Integer.parseInt(dArray[2]),
				Integer.parseInt(dArray[3]), Integer.parseInt(dArray[4]));
		if (et.before(st)) { //swap
			temp = et;
			et = st;
			st = temp;
		}
		ArrayList<Row> ar = new ArrayList<Row>();
		for (int i = 0; i < DB.size(); i++) {
			Row r = DB.get(i);
			String tempTime = r.getWiFi(0).getTime();
			dArray = tempTime.split("/ :");
			Date d = new Date(Integer.parseInt(dArray[2]), Integer.parseInt(dArray[1]), Integer.parseInt(dArray[0]),
					Integer.parseInt(dArray[3]), Integer.parseInt(dArray[4]));
			if(not) {
				if (!(d.before(et) && d.after(st)))
					ar.add(r);}
			else if (d.before(et) && d.after(st))
				ar.add(r);
		}
		return ar;
	}

	/**
	 * filter by the model your looking for
	 * 
	 * @param model
	 *            id device
	 */
	public static ArrayList<Row> displayByModel(ArrayList<Row> DB,boolean not, String model) {
		ArrayList<Row> ar = new ArrayList<Row>();
		
		for (int i = 0; i < DB.size(); i++) {
			Row r = DB.get(i);
			if(not) {
			if (!(r.getWiFi(0).getModel().equals(model)))
				ar.add(r);}
			else if (r.getWiFi(0).getModel().equals(model))
				ar.add(r);
		}
		return ar;
	}

	public ArrayList<WiFi> getToDisplay() {
		return toDisplay;
	}
}
