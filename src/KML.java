import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class KML {

	public void makeKML(ArrayList<WiFi> toDisplay) {
		Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		for (int i = 0; i < toDisplay.size(); i++) {
			WiFi w = toDisplay.get(i);
			doc.createAndAddPlacemark().withName(w.getSSID()).withOpen(Boolean.TRUE)
					.withDescription("mac: " + w.getMac() + " freq: " + w.getFreq() + " signal: " + w.getSignal())
					.createAndSetPoint().addToCoordinates(Double.parseDouble(w.getLon()),
							Double.parseDouble(w.getLat()), Double.parseDouble(w.getAlt()));
		}
		try {
			kml.marshal(new File(
					"C:\\Users\\dvir\\Documents\\Ariel University\\Eclipse Workspace\\Matala0\\bin\\KML Files\\placemarkes.kml"));
		} catch (IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}
}
