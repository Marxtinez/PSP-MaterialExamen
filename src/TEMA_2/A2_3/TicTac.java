package TEMA_2.A2_3;

public class TicTac {
    public static void main(String[] args) {
        Tic tic = new Tic();
        Tac tac = new Tac();

        Thread h1 = new Thread(tic);
        Thread h2 = new Thread(tac);
        h1.setPriority(Thread.MIN_PRIORITY);
        h2.setPriority(Thread.MAX_PRIORITY);
        h1.start();
        h2.start();


    }
}
    class Tic implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.print("TIC ");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    class Tac implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.print("TAC ");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
