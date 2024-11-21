package TEMA_2.A2_1;

public class Ej01Threads {
    public static void main(String[] args) throws InterruptedException {

        /*A1_2.MiTareaT proceso = new A1_2.MiTareaT();

        Thread[] threadArray = {new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso)};

        for (Thread thread : threadArray) {
            thread.start();
        }

         */

        MiTareaT[] tareas = {new MiTareaT(), new MiTareaT(),new MiTareaT(),new MiTareaT(),new MiTareaT(),new MiTareaT(),new MiTareaT(),new MiTareaT()};

        for(MiTareaT t : tareas){
            t.start();
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("Bucle vuelta: "+i);
            Thread.sleep(1000);


        }

        for(MiTareaT t : tareas){
            t.join();
        }

        System.out.println("Fin del programa.");
    }
}
class MiTareaT extends Thread {

    public void run(){
        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo ID: "+Thread.currentThread().getId()+" Contador: "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}