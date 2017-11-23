import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class KMLTest {

	@Test
	public void testMakeKML() {
		ArrayList<WiFi> toDisplay = new ArrayList<>();
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,1.10432894787016,35.20499025104117,400.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,2.10432894787016,35.20499025104117,500.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,3.10432894787016,35.20499025104117,600.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,4.10432894787016,35.20499025104117,700.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "SM-G920F"));
		
		KML prekml = new KML();//before creating the real file
		prekml.makeKML(toDisplay);
		
		/////result
		String src="src\\placemarks.kml";
		File f = new File(src);
		assertTrue(f.exists());//check if the file exists
		Kml kml = Kml.unmarshal(new File("src\\placemarks.kml"));
		Feature feature=kml.getFeature();
		assertNotNull(feature);//check if the file isnot empty 
		
		
		
	}

}
