import java.net.*;
import java.util.Iterator;
import java.io.*;
/*
 * En esta clase se maneja la comunicacion con un cliente independientemente
 * hay que implementar el login y el menu que le permita al usuario
 * ver la lista de usuarios en linea y agregar a la base de datos si es necesario
 */

public class TCPServerHilo extends Thread
{

    private Socket socket = null;
    TCPMultiServer servidor;
    // Metodos: almacenarPersona(Persona persona), recuperarPersona(Long cedula)

    
    public TCPServerHilo(Socket socket, TCPMultiServer servidor) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    private void crearCuenta(PrintWriter out, BufferedReader in)
    {
        try
        {
            out.print("Ingrese el nombre:");
            String nombre = in.readLine();
            out.print("Ingrese el apellido:");
            String apellido = in.readLine();
            out.print("Ingrese la cedula:");
            Integer cedula = Integer.getInteger(in.readLine());
            out.print("Ingrese el password");
            String password = in.readLine();
            servidor.dataBase.almacenarPersona(new Persona(cedula, nombre, apellido, password));
            out.println("Creo su cuenta correctamente!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iniciarSesion(PrintWriter out, BufferedReader in)
    {
        try
        {
            out.print("Ingrese la cedula: ");
            Integer cedula = Integer.getInteger(in.readLine());
            Persona personaIngresada = servidor.dataBase.recuperarPersona(cedula);
            out.print("Ingresar el password: ");
            String password = in.readLine();
            if(personaIngresada.getPassword().equals(password)) {
                out.println("Inicio sesion correctamente!!");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));
            out.println("Bienvenido!");
            out.println("1) Crear cuenta\n 2) Iniciar sesion");
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                if (inputLine.equals("1"))
                    crearCuenta(out, in);

                else if(inputLine.equals("2"))
                    iniciarSesion(out, in);




                
            }
            out.close();
            in.close();
            socket.close();
            System.out.println("Finalizando Hilo");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
