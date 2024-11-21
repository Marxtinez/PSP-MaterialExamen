package TEMA_2.A2_4;

import java.util.Scanner;

public class E1_RepartoCicico {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Cantidad de trabajo: ");
        int n = sc.nextInt();

        System.out.print("Cantidad de hilos: ");
        int m = sc.nextInt();

        for (int i = 0; i < m; i++) {
            Thread thread = new Thread(new ProcesoC(n, m, i), "Hilo " + i);
            thread.start();
        }

        sc.close();
    }

}
class ProcesoC implements Runnable {
    private int n;
    private int m;
    private int hiloId;

    public ProcesoC(int n, int m, int hiloID) {
        this.n = n;
        this.m = m;
        this.hiloId = hiloID;
    }
    @Override
    public void run() {
        for (int i = hiloId; i < n; i += m) {
            System.out.println("-"+Thread.currentThread().getName()+": " + "Proceso: " + i + ", Cuadrado: " + i*i);
        }
    }
}


