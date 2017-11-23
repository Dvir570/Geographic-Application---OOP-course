
import java.io.File;
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
	private static boolean isDouble(String s) { //Stackoverflow
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
				"src\\Wiggle WIFI");
		ArrayList<File> csvFiles = getCSVs(folder);

		final String resultFilePath = "src\\result.csv";
		ResultFile resultFile = new ResultFile(resultFilePath);

		Row rowWithAllwifis = new Row(csvFiles);
		resultFile.insertRows(rowWithAllwifis.getRow());
		resultFile.close();
		
		// ************************part 3**************************
		Scanner scanner = new Scanner(System.in);
		DisplayMap dm = new DisplayMap();
		String numberOfFilter;
		do {
			System.out.println(
					"how do you want to filter wifi points?\nPRESS 1 to filter by place\nPRESS 2 to filter by time\nPRESS 3 to filter by ID");
			numberOfFilter = scanner.next();
		} while (!(numberOfFilter.equals("1") || numberOfFilter.equals("2") || numberOfFilter.equals("3")));
		//ONEPLUS A3003
		switch (numberOfFilter) {
		case "1":
			String lat, lon, alt, radius;
			do {
				System.out.print("enter lon: ");
				lon = scanner.next();
				System.out.print("enter lat: ");
				lat = scanner.next();
				System.out.print("enter alt: ");
				alt = scanner.next();
				System.out.print("enter radius: ");
				radius = scanner.next();
			} while (!isDouble(lat) && !isDouble(lon) && !isDouble(alt) && !isDouble(radius));
			dm.displayByPlace(Double.parseDouble(lon), Double.parseDouble(lat), Double.parseDouble(alt),
					Double.parseDouble(radius));
			System.out.println("the kml file has been created! :)");
			break;
		case "2":
			//SimpleDateFormat sdf = new SimpleDateFormat("")
			
			System.out.println("enter year: ");
			String year = scanner.next();
			System.out.println("enter month: ");
			String month = scanner.next();
			System.out.println("enter day: ");
			String day = scanner.next();
			System.out.println("enter hours: ");
			String hours = scanner.next();
			System.out.println("enter minutes: ");
			String minutes = scanner.next();
			System.out.println("enter seconds: ");
			String seconds = scanner.next();
			dm.displayByTime(year+'-'+month+'-'+day+' '+hours+':'+minutes+':'+seconds);
			System.out.println("the kml file has been created! :)");
			break;
		case "3":
			System.out.println("enter ID device: ");
			String model = scanner.next();
			model += scanner.nextLine();
			dm.displayByModel(model);
			System.out.println("the kml file has been created! :)");
			break;
		default:
			break;
//SHIELD Tablet
		}
		scanner.close();
	}
}