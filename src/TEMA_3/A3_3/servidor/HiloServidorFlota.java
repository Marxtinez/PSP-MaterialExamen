package TEMA_3.A3_3.servidor;

import TEMA_3.A3_3.comun.MyStreamSocket;
import TEMA_3.A3_3.partida.Partida;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del juego Hundir la flota.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

class HiloServidorFlota implements Runnable {
   MyStreamSocket myDataSocket;
   private Partida partida = null;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 */
   HiloServidorFlota(MyStreamSocket myDataSocket) {
	   this.myDataSocket = myDataSocket;	   
   }
 
   /**
	* Gestiona una sesion con un cliente	
   */
   public void run() {
      boolean done = false;
      int operacion = 0;
      // ...
      try {
         while (!done) {
        	 String[] mensaje = myDataSocket.receiveMessage().split("#");
     
        	 // Recibe una peticion del cliente
        	 // Extrae la operación y los argumentos
        	 operacion = Integer.parseInt(mensaje[0]);
             switch (operacion) {
             case 0:  // fin de conexión con el cliente - cerrar el socket y finalizar el programa
                 myDataSocket.close();
                 done = true;
                 break;

             case 1: { // Crea nueva partida
                 //TODO
                 partida = new Partida(Integer.parseInt(mensaje[1]), Integer.parseInt(mensaje[2]), Integer.parseInt(mensaje[3]));
                 break;
             }             
             case 2: { // Prueba una casilla y devuelve el resultado al cliente
                 int resultado = partida.pruebaCasilla(Integer.parseInt(mensaje[1]), Integer.parseInt(mensaje[2]));
                 myDataSocket.sendMessage(""+resultado);
                 break;
             }
             case 3: { // Obtiene los datos de un barco y se los devuelve al cliente
                 //TODO
                 String resultado = partida.getBarco(Integer.parseInt(mensaje[1]));
                 myDataSocket.sendMessage(resultado);
                 break;
             }
             case 4: { // Devuelve al cliente la solucion en forma de vector de cadenas
        	   // Primero envia el numero de barcos 
            	
            	 String[] barcos = partida.getSolucion();
            	 String numBarcos = ""+barcos.length;
            	 myDataSocket.sendMessage(numBarcos);
            	 
            	 for(String bar: barcos) {
            		 myDataSocket.sendMessage(bar);
            	 }
            	 
               // Despues envia una cadena por cada barco
               break;
             }
         } // fin switch
       } // fin while   
     } // fin try
     catch (Exception ex) {
        System.out.println("Exception caught in thread: " + ex);
     } // fin catch
   } //fin run
   
} //fin class 
