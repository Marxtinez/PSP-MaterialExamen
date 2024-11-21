package TEMA_3.A3_2.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    Socket socket = null;
    DataInputStream fentrada;
    DataOutputStream fsalida;
    public Cliente(Socket s) {
        socket = s;
        try {
            fentrada = new DataInputStream(socket.getInputStream());
            // CREO FLUJO DE SALIDA AL socket de escritura
            fsalida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void enviaMensaje(String mensaje) {
        try {
            fsalida.writeUTF(mensaje);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void ejecutar() {
        Boolean repetir = true;
        String texto = "";
        while (repetir) {
            try {
                texto = fentrada.readUTF();
                if (!texto.isEmpty()) {
                    System.out.println(texto);
                    repetir = false;
                }
            } catch (IOException e) {
                // este error sale cuando el servidor se cierra
                System.out.println("ERROR DE E/S"+ e.getMessage());
                repetir = false;
            }
        }//while
    }// ejecutar

    public static void main(String[] args) {
        int puerto = 12345;
        Socket s = null;
        try {
            s = new Socket("localhost", puerto);
        } catch (IOException e) {
            System.out.println("IMPOSIBLE CONECTAR OCN EL SERVIDOR: \n" + e.getMessage());
            System.exit(0);
        }
        Cliente cliente = new Cliente(s);
        Scanner scan = new Scanner(System.in);
        int opcion = 1;
        while (opcion != 0) {
            System.out.println("Selecciona una operación: \n 1.-Suma\n 2.-Resta\n 3.-Multiplicación\n 4.-División\n 0.- Salir");
            opcion = scan.nextInt();
            switch (opcion) {
                case 1: {
                    System.out.println("Has seleccionado Suma.");
                    cliente.enviaMensaje("1#" + pideNumero() + "#" + pideNumero());
                    cliente.ejecutar();
                    break;
                }
                case 2: {
                    System.out.println("Has seleccionado Resta.");
                    cliente.enviaMensaje("2#" +pideNumero() + "#" + pideNumero());
                    cliente.ejecutar();
                    break;
                }
                case 3: {
                    System.out.println("Has seleccionado Multiplicacion.");
                    cliente.enviaMensaje("3#" + pideNumero() + "#" + pideNumero());
                    cliente.ejecutar();
                    break;
                }
                case 4: {
                    System.out.println("Has seleccionado División.");
                    cliente.enviaMensaje("4#" + pideNumero() + "#" + pideNumero());
                    cliente.ejecutar();
                    break;
                }
                case 0:
                    System.out.println("Se ha cerrado el cliente.");
                    break;
                default:
                    System.out.println("Por favor, selecciona una opcion válida.");
            }
        }
    }
    public static int pideNumero(){
        Scanner scan = new Scanner(System.in);
        boolean noValido = true;
        int n = 0;
        while (noValido){
            System.out.println("Introduce el número: ");
            try {
                n = scan.nextInt();
                noValido = false;
            } catch (Exception e) {
                System.out.println("Error: Por favor, introduce un número entero válido."); // Mensaje de error
                scan.next();
            }
        }
        return n;
    }
}
