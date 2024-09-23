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
    Persona personaLogueada;
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
            out.println("Ingrese el nombre:\n" + "");
            String nombre = in.readLine();

            out.println("Ingrese el apellido:\n" + "");
            String apellido = in.readLine();

            out.println("Ingrese la cedula:\n" + "");
            Integer cedula = Integer.parseInt(in.readLine());

            out.println("Ingrese el password:\n" + "");
            String password = in.readLine();


            Persona personaCreada = new Persona(cedula, nombre, apellido, password);
            personaLogueada = personaCreada;
            servidor.dataBase.almacenarPersona(personaCreada);

            out.println("Creo su cuenta correctamente!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean iniciarSesion(PrintWriter out, BufferedReader in)
    {
        try
        {
            out.println("Ingrese la cedula:\n" + "");
            Integer cedula = Integer.parseInt(in.readLine());

            personaLogueada = servidor.dataBase.recuperarPersona(cedula);
            if(personaLogueada == null) return false;
            out.println(personaLogueada);
            out.println("Ingrese el password:\n" + "");
            String password = in.readLine();

            if(personaLogueada.getPassword().equals(password)) {
                out.println("Inicio sesion correctamente!!");
                servidor.usuariosOnline.add(personaLogueada);
                return true;
            }
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }



    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));
            out.println("Bienvenido!");
            System.out.println("Ingreso un cliente");

            String inputLine, outputLine;

            while (true)
            {
                out.println("1) Crear cuenta");
                out.println("2) Iniciar sesion");
                out.println("3) Salir del servidor\n" + "");
                inputLine = in.readLine();
                System.out.println("Mensaje recibido: " + inputLine);
                if(inputLine.equals("1"))
                    crearCuenta(out, in);

                else if(inputLine.equals("2")){
                    boolean inicio = iniciarSesion(out, in);
                    while(inicio){
                        out.println("1) Mostrar usuarios en linea");
                        out.println("2) Terminar sesion\n" + "");
                        inputLine = in.readLine();
                        if(inputLine.equals("1"))
                            servidor.mostrarUsuariosConectados(out);

                        else if(inputLine.equals("2"))
                        {
                            servidor.desconectarUsuario(personaLogueada);
                            personaLogueada = null;
                            break;
                        }
                    }
                }

                else if(inputLine.equals("3"))
                    break;

                
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
