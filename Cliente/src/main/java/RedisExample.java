import redis.clients.jedis.Jedis;

public class RedisExample {
    public static void main(String[] args) {
        // Conectarse al servidor Redis en localhost y el puerto 6379
        Jedis jedis = new Jedis("localhost", 6379);

        // Guardar datos en Redis
        jedis.set("cliente:1", "nombre=Javier,edad=30");

        // Obtener los datos del cliente
        String clienteData = jedis.get("cliente:1");
        System.out.println("Datos del cliente: " + clienteData);

        // Cerrar la conexión
        jedis.close();
    }
}
