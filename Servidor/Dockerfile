# Usa una imagen base de Java
FROM openjdk:11-jre-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado por Maven a la imagen Docker
COPY target/Servidor-1.jar /app/Servidor-1.jar

# Expone el puerto donde el servidor escucha
EXPOSE 4444

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "Servidor-1.jar"]
