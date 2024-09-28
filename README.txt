Pasos para probar el programa:
1) Descargar e iniciar docker desktop de https://www.docker.com/products/docker-desktop/
2) Abrir la terminal en el directorio "tarea3/Servidor"
3) ejecutar los siguientes comandos:
	-docker-compose build
	-docker-compose up -d
	-java -jar target/Servidor-1.0-SNAPSHOT.jar
4) Abrir otra terminar en el directorio "tarea3/Cliente"
5) Ejecutar el comandos:
	-java -jar target/Cliente-1.0-SNAPSHOT.jar
	