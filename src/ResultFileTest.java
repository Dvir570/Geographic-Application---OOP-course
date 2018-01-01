package src;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.Test;

public class ResultFileTest {
	ResultFile rf= new ResultFile("input files\\result.csv");
	@Test
	public void testResultFile(){
		String src="input files\\result.csv";
	
		ResultFile rf= new ResultFile(src);
		rf.close();
		File f = new File(src);
		assertTrue(f.exists());//check if the csv file exists
		try {
		FileReader fr = new FileReader(src);
		BufferedReader br = new BufferedReader(fr);
		assertNotNull((br.readLine()));
		
	    br.close();
	    fr.close();
	    
		}
		catch (IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}

	@Test
	public void testInsertRows() {
		ArrayList<WiFi> toDisplay = new ArrayList<>();
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,1.10432894787016,35.20499025104117,400.1184746940897,16,BT", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,2.10432894787016,35.20499025104117,500.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-85,3.10432894787016,35.20499025104117,600.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,4.10432894787016,35.20499025104117,700.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,4.10432894787016,35.20499025104117,700.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,4.10432894787016,35.20499025104117,700.1184746940897,16,WIFI", "Iphone"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-28 14:07:34,11,-85,5.10432894787016,35.20499025104117,688.1184746940897,16,BT", "SM-G920F"));
		
		rf.insertRows(toDisplay);
		assertTrue(ResultFile.result.size()==3);
		boolean allWifi=true;
		for(int i=0;i<ResultFile.result.size();i++) {
			for(int j=0;j<ResultFile.result.get(i).size();j++) {
				if(!ResultFile.result.get(i).getWiFi(j).getType().equals("WIFI")) {
					allWifi=false;
					continue;
				}				
			}
			if(!allWifi)
				continue;
		}
		assertTrue(allWifi);
		
		ResultFile.result.clear();
		
		toDisplay = new ArrayList<>();
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-10,1.10432894787016,35.20499025104117,400.1184746940897,10,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-17,1.10432894787016,35.20499025104117,400.1184746940897,11,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-14,1.10432894787016,35.20499025104117,400.1184746940897,12,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-12,1.10432894787016,35.20499025104117,400.1184746940897,12,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-13,1.10432894787016,35.20499025104117,400.1184746940897,13,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-13,1.10432894787016,35.20499025104117,400.1184746940897,13,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-13,1.10432894787016,35.20499025104117,400.1184746940897,13,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-12,1.10432894787016,35.20499025104117,400.1184746940897,14,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-15,1.10432894787016,35.20499025104117,400.1184746940897,15,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-16,1.10432894787016,35.20499025104117,400.1184746940897,16,WIFI", "SM-G920F"));
		toDisplay.add(new WiFi("24:c9:a1:36:52:f8,Ariel_University,[ESS],2017-10-26 14:07:34,11,-11,1.10432894787016,35.20499025104117,400.1184746940897,17,WIFI", "SM-G920F"));
		
		rf.insertRows(toDisplay);
		assertTrue(ResultFile.result.size()==1);
		if(ResultFile.result.size()==1)
			assertTrue(ResultFile.result.get(0).size()==10);
		
		for (int i=0;i<ResultFile.result.get(0).getWifis().size();i++) {
			System.out.println(ResultFile.result.get(0).getWifis().get(i).getSignal());
		}
		int min = Integer.parseInt(toDisplay.get(0).getSignal());
		for(int i = 1; i<toDisplay.size();i++)
			if(min>Integer.parseInt(toDisplay.get(i).getSignal()))
				min = Integer.parseInt(toDisplay.get(i).getSignal());
		
		for(int i = 0;i<ResultFile.result.get(0).size();i++)
		{
			assertTrue(Integer.parseInt(ResultFile.result.get(0).getWiFi(i).getSignal())>=min);
			if(Integer.parseInt(ResultFile.result.get(0).getWiFi(i).getSignal())<min) continue;
		}
		
	}
	
/*
	@Test
	public void testClose() {
		fail("Not yet implemented");
	}
*/
}
