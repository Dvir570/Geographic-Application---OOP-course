package src;
import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayMapTest {
	DisplayMap dm = new DisplayMap();
	

	@Test
	public void testDisplayMap() {
		DisplayMap dm = new DisplayMap();
		assertTrue(dm.getToDisplay()!=null);
	}

	@Test
	public void testDisplayByPlace() {
		
		Row fictiveR1 = new Row();
		Row fictiveR2 = new Row();
		Row fictiveR3 = new Row();
		Row fictiveR4anthercor = new Row();
		double lon =50000;
		double lat =90000;
		double alt =5;
		fictiveR1.add(new WiFi("24:c9:a1:33:34:68,Ariel_University,[ESS],2017-10-26 14:07:34,11,-56,"+lat+','+(lon+3)+','+alt+",16,WIFI", "SM-G920F"));
		fictiveR2.add(new WiFi("24:c9:a1:33:34:68,dvir,[ESS],2017-10-26 14:07:34,11,-56,"+(lat-3)+','+lon+','+alt+",16,WIFI", "SM-G920F"));
	    fictiveR3.add(new WiFi("24:c9:a1:33:34:68,shmuel,[ESS],2017-10-26 14:07:39,11,-56,"+lat+','+lon+','+(alt+0.9)+",16,WIFI", "iphone"));
		double temp = lon;
		lon =lat;
		fictiveR4anthercor.add(new WiFi("24:c9:a1:33:34:68,Ariel_University,[ESS],2017-10-26 14:07:34,11,-56,"+lat+','+lon+','+alt+",16,WIFI", "SM-G920F"));
		lon = temp;
		ResultFile.result.add(fictiveR1);
		ResultFile.result.add(fictiveR2);
		ResultFile.result.add(fictiveR3);
		ResultFile.result.add(fictiveR4anthercor);
		dm.displayByPlace(ResultFile.result,false, lon, lat, alt, 3);
		
		assertTrue(dm.getToDisplay().size()==3);
		
		ResultFile.result.remove(fictiveR1);
		ResultFile.result.remove(fictiveR2);
		ResultFile.result.remove(fictiveR3);
		ResultFile.result.remove(fictiveR4anthercor);
	}

	@Test
	public void testDisplayByTime() {
		
		Row fictiveR1 = new Row();
		Row fictiveR2 = new Row();
		Row fictiveR3 = new Row();
		Row fictiveR4anthercor = new Row();
		
		fictiveR1.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "SM-G920F"));
		fictiveR2.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "SM-F"));
	    fictiveR3.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "iphone"));
		fictiveR4anthercor.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:30,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "iphone"));
		
		ResultFile.result.add(fictiveR1);
		ResultFile.result.add(fictiveR2);
		ResultFile.result.add(fictiveR3);
		ResultFile.result.add(fictiveR4anthercor);
		dm.displayByTime(ResultFile.result, false, "2017-10-26 14:07", "2017-10-26 14:07");
		
		assertTrue(dm.getToDisplay().size()==3);
		
		ResultFile.result.remove(fictiveR1);
		ResultFile.result.remove(fictiveR2);
		ResultFile.result.remove(fictiveR3);
		ResultFile.result.remove(fictiveR4anthercor);
	}
	
	@Test
	public void testDisplayByModel() {
		
		Row fictiveR1 = new Row();
		Row fictiveR2 = new Row();
		Row fictiveR3 = new Row();
		Row fictiveR4anthercor = new Row();
		
		fictiveR1.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "SM-G920F"));
		fictiveR2.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "SM-G920F"));
	    fictiveR3.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "SM-G920F"));
		fictiveR4anthercor.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:30,11,-85,32.10432894787016,35.20499025104117,688.1184746940897,16,WIFI", "iphone"));
		
		ResultFile.result.add(fictiveR1);
		ResultFile.result.add(fictiveR2);
		ResultFile.result.add(fictiveR3);
		ResultFile.result.add(fictiveR4anthercor);
		dm.displayByModel(ResultFile.result, false, "SM-G920F");
		
		assertTrue(dm.getToDisplay().size()==3);
		
		ResultFile.result.remove(fictiveR1);
		ResultFile.result.remove(fictiveR2);
		ResultFile.result.remove(fictiveR3);
		ResultFile.result.remove(fictiveR4anthercor);
	}

}
