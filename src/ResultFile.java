package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * class that responsible about the result.csv file (ex2).
 */
public class ResultFile {

	static ArrayList<Row> result = new ArrayList<Row>();
	IOfiles writeResultFile;
	private File fileExist;
	/**
	 * @param resultFilePath
	 *            path for file result
	 */
	public ResultFile(String resultFilePath) {
		writeResultFile = new IOfiles(resultFilePath);
		this.fileExist = new File(resultFilePath);
		
		// print headers:
		if(!this.fileExist.exists())
			writeResultFile.writeLine("Time,ID,Lon,Lat,Alt,#WiFi networks," + "SSID1,MAC1,Frequncy1,Signal1,"
				+ "SSID2,MAC2,Frequncy2,Signal2," + "SSID3,MAC3,Frequncy3,Signal3," + "SSID4,MAC4,Frequncy4,Signal4,"
				+ "SSID5,MAC5,Frequncy5,Signal5," + "SSID6,MAC6,Frequncy6,Signal6," + "SSID7,MAC7,Frequncy7,Signal7,"
				+ "SSID8,MAC8,Frequncy8,Signal8," + "SSID9,MAC9,Frequncy9,Signal9,"
				+ "SSID10,MAC10,Frequncy10,Signal10");
		// end print headers
	}

	/**
	 * insert new rows to the result.csv file.
	 * 
	 * @param wifis - ArrayList of all WiFi objects you have to insert.
	 */
	public void insertRows(ArrayList<WiFi> wifis) {
		rowsGroupByTimeModel(wifis);
		top10fromAnyGroup();

		for (int i = 0; i < this.result.size(); i++) {
			Row row = this.result.get(i);

			if (row.isEmpty())
				continue;

			WiFi wifi = row.getWiFi(0);
			String str = wifi.getTime() + "," + wifi.getModel() + "," + wifi.getLon() + "," + wifi.getLat() + ","
					+ wifi.getAlt() + "," + row.size() + ",";
			for (int j = 0; j < row.size() - 1; j++) {
				wifi = row.getWiFi(j);
				str += (wifi.getSSID() + "," + wifi.getMac() + "," + wifi.getFreq() + "," + wifi.getSignal() + ",");
			}
			wifi = row.getWiFi(row.size() - 1); // print without "," at
												// the end
			str += (wifi.getSSID() + "," + wifi.getMac() + "," + wifi.getFreq() + "," + wifi.getSignal()); // print																							// the
																											// end
			this.writeResultFile.writeLine(str, this.fileExist.exists());
		}
	}

	/**
	 * group wifis into Row by time and model. the function also remove all WiFi
	 * that is not from type "WIFI".
	 * 
	 * @param wifis
	 *            - ArrayList of all WiFi objects you have to group.
	 */
	private void rowsGroupByTimeModel(ArrayList<WiFi> wifis) {

		for (int i = 0; i < wifis.size(); i++) {
			WiFi w = wifis.get(i);
			if (!w.getType().equals("WIFI"))
				wifis.remove(i--);
		}

		if (wifis.size() == 0)
			return;

		String tempTime = wifis.get(0).getTime();
		String tempModel = wifis.get(0).getModel();
		Row newRow = new Row();
		newRow.add(wifis.get(0));
		for (int i = 1; i < wifis.size(); i++) {
			if (((wifis.get(i)).getTime()).equals(tempTime) && ((wifis.get(i)).getModel()).equals(tempModel))
				newRow.add(wifis.get(i));
			else {
				tempTime = wifis.get(i).getTime();
				tempModel = wifis.get(i).getModel();
				this.result.add(newRow);
				newRow = new Row();
				newRow.add(wifis.get(i));
			}
		}
		this.result.add(newRow);
	}

	/**
	 * sort groups of WiFi (Row objects) by signal and takes top 10.
	 */
	private void top10fromAnyGroup() {
		// top ten by signal:
		for (int i = 0; i < this.result.size(); i++) {
			Collections.sort(this.result.get(i).getRow(), new Comparator<WiFi>() { // StackOverflow:
																					// https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
				@Override
				public int compare(WiFi w1, WiFi w2) {
					if (Integer.parseInt(w2.getSignal()) > Integer.parseInt(w1.getSignal()))
						return 1;
					else if (Integer.parseInt(w2.getSignal()) < Integer.parseInt(w1.getSignal()))
						return -1;
					return 0;
				}
			});
			if (this.result.get(i).size() > 10)
				this.result.get(i).getRow()
						.removeAll(this.result.get(i).getRow().subList(10, this.result.get(i).size()));
		}
	}

	public void close() {
		this.writeResultFile.close();
	}

}
