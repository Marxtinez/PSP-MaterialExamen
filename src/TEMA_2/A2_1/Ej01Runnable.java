package TEMA_2.A2_1;

public class Ej01Runnable {


        public static void main(String[] args) throws InterruptedException {

            MiTarea proceso = new MiTarea();

            Thread[] threadArray = {new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso),new Thread(proceso)};

            for (Thread thread : threadArray) {
                thread.start();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println("Bucle vuelta: "+i);
                Thread.sleep(1000);
            }

            for(Thread thread : threadArray){
                thread.join();
            }

            System.out.println("Fin del programa.");
        }
}

class MiTarea implements Runnable{

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
