import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class KML {

	public void makeKML(ArrayList<WiFi> toDisplay) {
		final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String kml = "";

		kml += header + "\n" + OpenTag("kml", "xmlns=\"http://www.opengis.net/kml/2.2\"") + "\n"
				+ OpenTag("Document") + "\n";
		for (int i = 0; i < toDisplay.size(); i++) {
			WiFi w = toDisplay.get(i);
			kml += OpenTag("Placemark") + "\n" + OpenTag("name") + w.getSSID() + CloseTag("name") + "\n"
					+ OpenTag("description") + "mac: " + w.getMac() + OpenTag("br/") + " freq: " + w.getFreq()
					+ OpenTag("br/") + " signal: " + w.getSignal() + CloseTag("description") + "\n"
					+ OpenTag("Point") + "\n" + OpenTag("coordinates") + w.getLon() + "," + w.getLat() + ","
					+ w.getAlt() + CloseTag("coordinates") + "\n" + CloseTag("Point") + "\n"
					+ CloseTag("Placemark") + "\n";
		}
		kml += CloseTag("Document") + "\n" + CloseTag("kml");
		try {
			FileWriter fw = new FileWriter(
					"C:\\Users\\dvir\\Documents\\Ariel University\\Eclipse Workspace\\Matala0\\bin\\KML Files\\placemarkes.kml");
			PrintWriter pw = new PrintWriter(fw);
			pw.write(kml);
			pw.close();
			fw.close();

		} catch (IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}

	
	public String OpenTag(String nameTag) {
		return "<" + nameTag + ">";
	}

	public String OpenTag(String nameTag, String property) {
		return "<" + nameTag + " " + property + ">";
	}

	public String CloseTag(String nameTag) {
		return "</" + nameTag + ">";
	}
}
