package TEMA_3.A3_2.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Servidor {
    static ServerSocket servidor;
    static final int PUERTO = 12345;// puerto por el que escucho
    static int CONEXIONES = 0;
    static int ACTUALES = 0;
    static int MAXIMO=4;
    static Socket[] tabla = new Socket[10];//para controlar las conexiones
    public static void main(String[] args) throws IOException {
        servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor conectado");
        while (CONEXIONES < MAXIMO) {
            Socket s = new Socket();
            try {
                s = servidor.accept();// esperando cliente
                System.out.println("Cliente conectado.");
            } catch (SocketException ns) {
                //sala por aqui si pulsamos boton salir y no se ejecuta todo el bucle
                break;
            }// sale cuando
            tabla[CONEXIONES] = s;
            CONEXIONES++;
            ACTUALES++;
            HiloServidor hilo = new HiloServidor(s);
            hilo.start();
        }
    }
}
