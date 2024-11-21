package TEMA_2.A2_5;

public class PrincipalTienda {
    public static void main(String[] args) {
        Tienda t = new Tienda(10);

        Cliente cl1 = new Cliente(t, 5 );
        Cliente cl2 = new Cliente(t, 2 );
        Cliente cl3 = new Cliente(t, 8 );

        Thread h1 = new Thread(cl1);
        Thread h2 = new Thread(cl2);
        Thread h3 = new Thread(cl3);
        h1.setName("Thread1");
        h2.setName("Thread2");
        h3.setName("Thread3");
        h1.start();
        h2.start();
        h3.start();

    }
}
