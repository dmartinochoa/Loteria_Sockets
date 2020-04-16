
import java.io.*;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ServidorSocketStream {
	public static void main(String[] args) throws ClassNotFoundException {
		boolean end = false; // Se volvera true cuando el boleto sea 00000
		String prizeNum = "12345"; // generar aleatoriamente en el futuro

		try {
			ServerSocket serverSocket = new ServerSocket();
			System.out.println("Realizando El Bind");
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			serverSocket.bind(addr);
			System.out.println("Aceptando Conexiones");
			Socket newSocket = serverSocket.accept();
			System.out.println("Conexión Recibida");
			ObjectOutputStream oos = new ObjectOutputStream(newSocket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(newSocket.getInputStream());

			// Empieza la loteria
			System.out.println("Numero Ganador es : " + prizeNum);

			while (!end) {
				Numero numObj = (Numero) ois.readObject();
				System.out.println(numObj.toString());
				String boleto = numObj.getBoleto();
				if (boleto.equals("00000")) {
					end = true;
					System.out.println("Loteria Acabada");
				} else {
					if (boleto.equals(prizeNum)) {
						numObj.setPremio("Premio Gordo");
					} else {
						numObj.setPremio("Nada");
					}
					oos.writeObject(numObj);
					oos.flush();
				}

			}

			oos.close();
			ois.close();
			serverSocket.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

}