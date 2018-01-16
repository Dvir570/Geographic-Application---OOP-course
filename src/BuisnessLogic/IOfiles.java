package src.BuisnessLogic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IOfiles {
	private File file;
	private FileReader fr;
	private BufferedReader br;
	private FileWriter fw;
	private PrintWriter pw;

	public IOfiles(String FilePath) {
		this.file = new File(FilePath);
	}

	public String readLine() {
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

	public void writeLine(String line) {
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
	
	public void writeLine(String line, boolean append) {
		if (line == null)
			return;

		try {
			if (pw == null || fw == null) {
				this.fw = new FileWriter(file.getPath(), append);
				this.pw = new PrintWriter(fw);
			}

			pw.println(line);

		} catch (IOException ex) {
			System.out.print("Error writing file\n" + ex);
			System.exit(2);
		}
	}
	public void write(String line, boolean append) {
		if (line == null)
			return;

		try {
			if (pw == null || fw == null) {
				this.fw = new FileWriter(file.getPath(), append);
				this.pw = new PrintWriter(fw);
			}

			pw.print(line);

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