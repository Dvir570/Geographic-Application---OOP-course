package Tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import Algorithms.AvgSamplePoint;
import Algorithms.Data;
import BuisnessLogic.WiFi;

public class AvgSamplePointTest {

	@Test
	public void testAvgSamplePoint() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "", "1","1","1","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","1", "1", "1","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","1", "1", "1","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","1", "1", "1","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		ArrayList<Data> datas = new ArrayList<Data>();
		datas.add(new Data(wifis, input));
		ArrayList<WiFi> inputwifis = new ArrayList<WiFi>(wifis.values());
		AvgSamplePoint asp = new AvgSamplePoint(datas, 3, inputwifis);
		assertNotNull(asp);
	}

	@Test
	public void testGetAvgLon() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "", "1","1","1","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","1", "1", "1","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","1", "1", "1","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","1", "1", "1","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		ArrayList<Data> datas = new ArrayList<Data>();
		datas.add(new Data(wifis, input));
		ArrayList<WiFi> inputwifis = new ArrayList<WiFi>(wifis.values());
		AvgSamplePoint asp = new AvgSamplePoint(datas, 3, inputwifis);
		assertNotNull(asp.getAvgLon());
	}

	@Test
	public void testGetAvgLat() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "", "1","1","1","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","1", "1", "1","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","1", "1", "1","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","1", "1", "1","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		ArrayList<Data> datas = new ArrayList<Data>();
		datas.add(new Data(wifis, input));
		ArrayList<WiFi> inputwifis = new ArrayList<WiFi>(wifis.values());
		AvgSamplePoint asp = new AvgSamplePoint(datas, 3, inputwifis);
		assertNotNull(asp.getAvgLat());
	}

	@Test
	public void testGetAvgAlt() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "", "1","1","1","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","1", "1", "1","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","1", "1", "1","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","1", "1", "1","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		ArrayList<Data> datas = new ArrayList<Data>();
		datas.add(new Data(wifis, input));
		ArrayList<WiFi> inputwifis = new ArrayList<WiFi>(wifis.values());
		AvgSamplePoint asp = new AvgSamplePoint(datas, 1, inputwifis);
		assertNotNull(asp.getAvgAlt());
	}

	@Test
	public void testToString() {
		HashMap<String,WiFi> wifis = new HashMap<String,WiFi>();
		wifis.put("m1", new WiFi("", "m1", "", "", "1","1","1","",""));
		wifis.put("m2",new WiFi("", "m2", "", "","1", "1", "1","0",""));
		wifis.put("m3",new WiFi("", "m3", "", "","1", "1", "1","-130",""));
		wifis.put("m4",new WiFi("", "m4", "", "","1", "1", "1","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "m4", "", "","", "", "","-40",""));
		input.add(new WiFi("", "m2", "", "","", "", "","-50",""));
		input.add(new WiFi("", "m3", "", "","", "", "","-60",""));
		ArrayList<Data> datas = new ArrayList<Data>();
		datas.add(new Data(wifis, input));
		ArrayList<WiFi> inputwifis = new ArrayList<WiFi>(wifis.values());
		AvgSamplePoint asp = new AvgSamplePoint(datas, 3, inputwifis);
		assertNotNull(asp.toString());
	}

}
