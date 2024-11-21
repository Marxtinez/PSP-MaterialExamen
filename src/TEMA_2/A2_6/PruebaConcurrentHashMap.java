package TEMA_2.A2_6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class PruebaConcurrentHashMap {
	// -------------------------------------------------------------------------
	public static void main(String args[]) {
		long t1, t2;
		double tt, tp;
		int numHebras;
		String nombreFichero, palabraActual;
		Vector<String> vectorLineas;
		HashMap<String, Integer> hmCuentaPalabras;
		ConcurrentHashMap<String, Integer> chmCuentaPalabras;

		// Comprobacion y extraccion de los argumentos de entrada.
		if (args.length != 2) {
			System.err.println("Uso: java programa <numHebras> <fichero>");
			System.exit(-1);
		}
		try {
			numHebras = Integer.parseInt(args[0]);
			nombreFichero = args[1];
		} catch (NumberFormatException ex) {
			numHebras = -1;
			nombreFichero = "";
			System.out.println("ERROR: Argumentos numericos incorrectos.");
			System.exit(-1);
		}

		// Lectura y carga de lineas en "vectorLineas".
		vectorLineas = readFile(nombreFichero);
		System.out.println("Numero de lineas leidas: " + vectorLineas.size());
		System.out.println();

		//
		// Implementacion secuencial.
		//
		t1 = System.nanoTime();
		hmCuentaPalabras = new HashMap<String, Integer>(1000, 0.75F);

		for (int i = 0; i < vectorLineas.size(); i++) {
			// Procesa la linea "i".
			String[] palabras = vectorLineas.get(i).split("\\W+");
			for (int j = 0; j < palabras.length; j++) {
				// Procesa cada palabra de la linea "i", si es distinta de blancos.
				palabraActual = palabras[j].trim();
				if (palabraActual.length() > 0) {
					contabilizaPalabra(hmCuentaPalabras, palabraActual);
				}
			}
		}
		t2 = System.nanoTime();
		tt = ((double) (t2 - t1)) / 1.0e9;
		System.out.print("Implemen. secuencial: ");
		imprimePalabraMasUsadaYVeces(hmCuentaPalabras);
		System.out.println(" Tiempo(s): " + tt);
		System.out.println("Num. elems. tabla hash: " + hmCuentaPalabras.size());
		System.out.println();

		//
		// Implementacion concurrente con hilos y ConcurrentHashMap.
		//
		t1 = System.nanoTime();
		chmCuentaPalabras = new ConcurrentHashMap<String, Integer>();

		Thread[] hilos = new Thread[numHebras];
		int lineasPorHebra = vectorLineas.size() / numHebras;
		for (int i = 0; i < numHebras; i++) {
			int inicio = i * lineasPorHebra;
			int fin = (i == numHebras - 1) ? vectorLineas.size() : inicio + lineasPorHebra;
			hilos[i] = new Thread(new Hebra(vectorLineas, inicio, fin, chmCuentaPalabras));
			hilos[i].start();
		}

		// Esperamos a que todas las hebras terminen
		for (int i = 0; i < numHebras; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		t2 = System.nanoTime();
		tt = ((double) (t2 - t1)) / 1.0e9;
		System.out.print("Implemen. concurrente: ");
		imprimePalabraMasUsadaYVeces(chmCuentaPalabras);
		System.out.println(" Tiempo(s): " + tt);
		System.out.println("Num. elems. tabla hash: " + chmCuentaPalabras.size());
		System.out.println();
	}

	// -------------------------------------------------------------------------
	public static Vector<String> readFile(String fileName) {
		BufferedReader br;
		String linea;
		Vector<String> data = new Vector<String>();

		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((linea = br.readLine()) != null) {
				//// System.out.println( "Leida linea: " + linea );
				data.add(linea);
			}
			br.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return data;
	}

	// -------------------------------------------------------------------------
	public static void contabilizaPalabra(Map<String, Integer> lista, String palabra) {
		lista.merge(palabra, 1, Integer::sum); // Actualizamos de forma atomica
	}

	// -------------------------------------------------------------------------
	static void imprimePalabraMasUsadaYVeces(Map<String, Integer> cuentaPalabras) {
		Vector<Map.Entry> lista = new Vector<Map.Entry>(cuentaPalabras.entrySet());

		String palabraMasUsada = "";
		int numVecesPalabraMasUsada = 0;
		// Calcula la palabra mas usada.
		for (int i = 0; i < lista.size(); i++) {
			String palabra = (String) lista.get(i).getKey();
			int numVeces = (Integer) lista.get(i).getValue();
			if (i == 0) {
				palabraMasUsada = palabra;
				numVecesPalabraMasUsada = numVeces;
			} else if (numVecesPalabraMasUsada < numVeces) {
				palabraMasUsada = palabra;
				numVecesPalabraMasUsada = numVeces;
			}
		}
		// Imprime resultado.
		System.out.print("( Palabra: '" + palabraMasUsada + "' " + "veces: " + numVecesPalabraMasUsada + " )");
	}

	// -------------------------------------------------------------------------
	static class Hebra implements Runnable {
		private Vector<String> lineas;
		private int inicio, fin;
		private ConcurrentHashMap<String, Integer> cuentaPalabras;

		public Hebra(Vector<String> lineas, int inicio, int fin, ConcurrentHashMap<String, Integer> cuentaPalabras) {
			this.lineas = lineas;
			this.inicio = inicio;
			this.fin = fin;
			this.cuentaPalabras = cuentaPalabras;
		}

		@Override
		public void run() {
			for (int i = inicio; i < fin; i++) {
				String[] palabras = lineas.get(i).split("\\W+");
				for (String palabra : palabras) {
					palabra = palabra.trim();
					if (palabra.length() > 0) {
						cuentaPalabras.merge(palabra, 1, Integer::sum); // Actualizamos de forma atomica
					}
				}
			}
		}
	}
}
