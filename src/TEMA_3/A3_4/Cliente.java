package TEMA_3.A3_4;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    private static final Scanner sc = new Scanner(System.in);
    final static int PUERTO = 9876;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress ip = InetAddress.getLocalHost();
            byte[] buf;
            boolean solicitar = true;
            System.out.println("\n-- Calculadora --\n");
            String op;
            String msg;
            while (solicitar) {

                System.out.println("Ingresa operación (SUMA, RESTA o EXIT para salir): ");
                op = sc.nextLine().trim().toUpperCase();
                switch (op) {
                    case "SUMA", "RESTA" -> {
                        msg = op + ":" + pideNumN("Primer número:") + ":" + pideNumN("Segundo número:");

                        buf = new byte[64];
                        buf = msg.getBytes();
                        DatagramPacket dpSend = new DatagramPacket(buf, buf.length, ip, PUERTO);

                        ds.send(dpSend);

                        buf = new byte[64];
                        DatagramPacket dpRecieve = new DatagramPacket(buf, buf.length);
                        ds.receive(dpRecieve);

                        System.out.println("El resultado es: " + new String(dpRecieve.getData(), 0, dpRecieve.getLength()));
                    }
                    case "EXIT" -> {
                        System.out.println("Cerrando el programa");
                        solicitar = false;
                    }
                    default -> System.out.print("Opción no válida. ");
                }
            }

        } catch (SocketException e) {
            System.out.println("Socket exception: " + e.getMessage());
        } catch (UnknownHostException eUH) {
            System.out.println("Unknown host: " + eUH.getMessage());

        } catch (IOException e) {
            System.out.println("Input/Output error: " + e.getMessage());
        }
    }

    private static int pideNumN(String n) {
        int num = 0;
        boolean valido = false;

        while (!valido) {
            System.out.println(n);
            try {
                String input = sc.nextLine();
                num = Integer.parseInt(input);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
            }
        }
        return num;
    }
}
