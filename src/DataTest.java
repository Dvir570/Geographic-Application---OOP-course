import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DataTest {

	@Test
	public void testData() {
		ArrayList<WiFi> wifis = new ArrayList<WiFi>();
		wifis.add(new WiFi("", "", "", "","", "", "","",""));
		wifis.add(new WiFi("", "", "", "","", "", "","0",""));
		wifis.add(new WiFi("", "", "", "","", "", "","-130",""));
		wifis.add(new WiFi("", "", "", "","", "", "","-40",""));
		
		ArrayList<WiFi> input = new ArrayList<WiFi>();
		input.add(new WiFi("", "", "", "","", "", "","-40",""));
		
	}

	@Test
	public void testGetPi() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWifis() {
		fail("Not yet implemented");
	}

}
