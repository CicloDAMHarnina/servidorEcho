import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer extends Thread {
	private static final int PORT = 8889;

	private EchoServer() {
		System.out.println("server");
	}

	//Como siempre un singleton
	public static void main(String[] args) {
		EchoServer echoServer = new EchoServer();
		if (echoServer != null) {
			echoServer.start();
		}
	}

	public void run() {
		try {
			//Esta clase escucha en el puerto definido
			ServerSocket serverSocket = new ServerSocket(PORT, 1);
			while (true) {
				//Aqui aceptamos la conexion con el cliente
				Socket client = serverSocket.accept();
				System.out.println("Cliente conectado");
				while (true) {
					//leemos lo que haya en el stream del cliente y hacemos echo sobre su flujo
					//pero en la direccion de servidor a cliente
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(client.getInputStream()));
					System.out.println("Leyendo del cliente");
					String textLine = reader.readLine() + "\n";
					if (textLine.equalsIgnoreCase("EXIT\n")) {
						System.out.println("SALIDA invocada cerrando cliente");
						break;
					}
					//reader siempre es donde leo y writer donde escribo pero es 
					//contextual cuando en el cliente hago referencia a socket creo una
//					nueva instancia pero aqui lo que hago es aceptar el cliente
					BufferedWriter writer = new BufferedWriter(
							new OutputStreamWriter(client.getOutputStream()));
					System.out.println("echo input to client");
					writer.write("Echo del servidor" + textLine, 0,
							textLine.length() + 17);
					writer.flush();
				}
				client.close();
				//Ahora debemos hacer una aplicacion en android que se conecte a este programa
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
