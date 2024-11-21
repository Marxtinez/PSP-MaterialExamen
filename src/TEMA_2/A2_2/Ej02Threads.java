package TEMA_2.A2_2;

public class Ej02Threads {

    public static void main(String[] args) {
        long ti = System.currentTimeMillis();

            ClaseHilo[] miClaseHilo = {new ClaseHilo(1,10000)
                    ,new ClaseHilo(100001, 200000)
                    ,new ClaseHilo(200001, 300000)
                    ,new ClaseHilo(300001, 400000)
            };
        Thread[] threadArray = {new Thread(miClaseHilo[0]),new Thread(miClaseHilo[1]),new Thread(miClaseHilo[2]),new Thread(miClaseHilo[3])};

        for (int i = 0; i < threadArray.length; i++) {
                threadArray[i].start();
            }
        try{
            for (int i = 0; i < threadArray.length; i++) {
            threadArray[i].join();}
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Tiempo total de ejecución: "+(System.currentTimeMillis() - ti)/1000);
        }
    }
class ClaseHilo implements Runnable{

    int inicio, fin;

    public ClaseHilo(int inicio, int fin){
        this.inicio = inicio;
        this.fin = fin;
    }
    @Override
    public void run() {
        for(int i = inicio; i <= fin; i++){
        if(Ej02Secuencial.esPrimo(i)){
            System.out.println(i+" es primo.");
            }else{
            System.out.println(i+" no es primo.");
        }
        }
    }
}
