
import java.io.*;

import java.net.*;
import java.util.Scanner;

public class ClienteSocketStream {
	public static void main(String[] args) throws ClassNotFoundException {
		boolean end = false;
		String boleto;
		String prizeNum;
		try {
			Socket clientSocket = new Socket();
			System.out.println("Estableciendo La Conexión");
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			clientSocket.connect(addr);
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

			prizeNum = ois.readUTF();
			System.out.println("---- Numero Ganador es : " + prizeNum + " ----");
			Scanner myObj = new Scanner(System.in);

			// Empieza la loteria
			while (!end) {
				boleto = askForNum(myObj);
				Numero numObj = new Numero(boleto);
				oos.writeObject(numObj);
				oos.flush();

				if (boleto.equals("00000")) {
					end = true;
					System.out.println("Loteria Acabada");
				} else {
					numObj = (Numero) ois.readObject();
					System.out.println(numObj.toString() + "; El Numero Ganador era " + prizeNum);
				}
			}
			oos.close();
			ois.close();
			clientSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String askForNum(Scanner myObj) {
		System.out.println("---- Introduce tu boleto ---- ");
		String boleto = myObj.nextLine();
		while (boleto.length() > 5 || boleto.length() == 0 || boleto.matches("[0-9]+") == false) {
			System.out.println("Numero Invalido, solo puedo tener numeros entre 1 y 5 cifras");
			System.out.println("---- Introduce tu boleto ---- ");
			boleto = myObj.nextLine();
		}
		// 0's a la izquierda del numero cuando no llegue a 5 cifras
		boleto = ("00000" + boleto).substring(boleto.length());
		return boleto;
	}
}