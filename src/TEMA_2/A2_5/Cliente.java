package TEMA_2.A2_5;

public class Cliente implements Runnable{
    private Tienda tienda;

    private int cantidad;
    public Cliente(Tienda tienda, int cantidad) {
        this.tienda = tienda;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        tienda.comprarProducto(cantidad);
    }
}
