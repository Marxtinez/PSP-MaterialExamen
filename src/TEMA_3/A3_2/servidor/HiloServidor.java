package TEMA_3.A3_2.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class HiloServidor extends Thread{

    DataInputStream fentrada;
    Socket socket = null;

    public HiloServidor(Socket s) {
        socket = s;
        try {
            // CREO FLUJO DE entrada para leer los mensajes
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        }
    }// ..
    public void run() {

        while (true) {
            String cadena = "";
            try {
                cadena = fentrada.readUTF();

                    String[] opciones = cadena.split("#");
                    double resultado = realizaOperaciones(opciones);
                    devolverResultado("\nEl resultado es: " + resultado+"\n");


            } catch (Exception e) {
                System.out.println("Cliente desconectado");
                break;
            }
        }// fin while

    }//run
    private void devolverResultado(String texto) {

        try {
            DataOutputStream fsalida2 = new DataOutputStream(socket.getOutputStream());

            fsalida2.writeUTF(texto);
        } catch (SocketException se) {//hay que dejarlo
            // puede ocurrir cuando finalizo cliente
            // sale cuando un cliente ha finalizado
            System.out.println(se.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //System.out.println(" IO EXCEPTION  : " + e.getMessage());
        }
    }//EnviarMensajes

    public double realizaOperaciones(String[] opciones) {
        return switch (opciones[0]) {
            case "1" -> Integer.parseInt(opciones[1]) + Integer.parseInt(opciones[2]);
            case "2" -> Integer.parseInt(opciones[1]) - Integer.parseInt(opciones[2]);
            case "3" -> Integer.parseInt(opciones[1]) * Integer.parseInt(opciones[2]);
            case "4" -> (double) Integer.parseInt(opciones[1]) / Integer.parseInt(opciones[2]);
            default -> 0;
        };

    }
}
