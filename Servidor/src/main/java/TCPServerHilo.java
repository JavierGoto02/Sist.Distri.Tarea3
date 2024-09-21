import java.net.*;
import java.util.Iterator;
import java.io.*;
/*
 * En esta clase se maneja la comunicacion con el cliente
 * hay que implementar el login y el menu que le permita al usuario
 * ver la lista de usuarios en linea y agregar a la base de datos si es necesario
 */
public class TCPServerHilo extends Thread {

    private Socket socket = null;
    TCPMultiServer servidor;
    // Metodos: almacenarPersona(Persona persona), recuperarPersona(Long cedula)
    RedisDB dataBase = new RedisDB();  // Instancia de la base de datos
    
    public TCPServerHilo(Socket socket, TCPMultiServer servidor ) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));
            out.println("Bienvenido!");
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                //Aca se debe implementar el login y el menu
                out.println("Hola soy el servidor");
            }
            out.close();
            in.close();
            socket.close();
            dataBase.cerrar();
            System.out.println("Finalizando Hilo");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
