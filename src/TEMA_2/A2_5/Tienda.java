package TEMA_2.A2_5;

public class Tienda {
    private int stock;

    public Tienda(int stock) {
        this.stock = stock;
    }

    public void comprarProducto(int cantidad){

        if(cantidad<=stock){
            stock -= cantidad;
            System.out.println(Thread.currentThread().getName()+" ha comprado: "+cantidad+" productos. Stock actual: "+ stock);
        }else{ System.out.println(Thread.currentThread().getName()+" ha intentado comprar"+cantidad+" productos pero stock insuficiente");}
    }
    public int getStock() {return stock;}
}
