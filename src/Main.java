import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		List<String[]> testna = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		int lineCount = 0;
		Options options = new Options();
		options.addOption("t", true, "Ucna mnozica");
		options.addOption("T", true, "Testna mnozica");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * String branje_ucna = cmd.getOptionValue("t"); if (branje_ucna !=
		 * null) System.out.println("Imamo uèno množico " + branje_ucna);
		 * 
		 * String branje_testna = cmd.getOptionValue("T"); if (branje_testna !=
		 * null) System.out.println("Imamo testno množico " +
		 * branje_testna);
		 */

		// branje testne datoteke
		String branje_ucna = "car_ucna.csv";
		String branje_testna = "car_testna.csv";
		try {
			br = new BufferedReader(new FileReader(branje_testna));
			System.out.println("Branje: " + branje_testna + " kot testni dokument");
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty() && lineCount > 0) {
					String[] strings = line.split(","); // razdelimo z vejicami
					testna.add(strings);
				}
				lineCount++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// branje ucne datoteke
		List<String[]> ucna = new ArrayList<>();
		BufferedReader br_uc = null;
		String line_uc = "";
		int lineCount_uc = 0;
		try {

			br_uc = new BufferedReader(new FileReader(branje_ucna));
			System.out.println("Branje: " + branje_ucna + " kot ucni dokument");
			while ((line_uc = br_uc.readLine()) != null) {
				if (!line_uc.isEmpty() && lineCount_uc > 0) {
					String[] strings = line_uc.split(","); // razdelimo z
															// vejicami
					ucna.add(strings);
				}
				lineCount_uc++;
			}
			br_uc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// zaloga resitev
		ArrayList<String> resitve = Algorithm.zalogaResitev(ucna);

		// pravilno razvrsceni primeri
		ArrayList<String[]> pravilnoRazvrsceni = Algorithm.nauciSeNaTestni(ucna, testna, resitve);
		int pravilnoRazvrscenih = pravilnoRazvrsceni.size();

		// izracun tocnosti,recalla,precision in fmeasure
		double tocnost = Algorithm.izracunTocnosti(pravilnoRazvrsceni, pravilnoRazvrscenih);
		double recall = Algorithm.izracunajRecall(pravilnoRazvrsceni, resitve, pravilnoRazvrscenih);
		double precision = Algorithm.izracunajPrecision(pravilnoRazvrsceni, resitve, pravilnoRazvrscenih);
		double fMeasure = Algorithm.izracunajFMeasure(recall, precision);

		// izpisi za zgornje stvari
		System.out.println();
		System.out.println("Toènost   : " + tocnost);
		System.out.println("Recall    : " + recall);
		System.out.println("Precision : " + precision);
		System.out.println("FMeasure  : " + fMeasure);
		System.out.println();
		System.out.println("Matrika zmede:");
		System.out.println();

		// stevec za velikost resitev, izracun matrike zmede
		int stevec = resitve.size();
		int[][] matrikaZmede = Algorithm.izracunajMatrikoZmede(pravilnoRazvrsceni, resitve, stevec);
		System.out.print("            ");
		for (int i = 0; i < stevec; i++) {
			System.out.printf("%8s", resitve.get(i));
			System.out.print("     ");
		}
		System.out.println();
		for (int i = 0; i < stevec; i++) {
			System.out.print(String.format("%1$10s", resitve.get(i)));
			for (int j = 0; j < stevec; j++) {
				System.out.print(String.format("%1$10s", matrikaZmede[i][j]));
			}
			System.out.println();
		}
	}

}