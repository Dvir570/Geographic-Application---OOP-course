
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *main class
 */
public class matala0 {

	/**
	* return true if String s is valid Double. else return false.
	*/
	public static boolean isDouble(String s) { //Stackoverflow
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
	* return ArrayList of all .csv files in folder param.
	*/
	public static ArrayList<File> getCSVs(File folder) {
		ArrayList<File> ans = new ArrayList<File>();

		for (File currentF : folder.listFiles()) {
			if (currentF.isDirectory())
				ans.addAll(getCSVs(currentF));
			else if (currentF.getName().contains(".csv"))
				ans.add(currentF);
		}

		return ans;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File folder = new File(
				"C:\\Users\\dvir\\Documents\\Ariel University\\Eclipse Workspace\\Matala0\\src\\Wiggle WIFI");
		ArrayList<File> csvFiles = getCSVs(folder);

		final String resultFilePath = "C:\\Users\\dvir\\Documents\\Ariel University\\Eclipse Workspace\\Matala0\\src\\Wiggle WIFI Result\\result.csv";
		ResultFile resultFile = new ResultFile(resultFilePath);

		Row rowWithAllwifis = new Row(csvFiles);
		resultFile.insertRows(rowWithAllwifis.getRow());
		resultFile.close();
		/*// print:
		System.out.println("---------------------- #" + 0 + " -----------------------------");
		for (int i = 0; i < ResultFile.result.size(); i++) {
			for (int j = 0; j < ResultFile.result.get(i).size(); j++) {
				System.out.println("#" + (j + 1) + ":\t" + ResultFile.result.get(i).getWiFi(j));
			}
			System.out.println("---------------------- #" + (i + 1) + " -----------------------------");
		}
		// end of the print code*/

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
