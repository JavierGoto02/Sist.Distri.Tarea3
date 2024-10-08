import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
/*
 * Acepta conexiones de clientes y lo delega a un hilo
 * esta clase manejara los usuarios en linea
 */


public class TCPMultiServer {

	//variables compartidas
	boolean listening = true;
    List<Persona> usuariosOnline; // Lista de usuarios en linea
    RedisDB dataBase = new RedisDB();  // Guarda usuarios existentes

    public void ejecutar() throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("No se puede abrir el puerto: 4444.");
            System.exit(1);
        }
        System.out.println("Puerto abierto: 4444.");

        while (listening) {
        	
        	TCPServerHilo hilo = new TCPServerHilo(serverSocket.accept(), this);
            hilo.start();
        }

        serverSocket.close();
    }

    // Muestra los usuarios conectados
    public void mostrarUsuariosConectados(PrintWriter out) throws IOException
    {
        for(Persona p : usuariosOnline)
            out.println("* " + p);
    }

    // Muestra los usuarios en la base de datos
    public void mostrarUsuarios(PrintWriter out, List<Integer> usersId) throws IOException
    {
        for(Integer id : usersId)
            out.println("* " + dataBase.recuperarPersona(id));
    }

    public void desconectarUsuario(Persona p) {usuariosOnline.remove(p);}
    
    public static void main(String[] args) throws IOException {
    	
    	TCPMultiServer tms = new TCPMultiServer();
        tms.usuariosOnline = new ArrayList<Persona>();
    	tms.ejecutar();
    	
    }
}
