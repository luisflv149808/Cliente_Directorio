package clientedir;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteDir {

    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket serverSocket = null;

        if (args.length == 3) {

            if (Metodos.ValidarIP(args[0])) {
                if (Metodos.Numerico(args[1])) {

                    try {
                        if (args[1] != null) {
                            socket = new Socket(args[0].toString(), Integer.parseInt(args[1]));
                        } else {
                            System.out.println("Puerto vacio");
                        }

                    } catch (IOException e) {
                        System.out.println("error al crear el socket " + e.toString());
                        System.exit(1);
                    }
                    PrintWriter escritor = null;

                    try {
                        escritor = new PrintWriter(
                                socket.getOutputStream(), true);
                        escritor.println(args[2]);
                    } catch (IOException e) {
                        System.out.println("Error al mandar info " + e.toString());
                        System.exit(2);
                    }

                    BufferedReader lector = null;
                    try {
                        lector = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                    } catch (IOException ex) {
                        System.out.println("Error al leer mensaje " + ex);
                    }

                    String mensajeentrada;

                    try {
                        System.out.println("Ruta: " + args[2] + "\n");
                        while ((mensajeentrada = lector.readLine()) != null) {

                            System.out.println(mensajeentrada);

                        }
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex);
                    }
                    try {
                        socket.getOutputStream().flush();
                        socket.getOutputStream().close();
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("Error al cerrar" + e);
                        System.exit(3);
                    }

                } else {
                    System.out.println("!!Error!!. El puerto debe ser númerico (Argumento 2)");
                }
            } else {
                System.out.println("Ingrese una dirección ip correcta");
                System.exit(0);
            }

        } else {

            System.out.println("!!Error!!. Ingresa todos los argumentos | Dirección IP-Puerto-Ruta");
        }

    }

}
