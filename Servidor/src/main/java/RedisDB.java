import redis.clients.jedis.Jedis;

import java.util.Set;

/* Esta clase se encarga de la conexión con la base de datos Redis
   agregar Persona y recuperar Persona  
*/
public class RedisDB {
    private Jedis jedis;
    private String host = "localhost";
    private int port = 6379;
    // Constructor que establece la conexión
    public RedisDB() {
        jedis = new Jedis(this.host, this.port);
    }

    // Método para almacenar una persona
    public void almacenarPersona(Persona persona) {
        String key = "persona:" + persona.getCedula(); // Crear una clave única
        jedis.hset(key, "nombre", persona.getNombre());
        jedis.hset(key, "apellido", persona.getApellido());
        jedis.hset(key, "password", persona.getPassword());
        System.out.println("Persona almacenada en Redis: " + key);
    }

    // Método para recuperar una persona
    public Persona recuperarPersona(Integer cedula) {
        String key = "persona:" + cedula;
        System.out.println("Persona recuperada: " + key);
        String nombre = jedis.hget(key, "nombre");
        String apellido = jedis.hget(key, "apellido");
        String password = jedis.hget(key, "password");
        System.out.println(nombre + " " + apellido + " " + password);

        if (nombre != null && apellido != null && password != null) {
            return new Persona(cedula, nombre, apellido, password);
        }
        return null; // Persona no encontrada
    }

    public void listarUsuariosOnline()
    {
        Set<String> claves = jedis.keys("*");
        System.out.println("Clientes conectados:");
        for(String clave : claves)
        {
            String nombre = jedis.hget(clave, "nombre");
            System.out.println(clave + " -> " + nombre);
        }
    }


    // Método para cerrar la conexión
    public void cerrar() {
        if (jedis != null) {
            jedis.close();
            System.out.println("Conexión cerrada.");
        }
    }
}
