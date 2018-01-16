package src.Tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import src.Algorithms.Data;
import src.BuisnessLogic.WiFi;

public class DataTest {

	@Test
	public void testData() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "","", "", "","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","", "", "","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","", "", "","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","", "", "","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		Data d = new Data(wifis, input);
		assertTrue(d.getPi()==1.48320439700778);
	}

	@Test
	public void testGetPi() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "","", "", "","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","", "", "","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","", "", "","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","", "", "","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		Data d = new Data(wifis, input);
		assertNotNull(d.getPi());
	}

	@Test
	public void testGetWifis() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "","", "", "","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","", "", "","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","", "", "","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","", "", "","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		Data d = new Data(wifis, input);
		assertNotNull(d.getWifis());
	}

}
