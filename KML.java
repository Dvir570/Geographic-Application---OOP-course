import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.TimePrimitive;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;

public class KML {

	public void makeKML(ArrayList<WiFi> toDisplay) {
		Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		String time;
		for (int i = 0; i < toDisplay.size(); i++) {
			WiFi w = toDisplay.get(i);
			time = convertTimeFormat(w.getTime());
			TimeStamp ts = new TimeStamp();
			ts.setWhen(time);
			doc.createAndAddPlacemark().withName(w.getSSID()).withOpen(Boolean.TRUE).withTimePrimitive(ts)
					.withDescription("mac: " + w.getMac() + " freq: " + w.getFreq() + " signal: " + w.getSignal())
					.createAndSetPoint().addToCoordinates(Double.parseDouble(w.getLon()),
							Double.parseDouble(w.getLat()), Double.parseDouble(w.getAlt()));
		}
		try {
			kml.marshal(new File("src\\placemarks.kml"));
		} catch (IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}

	//ONEPLUS A3003
	private String convertTimeFormat(String oldTimeFormat) {
		String[] dateTime = oldTimeFormat.split(" ");
		System.out.println(dateTime[0] + 'T' + dateTime[1]);
		return dateTime[0] + 'T' + dateTime[1];
	}
}
