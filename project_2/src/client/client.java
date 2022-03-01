package client;

import java.io.*;
import javax.net.ssl.*;

import api.request.CreateLogRequest;
import api.request.DeleteRequest;
import api.request.LoginRequest;
import api.request.ReadLogRequest;
import api.request.Request;
import api.request.WriteLogRequest;
import api.response.Response;

import java.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.cert.*;

/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */

public class client {

  private SSLSocket socket;

  public static void main(String[] args) throws Exception {
    String host = null;
    int port = -1;
    for (int i = 0; i < args.length; i++) {
      System.out.println("args[" + i + "] = " + args[i]);
    }
    if (args.length < 2) {
      System.out.println("USAGE: java client host port");
      System.exit(-1);
    }
    try { /* get input parameters */
      host = args[0];
      port = Integer.parseInt(args[1]);
    } catch (IllegalArgumentException e) {
      System.out.println("USAGE: java client host port");
      System.exit(-1);
    }

    try {
      SSLSocketFactory factory = null;
      try {
        char[] password = "password".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        // keystore password (storepass);
        ks.load(new FileInputStream("../certificates/users/doc1keystore"), password);
        // truststore password (storepass);
        ts.load(new FileInputStream("../certificates/users/doc1keystore"), password);
        kmf.init(ks, password); // user password (keypass)
        tmf.init(ts); // keystore can be used as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        factory = ctx.getSocketFactory();
      } catch (Exception e) {
        throw new IOException(e.getMessage());
      }
      SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
      System.out.println("\nsocket before handshake:\n" + socket + "\n");

      /*
       * send http request
       *
       * See SSLSocketClient.java for more information about why
       * there is a forced handshake here when using PrintWriters.
       */

      socket.startHandshake();
      SSLSession session = socket.getSession();
      Certificate[] cert = session.getPeerCertificates();
      String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
      String issuer = ((X509Certificate) cert[0]).getIssuerX500Principal().getName();
      System.out.println("certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
      System.out.println("issuer (cert issuer DN field): " + issuer);
      System.out.println("socket after handshake:\n" + socket + "\n");
      System.out.println("secure connection established\n\n");

      // BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
      Console read = System.console();
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      String userName;
      Response response;
      do {
        System.out.println("Login");
        userName = read.readLine("Username: ");
        String pw = new String(read.readPassword("Password: "));
        Request login = new LoginRequest(userName, pw);

        System.out.println(((LoginRequest) login).userName + " " + ((LoginRequest) login).password);

        out.writeObject(login);
        out.flush();
        response = (Response) in.readObject();

        if (!response.granted) {
          System.out.println("Login failed");
        } else {
          System.out.println("Login successful");
        }
      } while (!response.granted);
      String options = "What do you want to do?" + "\n" +
          "Press 1 to create a file, 2 to write to a file, " +
          "3 to read a file, 4 to delete a file and 'quit' to quit"
          + "\n" + ">";
      String msg;
      do {

        System.out.print(options);

        msg = read.readLine();
        while (!validInputAction(msg) || msg.equalsIgnoreCase("quit")) {
          System.out.println("Not a valid action. Please choose an action between 1-4 or type 'quit' to quit");
          msg = read.readLine();
        }

        switch (msg) {
          case "1":
            System.out.print("Name of patient: ");
            String patientName = read.readLine();
            int patientSSN;
            try {
              patientSSN = Integer.parseInt(read.readLine("Social security number of patient: "));
            } catch (NumberFormatException e) {
              System.out.println("not a valid social security number");
              break;
            }
            System.out.print("\nUsername of the nurse to be associated with the log?: ");
            String nurse = read.readLine();

            System.out.print("\nLog Entry: ");
            String log = read.readLine();

            Request create = new CreateLogRequest(patientName, patientSSN, nurse, log);
            out.writeObject(create);
            out.flush();

            Response r = (Response) in.readObject();

            if (r.granted) {
              System.out.println("Hurra filen skapad");
            }

            break;

          case "2":
            try {
              int pSSN = Integer.parseInt(read.readLine("Social security number of patient: "));
              long lnbr = Integer.parseInt(read.readLine("Lognbr(write -1 to show all): "));
              String input = read.readLine("Log Entry: ");
              out.writeObject(new WriteLogRequest(pSSN, input, lnbr));
              out.flush();
              Response r2 = (Response) in.readObject();
              System.out.println(r2.granted);
            } catch (NumberFormatException e) {
              System.out.println("not a valid social security number or lognbr");
              break;
            }
            break;

          case "3":
            try {
              int pSSN = Integer.parseInt(read.readLine("Social security number of patient: "));
              long lnbr = Integer.parseInt(read.readLine("Lognbr(write -1 to show all): "));
              out.writeObject(new ReadLogRequest(pSSN, lnbr));
              out.flush();
              Response r2 = (Response) in.readObject();
              System.out.println(r2.granted + r2.log);
            } catch (NumberFormatException e) {
              System.out.println("not a valid social security number or lognbr");
              break;
            }

            break;

          case "4":
            try {
              int pSSN = Integer.parseInt(read.readLine("Social security number of patient: "));
              long lnbr = Integer.parseInt(read.readLine("Lognbr(write -1 to show all): "));
              out.writeObject(new DeleteRequest(pSSN, lnbr));
              out.flush();
              Response r2 = (Response) in.readObject();
              System.out.println(r2.granted);
            } catch (NumberFormatException e) {
              System.out.println("not a valid social security number or lognbr");
              break;
            }
            break;

          default:
            break;
        }

      } while (!msg.equals("quit"));

      in.close();
      out.close();
      // read.close();
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static boolean validInputAction(String s) {
    if (s.length() != 1 || !Character.isDigit(s.charAt(0))) {
      return false; // Check if the string is empty
    }
    return true;
  }
}
