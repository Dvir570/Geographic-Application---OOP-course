import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *present row in the result.csv file
 */
public class Row {
	ArrayList<WiFi> row;

	public Row() {
		this.row = new ArrayList<>();
	}

	/**
	 * collect WiFi objects from any .csv file in csvFiles
	 * @param csvFiles ArrayList of .csv files
	 */
	public Row(ArrayList<File> csvFiles) {
		this.row = new ArrayList<WiFi>();
		
		for (int cf = 0; cf < csvFiles.size(); cf++) {
			try {
				FileReader fr = new FileReader(csvFiles.get(cf).getPath());
				BufferedReader br = new BufferedReader(fr);

				String model = br.readLine().split(",")[2].split("=")[1];
				String headersFile = br.readLine();
				
				String nextWiFi = br.readLine();
				while (nextWiFi != null) {
					this.row.add(new WiFi(nextWiFi, model));
					nextWiFi = br.readLine();
				}

				br.close();
				fr.close();
			} catch (IOException ex) {
				System.out.print("Error reading file\n" + ex);
				System.exit(2);
			}
		}
	}

	/**
	 * set ArrayList of WiFi objects to the Row object
	 * @param row is ArrayList of WiFi
	 */
	public void setRow(ArrayList<WiFi> row) {
		this.row = row;
	}
	
	/**
	 * @return ArrayList of WiFi object in  the row
	 */
	public ArrayList<WiFi> getRow(){
		return this.row;
	}

	/**
	 * insert a new WiFi object to the row
	 * @param newWiFi is a new WiFi object
	 */
	public void add(WiFi newWiFi) {
		this.row.add(newWiFi);
	}

	/**
	 * 
	 * @param index is the index of a specipic WiFi object
	 * @return a WiFi object in index
	 */
	public WiFi getWiFi(int index){
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
	public boolean isEmpty(){
		return this.row.isEmpty();
	}
}
