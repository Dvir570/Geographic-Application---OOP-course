package src;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AvgMacPointTest {

	@Test
	public void testAvgMacPoint() {
		ArrayList<WiFi> commonMac = new ArrayList<WiFi>();
		commonMac.add(new WiFi("", "m", "", "","3", "2", "1","-40",""));
		commonMac.add(new WiFi("", "m", "", "","2", "1", "2","-40",""));
		commonMac.add(new WiFi("", "m", "", "","1", "3", "3","-40",""));
		AvgMacPoint amp = new AvgMacPoint(commonMac);
		assertNotNull(amp);
	}

	@Test
	public void testGetAvgLon() {
		ArrayList<WiFi> commonMac = new ArrayList<WiFi>();
		commonMac.add(new WiFi("", "m", "", "","3", "2", "1","-40",""));
		commonMac.add(new WiFi("", "m", "", "","2", "1", "2","-40",""));
		commonMac.add(new WiFi("", "m", "", "","1", "3", "3","-40",""));
		AvgMacPoint amp = new AvgMacPoint(commonMac);
		assertNotNull(amp.getAvgLon());
		assertTrue(amp.getAvgLon()==2);
	}

	@Test
	public void testGetAvgLat() {
		ArrayList<WiFi> commonMac = new ArrayList<WiFi>();
		commonMac.add(new WiFi("", "m", "", "","3", "2", "1","-30",""));
		commonMac.add(new WiFi("", "m", "", "","2", "1", "2","-30",""));
		commonMac.add(new WiFi("", "m", "", "","1", "3", "3","-30",""));
		
		AvgMacPoint amp = new AvgMacPoint(commonMac);
		assertNotNull(amp.getAvgLat());
		assertTrue(amp.getAvgLat()==2);
	}

	@Test
	public void testGetAvgAlt() {
		ArrayList<WiFi> commonMac = new ArrayList<WiFi>();
		commonMac.add(new WiFi("", "m", "", "","3", "2", "1","-40",""));
		commonMac.add(new WiFi("", "m", "", "","2", "1", "2","-40",""));
		commonMac.add(new WiFi("", "m", "", "","1", "3", "3","-40",""));
		AvgMacPoint amp = new AvgMacPoint(commonMac);
		assertNotNull(amp.getAvgAlt());
		assertTrue(amp.getAvgAlt()==2);
	}

	@Test
	public void testGetSSID() {
		ArrayList<WiFi> commonMac = new ArrayList<WiFi>();
		
		commonMac.add(new WiFi("", "m", "dvirshmuel", "","3", "2", "1","-40",""));
		commonMac.add(new WiFi("", "m", "dvirshmuel", "","2", "1", "2","-40",""));
		commonMac.add(new WiFi("", "m", "dvirshmuel", "","1", "3", "3","-40",""));
		
		AvgMacPoint amp = new AvgMacPoint(commonMac);
		
		assertNotNull(amp.getSSID());
		assertTrue(amp.getSSID().equals("dvirshmuel"));
	}

	@Test
	public void testGetMac() {
		ArrayList<WiFi> commonMac = new ArrayList<WiFi>();
		commonMac.add(new WiFi("", "m", "dvirshmuel", "","3", "2", "1","-40",""));
		commonMac.add(new WiFi("", "m", "dvirshmuel", "","2", "1", "2","-40",""));
		commonMac.add(new WiFi("", "m", "dvirshmuel", "","1", "3", "3","-40",""));
		AvgMacPoint amp = new AvgMacPoint(commonMac);
		assertNotNull(amp.getMac());
		assertTrue(amp.getMac().equals("m"));
	}

}
