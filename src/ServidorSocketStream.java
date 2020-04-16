
import java.io.*;

import java.net.*;

public class ServidorSocketStream {
	public static void main(String[] args) throws ClassNotFoundException {
		boolean end = false;
		String prizeNum = Integer.toString((int) (Math.random() * (99999 - 1) + 1));
		prizeNum = ("00000" + prizeNum).substring(prizeNum.length());
		String boleto;

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
			System.out.println("---- Numero Ganador es : " + prizeNum + " ----");
			oos.writeUTF(prizeNum);
			oos.flush();

			while (!end) {
				Numero numObj = (Numero) ois.readObject();
				boleto = numObj.getBoleto();
				if (boleto.equals("00000")) {
					end = true;
					System.out.println("Loteria Acabada");
				} else {
					// Casos de premio
					if (boleto.equals(prizeNum)) {
						numObj.setPremio("Premio Gordo");
					} else if ((Integer.parseInt(boleto) == (Integer.parseInt(prizeNum) - 1))) {
						numObj.setPremio("Numero Anterior");
					} else if ((Integer.parseInt(boleto) == (Integer.parseInt(prizeNum) + 1))) {
						numObj.setPremio("Numero Posterior");
					} else if (boleto.substring(2).equals(prizeNum.substring(2))) {
						numObj.setPremio("Centenas");
					} else if (boleto.substring(3).equals(prizeNum.substring(3))) {
						numObj.setPremio("Dos Ultimas Cifras");
					} else if (boleto.charAt(boleto.length() - 1) == prizeNum.charAt(prizeNum.length() - 1)) {
						numObj.setPremio("Reintegro");
					} else {
						numObj.setPremio("Nada");
					}
					System.out.println(numObj.toString());
					oos.writeObject(numObj);
					oos.flush();
				}
			}
			oos.close();
			ois.close();
			serverSocket.close();
			newSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}