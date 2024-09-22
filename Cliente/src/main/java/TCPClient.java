import java.io.*;
import java.net.*;
import java.util.stream.Stream;

public class TCPClient
{


    private static void leerEntrada(BufferedReader in, String fromServer) throws IOException
    {

        System.out.println("Servidor:");
        while(true)
        {
            fromServer = in.readLine();

            // Detener la lectura si encontramos una línea vacía
            if(fromServer.trim().isEmpty())
                break;

            System.out.println(fromServer);
        }
    }


    public static void main(String[] args) throws IOException
    {

        Socket unSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            unSocket = new Socket("localhost", 4444);
            // enviamos nosotros
            out = new PrintWriter(unSocket.getOutputStream(), true);

            //viene del servidor
            in = new BufferedReader(new InputStreamReader(unSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de I/O en la conexion al host");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer = "";
        String fromUser;

        try
        {
            while (true)
            {
                leerEntrada(in, fromServer);
                System.out.print("\nCliente:");
                fromUser = stdIn.readLine();
                System.out.println();
                out.println(fromUser);
                if (fromUser.equals("3"))
                    break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        out.close();
        in.close();
        stdIn.close();
        unSocket.close();
    }
}
