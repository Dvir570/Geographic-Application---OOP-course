import java.io.File;
import java.util.ArrayList;

public class Algo1 {
	private File  fileOut;
	private ArrayList<AvgMacPoint> avgMacPoints;
	private ArrayList<WiFi> DB;

	public Algo1(String pathIn, String pathOut) {

		this.fileOut = new File(pathOut);
		IOcsv readDB = new IOcsv(pathIn);
		this.DB = new ArrayList<WiFi>();
		String headersFile = readDB.readCsvLine();
		String[] sRow;
		String nextRow = readDB.readCsvLine();
		while (nextRow != null && !nextRow.equals("")) {
			System.out.println(nextRow);
			sRow = nextRow.split(",");
			int countWifi = Integer.parseInt(sRow[5]);
			for (int i = 0; i < countWifi; i++) {
				this.DB.add(new WiFi(sRow[1], sRow[7+(4*i)], sRow[6+(4*i)], sRow[8+(4*i)], sRow[3], sRow[2], sRow[4], sRow[9+(4*i)], sRow[0]));
			}
			nextRow = readDB.readCsvLine();
		}
		sortByMac();
		readDB.close();
	}

	private void sortByMac() {
		if (DB.size() == 0)
			return;

		ArrayList<Row> ar = new ArrayList<Row>();
		Row r = new Row();
		ar.add(r);
		r.add(DB.get(0));		
		for (int i = 1; i < DB.size(); i++) {
			for (int j = 0; j < ar.size(); j++) {
				if (DB.get(i).getMac().equals(ar.get(j).getWiFi(0).getMac())) {
					ar.get(j).add(DB.get(i));
					break;
				} else if (j == (ar.size() - 1)) {
					r = new Row();
					r.add(DB.get(i));
					ar.add(r);
					break;
				}
			}
		}
		this.avgMacPoints = new ArrayList<AvgMacPoint>();
		for (int i = 0; i < ar.size(); i++) {
			avgMacPoints.add(new AvgMacPoint(ar.get(i).getRow()));
		}
	}
	
	public void writeCsv() {
		IOcsv writeCsvPoints = new IOcsv(this.fileOut.getPath());
		for (int i=0;i<avgMacPoints.size();i++) {
			writeCsvPoints.writeCsvLine(i+","+avgMacPoints.get(i).toString());
		}
		writeCsvPoints.close();
	}
}
