package BuisnessLogic;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
					if (Double.parseDouble(w2.getSignal()) > Double.parseDouble(w1.getSignal()))
						return 1;
					else if (Double.parseDouble(w2.getSignal()) < Double.parseDouble(w1.getSignal()))
						return -1;
					return 0;
				}
			});
			toDisplay.add(ar.get(i).getWiFi(0));
		}
	}

	/**
	 * filter by place giving coordinates
	 * 
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
	public static ArrayList<Row> displayByPlace(ArrayList<Row> DB, boolean not, double lon1, double lon2, double lat1,
			double lat2) {
		ArrayList<Row> ar = new ArrayList<Row>();
		for (int i = 0; i < DB.size(); i++) {
			Row r = DB.get(i);
			WiFi w = r.getWiFi(0);

			if (not) {
				if (!((Double.parseDouble(w.getLat()) >= Math.min(lat1, lat2))
						&& (Double.parseDouble(w.getLat()) <= Math.max(lat1, lat2))
						&& (Double.parseDouble(w.getLon()) >= Math.min(lon1, lon2))
						&& (Double.parseDouble(w.getLon()) <= Math.max(lon1, lon2))))
					ar.add(r);
			} else if (((Double.parseDouble(w.getLat()) >= Math.min(lat1, lat2))
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
	public static ArrayList<Row> displayByTime(ArrayList<Row> DB, boolean not, String startTime, String endTime) {
		ArrayList<Row> ar = new ArrayList<Row>();
		startTime += ":00";
		endTime += ":00";
		startTime = startTime.replace('T', ' ');
		endTime = endTime.replace('T', ' ');
		try {
			SimpleDateFormat format = new SimpleDateFormat();

			format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date temp;
			Date st = format.parse(startTime);
			Date et = format.parse(endTime);
			if (et.before(st)) { // swap
				temp = et;
				et = st;
				st = temp;
			}
			for (int i = 0; i < DB.size(); i++) {
				Row r = DB.get(i);
				String tempTime = r.getWiFi(0).getTime();
				Date d = format.parse(tempTime);
				if (not) {
					if (!(d.before(et) && d.after(st)))
						ar.add(r);
				} else if (d.before(et) && d.after(st))
					ar.add(r);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ar;
	}

	/**
	 * filter by the model your looking for
	 * 
	 * @param model
	 *            id device
	 */
	public static ArrayList<Row> displayByModel(ArrayList<Row> DB, boolean not, String model) {
		ArrayList<Row> ar = new ArrayList<Row>();

		for (int i = 0; i < DB.size(); i++) {
			Row r = DB.get(i);
			if (not) {
				if (!(r.getWiFi(0).getModel().equals(model)))
					ar.add(r);
			} else if (r.getWiFi(0).getModel().equals(model))
				ar.add(r);
		}
		return ar;
	}

	public ArrayList<WiFi> getToDisplay() {
		return toDisplay;
	}
}
