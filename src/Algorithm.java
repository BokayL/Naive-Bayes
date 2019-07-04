import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {
	//doda zaloge resitev
	public static ArrayList<String> zalogaResitev(List<String[]> seznam) {
		ArrayList<String> zaloge = new ArrayList<>();

		for (int i = 0; i < seznam.size(); i++) {
			String tempVal = seznam.get(i)[seznam.get(i).length - 1];

			if (!zaloge.contains(tempVal)) {
				zaloge.add(tempVal);
			}
		}

		return zaloge;
	}

	
	public static ArrayList<String[]> nauciSeNaTestni(List<String[]> ucna, List<String[]> test,
			List<String> vseMozneResitve) {
		ArrayList<String[]> pravilnoRazvrsceni = new ArrayList<>();
		int steviloParametrov = ucna.get(0).length - 1;
		
		for (String[] vrsticaTestna : test) {
			ArrayList<Double> rezultatiZaPrimerjanje = new ArrayList<>();

			 
			for (String resitev : vseMozneResitve) {
				int ustreza = 0;

				for (String[] vrstica : ucna) {
					if (vrstica[vrstica.length - 1].equals(resitev)) {
						ustreza++;
					}
				}

				double odVseh = (double) ustreza / ucna.size();
				double tempRez = odVseh;

				
				for (int i = 0; i < steviloParametrov; i++) {
					int stResitev = 0;
					int stParametrov = 0;

					for (String[] vrstica : ucna) {
						if (vrstica[i].equals(vrsticaTestna[i])) {
							stParametrov++;

							if (vrstica[vrstica.length - 1].equals(resitev)) {
								stResitev++;
							}
						}
					}

					double res = (double) stResitev / stParametrov;

					tempRez = tempRez * res / odVseh;

				}

				rezultatiZaPrimerjanje.add(tempRez);

			}

			int max = rezultatiZaPrimerjanje.indexOf(Collections.max(rezultatiZaPrimerjanje));
			pravilnoRazvrsceni.add(new String[] { vseMozneResitve.get(max), vrsticaTestna[vrsticaTestna.length - 1] });
		}

		return pravilnoRazvrsceni;
	}

	
	//tocnost se izracuna pravilno razvrscene/vsemi razvrscenimi
	public static double izracunTocnosti(ArrayList<String[]> pravilnoRazvrsceni, int vseh) {
		int pravilnoRazvercenih = 1;
		for (String[] vrsticaUcma : pravilnoRazvrsceni) {
			if (vrsticaUcma[0].equals(vrsticaUcma[1]))
				pravilnoRazvercenih = pravilnoRazvercenih + 1;
			else
				continue;
		}

		double tocnostAlguritma = (double) pravilnoRazvercenih / vseh;
		return tocnostAlguritma;
	}

	
	//recall izracun = pozitivni pravilni  /z vsemi pozitivnimi
	public static double izracunajRecall(ArrayList<String[]> pravilnoRazvrsceni, ArrayList<String> parametri,
			int vseh) {
		ArrayList<Double> recalli = new ArrayList<>();
		for (String razvstitev : parametri) {
			int pozitivniPravilni = 1;
			int pozitivni = 0;
			for (String[] vrsticaUcna : pravilnoRazvrsceni) {
				if (vrsticaUcna[0].equals(razvstitev)) {
					if (vrsticaUcna[1].equals(razvstitev)) {
						pozitivniPravilni++;
					}
				}
				if (vrsticaUcna[1].equals(razvstitev)) {
					pozitivni++;
				} else
					continue;
			}
			double recal = (double) pozitivniPravilni / pozitivni * pozitivni / vseh;
			recalli.add(recal);
		}
		double recall = 0;
		for (int i = 0; i < recalli.size(); i++) {
			double sestevek = recalli.get(i).doubleValue();
			recall = recall + sestevek;
		}

		return recall;
	}
	
	
	//izracun precision  = pravilni pozitivni/true+false pozitivni
	public static double izracunajPrecision(ArrayList<String[]> pravilnoRazvrsceni, ArrayList<String> parametri,
			int vseh) {
		ArrayList<Double> precisioni = new ArrayList<>();
		for (String razvstitev : parametri) {
			int vsota = 0;
			int pozitivniPravilni = 0;
			int pozitivni = 1;
			for (String[] vrsticaUcna : pravilnoRazvrsceni) {
				if (vrsticaUcna[0].equals(razvstitev)) {
					if (vrsticaUcna[1].equals(razvstitev)) {
						pozitivniPravilni++;
					}
				}
				if (vrsticaUcna[0].equals(razvstitev)) {
					pozitivni++;
				}
				if (vrsticaUcna[1].equals(razvstitev)) {
					vsota++;
				} else
					continue;
			}
			double percsion = (double) pozitivniPravilni / pozitivni * vsota / vseh;
			precisioni.add(percsion);
		}
		double precision = 0;
		for (int i = 0; i < precisioni.size(); i++) {
			double sestevek = precisioni.get(i).doubleValue();
			precision = precision + sestevek;
		}
		return precision;
	}

	//izracun fscore
	public static double izracunajFMeasure(double recall, double precision) {
		double fMeasure = 2.0 * ((precision * recall) / (precision + recall));
		return fMeasure;
	}

	
	//izracun matrike zmede
	public static int[][] izracunajMatrikoZmede(ArrayList<String[]> pravilnoRazvrsceni, ArrayList<String> parametri,
			int steviloParametrov) {
		int[][] matrikaZmede = new int[steviloParametrov][steviloParametrov];
		for (int i = 0; i < steviloParametrov; i++) {
			for (int j = 0; j < steviloParametrov; j++) {
				for (String[] vrsticaTestna : pravilnoRazvrsceni) {
					if (vrsticaTestna[0].equals(parametri.get(j))) {
						if (vrsticaTestna[1].equals(parametri.get(i))) {
							matrikaZmede[i][j]++;
						}
					} else
						continue;
				}
			}
		}
		return matrikaZmede;
	}

}
