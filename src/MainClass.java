package src;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
/**
 *main class
 */
public class MainClass {

	/**
	* @return true if String s is valid Double. else return false.
	*/
	private static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	/**
	* @return ArrayList of all .csv files in folder param.
	*/
	private static ArrayList<File> getCSVs(File folder) {
		ArrayList<File> ans = new ArrayList<File>();

		for (File currentF : folder.listFiles()) {
			if (currentF.isDirectory())
				ans.addAll(getCSVs(currentF));
			else if (currentF.getName().contains(".csv"))
				ans.add(currentF);
		}

		return ans;
	}
/**
 * the main function 
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File folder = new File(
				"input files\\Wiggle WIFI");

		ArrayList<File> csvFiles = getCSVs(folder);

		final String resultFilePath = "output files\\result.csv";
		ResultFile resultFile = new ResultFile(resultFilePath);

		Row rowWithAllwifis = new Row(csvFiles);
		resultFile.insertRows(rowWithAllwifis.getWifis());
		resultFile.close();
		
//		// ************************part 3**************************
//		Scanner scanner = new Scanner(System.in);
//		DisplayMap dm = new DisplayMap();
//		String numberOfFilter;
//		do {
//			System.out.println(
//					"how do you want to filter wifi points?\nPRESS 1 to filter by place\nPRESS 2 to filter by time\nPRESS 3 to filter by ID");
//			numberOfFilter = scanner.nextLine();
//		} while (!(numberOfFilter.equals("1") || numberOfFilter.equals("2") || numberOfFilter.equals("3")));
//		//ONEPLUS A3003
//		switch (numberOfFilter) {
//		case "1":
//			String lat, lon, alt, radius;
//			do {
//				System.out.print("enter lon: ");
//				lon = scanner.nextLine();
//				System.out.print("enter lat: ");
//				lat = scanner.nextLine();
//				System.out.print("enter alt: ");
//				alt = scanner.nextLine();
//				System.out.print("enter radius: ");
//				radius = scanner.nextLine();
//			} while (!isDouble(lat) && !isDouble(lon) && !isDouble(alt) && !isDouble(radius));
//			dm.displayByPlace(Double.parseDouble(lon), Double.parseDouble(lat), Double.parseDouble(alt),
//					Double.parseDouble(radius));
//			System.out.println("the kml file has been created! :)");
//			break;
//		case "2":
//			//SimpleDateFormat sdf = new SimpleDateFormat("")
//			
//			System.out.println("enter year: ");
//			String year = scanner.nextLine();
//			System.out.println("enter month: ");
//			String month = scanner.nextLine();
//			System.out.println("enter day: ");
//			String day = scanner.nextLine();
//			System.out.println("enter hours: ");
//			String hours = scanner.nextLine();
//			System.out.println("enter minutes: ");
//			String minutes = scanner.nextLine();
//			System.out.println("enter seconds: ");
//			String seconds = scanner.nextLine();
//			dm.displayByTime(year+'-'+month+'-'+day+' '+hours+':'+minutes+':'+seconds);
//			System.out.println("the kml file has been created! :)");
//			break;
//		case "3":
//			System.out.println("enter ID device: ");
//			String model = scanner.nextLine();
//			//model += scanner.nextLine();
//			dm.displayByModel(model);
//			System.out.println("the kml file has been created! :)");
//			break;
//		default:
//			scanner.close();
//			break;
////SHIELD Tablet
//		}
	
		
//		System.out.println("--- Algorithm 1 ---");
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("enter a DB path:");
//		//input files\Algo1\DB.csv
//		String dbPath = scanner.nextLine();
//		System.out.println("enter a destination path:");
//		//output files\result of Algorithm 1.csv
//		String destPath = scanner.nextLine();
//		scanner.close();
//		Algo1 algo1  =new Algo1(dbPath, destPath);
//		algo1.writeCsv();
//		System.out.println("--- Algorithm 1 finished ---");
		
		System.out.println("--- Algorithm 2 ---");
		AlgorithmII algo2 = new AlgorithmII("input files\\Algo2\\DB BOAZ.csv", "input files\\Algo2\\noGPS BOAZ.csv", 4, "output files\\result of Algorithm 2.csv");
		algo2.writeCsv();
		System.out.println("--- Algorithm 2 finished ---");
	}
}