package src.BuisnessLogic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * present row in the result.csv file
 */
public class Row {
	private ArrayList<WiFi> row;

	public Row() {
		this.row = new ArrayList<WiFi>();
	}
	
	public Row(WiFi w) {
		this.row = new ArrayList<WiFi>();
		this.row.add(w);
	}
	/**
	 * collect WiFi objects from any .csv file in csvFiles
	 * 
	 * @param csvFiles
	 *            ArrayList of .csv files
	 */
	public Row(ArrayList<File> csvFiles) {
		this.row = new ArrayList<WiFi>();

		for (int cf = 0; cf < csvFiles.size(); cf++) {
			IOfiles readCsv = new IOfiles(csvFiles.get(cf).getPath());

			String model = readCsv.readLine().split(",")[2].split("=")[1];
			String headersFile = readCsv.readLine();

			String nextWiFi = readCsv.readLine();
			while (nextWiFi != null) {
				this.row.add(new WiFi(nextWiFi, model));
				nextWiFi = readCsv.readLine();
			}

			readCsv.close();
		}
	}
	
	public void addRows(ArrayList<File> csvFiles) {
		for (int cf = 0; cf < csvFiles.size(); cf++) {
			IOfiles readCsv = new IOfiles(csvFiles.get(cf).getPath());

			String model = readCsv.readLine().split(",")[2].split("=")[1];
			String headersFile = readCsv.readLine();

			String nextWiFi = readCsv.readLine();
			while (nextWiFi != null) {
				this.row.add(new WiFi(nextWiFi, model));
				nextWiFi = readCsv.readLine();
			}

			readCsv.close();
		}
	}

	/**
	 * set ArrayList of WiFi objects to the Row object
	 * 
	 * @param row
	 *            is ArrayList of WiFi
	 */
	public void setRow(ArrayList<WiFi> row) {
		if (row != null)
			this.row = row;
	}

	/**
	 * @return ArrayList of WiFi object in the row
	 */
	public ArrayList<WiFi> getWifis() {
		return this.row;
	}

	/**
	 * insert a new WiFi object to the row
	 * 
	 * @param newWiFi
	 *            is a new WiFi object
	 */
	public void add(WiFi newWiFi) {
		if (newWiFi != null)
			this.row.add(newWiFi);
	}

	/**
	 * 
	 * @param index
	 *            is the index of a specipic WiFi object
	 * @return a WiFi object in index
	 */
	public WiFi getWiFi(int index) {
		return this.row.get(index);
	}

	/**
	 * @return the count of WiFi objects we have in this row
	 */
	public int size() {
		return this.row.size();
	}

	/**
	 * @return false if we have WiFi object in this row. else return true.
	 */
	public boolean isEmpty() {
		return this.row.isEmpty();
	}
}
