import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IOcsv {
	private File file;
	private FileReader fr;
	private BufferedReader br;
	private FileWriter fw;
	private PrintWriter pw;

	public IOcsv(String FilePath) {
		this.file = new File(FilePath);
	}

	public String readCsvLine() {
		try {
			if (fr == null || br == null) {
				fr = new FileReader(file.getPath());
				br = new BufferedReader(fr);
			}

			return br.readLine();

		} catch (IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		return null;
	}

	public void writeCsvLine(String line) {
		if (line == null)
			return;

		try {
			if (pw == null || fw == null) {
				this.fw = new FileWriter(file.getPath());
				this.pw = new PrintWriter(fw);
			}

			pw.println(line);

		} catch (IOException ex) {
			System.out.print("Error writing file\n" + ex);
			System.exit(2);
		}
	}

	public void close() {
		try {
			if (this.fr != null) {
				fr.close();
				fr = null;
			}
			if (this.br != null) {
				br.close();
				br = null;
			}
			if (this.pw != null) {
				pw.close();
				pw = null;
			}
			if (this.fw != null) {
				fw.close();
				fw = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}