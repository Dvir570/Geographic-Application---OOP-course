package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import BuisnessLogic.WiFi;

public class WiFiTest {

	@Test
	public void testWiFi() {
		WiFi w = new WiFi();
		assertNotNull(w);
	}

	@Test
	public void testWiFiStringString() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertNotNull(w);
	}

	@Test
	public void testGetLat() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		try {
		Float.parseFloat(w.getLat());
		} catch( NumberFormatException e) {
			assertTrue(false);
		}catch(NullPointerException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetLon() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		try {
		Float.parseFloat(w.getLon());
		} catch( NumberFormatException e) {
			assertTrue(false);
		}catch(NullPointerException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAlt() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		try {
		Float.parseFloat(w.getAlt());
		} catch( NumberFormatException e) {
			assertTrue(false);
		}catch(NullPointerException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetModel() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertNotNull(w.getModel());
	}

	@Test
	public void testGetType() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertNotNull(w.getType());
	}

	@Test
	public void testGetMac() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertNotNull(w.getMac());
	}

	@Test
	public void testGetSSID() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertTrue(!w.getSSID().equals(""));
		w = new WiFi(
				"24:c9:a1:36:52:f8,,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertTrue(w.getSSID().equals(""));
	}

	@Test
	public void testGetFreq() {
		int channel = 11;
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,"+channel+",-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");

		assertTrue(w.getFreq().equals(((channel - 1) * 5 + 2412)+""));
		
		channel = 90;
		w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,"+channel+",-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertTrue(w.getFreq().equals(((channel - 34) * 5 + 5170)+""));
		
		channel = 15;
		w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,"+channel+",-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertTrue(w.getFreq().equals(""));
		
	}

	@Test
	public void testGetTime() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertTrue(w.getTime().equals("2017-10-28 14:07:34"));
		
	}

	@Test
	public void testGetSignal() {
		WiFi w = new WiFi(
				"24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT",
				"SM-G920F");
		assertTrue(w.getSignal().equals("-85"));
	}

}
