package TEMA_2.A2_4;

import java.util.Scanner;

public class E2_RepartoBloques {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Cantidad de trabajo: ");
        int n = sc.nextInt();

        System.out.print("Cantidad de hilos: ");
        int m = sc.nextInt();

        System.out.println("--_COMIENZA EL REPARTO POR BLOQUES_--");
        for (int i = 0; i < m; i++) {
            Thread thread = new Thread(new ProcesoB(n, m, i), "Hilo " + i);
            thread.start();
        }

        sc.close();
    }

}

class ProcesoB implements Runnable {
    private int n;
    private int m;
    private int hiloId;

    private int blockSize;
    private int ini;
    private int fin;


    public ProcesoB(int n, int m, int hiloID) {
        this.n = n;
        this.m = m;
        this.hiloId = hiloID;
        this.blockSize = (n + m -1)/m;
        this.ini = blockSize*hiloId;
        this.fin = Math.min(ini + blockSize, n);
    }
    @Override
    public void run() {
        for (int i = ini; i < fin; i++) {
            System.out.println("-"+Thread.currentThread().getName()+": " + "Proceso: " + i + ", Cuadrado: " + i*i);

        }
    }
}
