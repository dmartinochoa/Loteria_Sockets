
import java.io.*;

import java.net.*;
import java.util.Scanner;

public class ClienteSocketStream {
	public static void main(String[] args) throws ClassNotFoundException {
		boolean end = false; // Se volvera true cuando el boleto sea 00000
		String boleto;
		try {
			Socket clientSocket = new Socket();
			System.out.println("Estableciendo La Conexión");
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			clientSocket.connect(addr);
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

			// Empieza la loteria
			System.out.println("---- Introduce tu boleto ---- ");
			Scanner myObj = new Scanner(System.in);

			while (!end) {
				boleto = myObj.nextLine();
				boleto = lengthCheck(myObj, boleto);
				boleto = ("00000" + boleto).substring(boleto.length()); // 0's a la izquierda
				Numero numObj = new Numero(boleto);
				oos.writeObject(numObj);
				oos.flush();
				if (boleto.equals("00000")) {
					end = true;
					System.out.println("Loteria Acabada");
				} else {
					numObj = (Numero) ois.readObject();
					System.out.println(numObj.toString());
				}

			}

			oos.close();
			ois.close();
			clientSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String lengthCheck(Scanner myObj, String boleto) {
		if (boleto.length() > 5) {
			System.out.println("Numero Invalido, debe tener 5 cifras o menos");
			while (boleto.length() > 5) {
				System.out.println("---- Introduce tu boleto ---- ");
				boleto = myObj.nextLine();
			}
		}
		return boleto;
	}

}