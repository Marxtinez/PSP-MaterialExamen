package TEMA_3.A3_4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servidor {
    final static int PUERTO = 9876;
    public static void main(String[] args) {
        try {

            DatagramSocket ds = new DatagramSocket(PUERTO);
            byte[] buf;
            DatagramPacket dpRecieve;
            DatagramPacket dpSend;

            while(true){

                buf = new byte[64];
                dpRecieve = new DatagramPacket(buf, buf.length);
                System.out.println("Servidor escuchando");
                ds.receive(dpRecieve);
                String msgCliente = new String(dpRecieve.getData(), 0, dpRecieve.getLength());
                System.out.println("Paquete recibido: " + msgCliente);

                String[] partes = msgCliente.split(":");
                String resultado = "";

                switch(partes[0]){
                    case "SUMA":
                        resultado = ""+(Integer.parseInt(partes[1])+Integer.parseInt(partes[2]));
                        break;
                    case "RESTA":
                        resultado = ""+(Integer.parseInt(partes[1])-Integer.parseInt(partes[2]));
                        break;

                }

                buf = resultado.getBytes();

                dpSend = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(),dpRecieve.getPort());
                ds.send(dpSend);
                System.out.println("Resultado ("+resultado+") enviado al cliente");


            }


        } catch (SocketException e) {
            System.out.println("Error de socket: "+e.getMessage());;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
