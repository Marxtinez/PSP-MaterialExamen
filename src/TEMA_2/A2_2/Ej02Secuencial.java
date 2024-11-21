package TEMA_2.A2_2;

public class Ej02Secuencial {
    public static void main(String[] args) {

        long t0 = System.currentTimeMillis();

        for (int i = 1; i <= 400000; i++) {
            if (esPrimo(i)) {
                System.out.println(i + " es primo.");
            } else {
                System.out.println(i + " no es primo.");
            }
        }
            System.out.println("El tiempo total de ejecución ha sido de: " + (System.currentTimeMillis() - t0) / 1000 + " segundos");
    }

    public static boolean esPrimo(int numero) {
// Los números menores o iguales a 1 no son primos
        if (numero <= 1) {
            return false;
        }
// Comprobamos si es divisible por algún número desde 2 hasta 'numero - 1'
        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) {
                return false; // Si es divisible, no es primo
            }
        }
        return true; // Si no es divisible por ningún número, es primo
    }
}
