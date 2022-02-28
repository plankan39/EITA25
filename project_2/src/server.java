
import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;

import api.Request;
import api.Response;
import api.Request.RequestType;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class server implements Runnable {
  private ServerSocket serverSocket = null;
  private static int numConnectedClients = 0;

  public server(ServerSocket ss) throws IOException {
    serverSocket = ss;
    newListener();
  }

  public void run() {
    try {
      SSLSocket socket = (SSLSocket) serverSocket.accept();
      newListener();
      SSLSession session = socket.getSession();
      Certificate[] cert = session.getPeerCertificates();
      String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
      String issuer = ((X509Certificate) cert[0]).getIssuerX500Principal().getName();
      int serialNumber = ((X509Certificate) cert[0]).getSerialNumber().intValue();
      numConnectedClients++;
      System.out.println("client connected");
      System.out.println("client name (cert subject DN field): " + subject);
      System.out.println("issuer (cert issuer DN field): " + issuer);
      System.out.println("serial number (cert serialNumber DN field): " + serialNumber);

      System.out.println(numConnectedClients + " concurrent connection(s)\n");

      // gathers the request from the client
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      Request request = (Request) in.readObject(); // behövs kanske try-catch här

      // måste lägga in hur auditen funkar
      // performs the request (if allowed) and sends back response
      sendResponse(socket, request, subject);

      socket.close(); // kanske inte ska stänga socketen här? om vi vill göra flera actions under
                      // samma inloggning vill vi slippa starta upp servern flera gånger
      numConnectedClients--;
      System.out.println("client disconnected");
      System.out.println(numConnectedClients + " concurrent connection(s)\n");
    } catch (IOException e) {
      System.out.println("Client died: " + e.getMessage());
      e.printStackTrace();
      return;
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void newListener() {
    (new Thread(this)).start();
  } // calls run()

  public static void main(String args[]) {
    System.out.println("\nServer Started\n");
    int port = -1;
    if (args.length >= 1) {
      port = Integer.parseInt(args[0]);
    }
    String type = "TLSv1.2";
    try {
      ServerSocketFactory ssf = getServerSocketFactory(type);
      ServerSocket ss = ssf.createServerSocket(port);
      ((SSLServerSocket) ss).setNeedClientAuth(true); // enables client authentication
      new server(ss);
    } catch (IOException e) {
      System.out.println("Unable to start Server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static ServerSocketFactory getServerSocketFactory(String type) {
    if (type.equals("TLSv1.2")) {
      SSLServerSocketFactory ssf = null;
      try { // set up key manager to perform server authentication
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        char[] password = "password".toCharArray();
        // keystore password (storepass)
        ks.load(new FileInputStream("./certificates/serverkeystore"), password);
        // truststore password (storepass)
        ts.load(new FileInputStream("./certificates/serverkeystore"), password);
        kmf.init(ks, password); // certificate password (keypass)
        tmf.init(ts); // possible to use keystore as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ssf = ctx.getServerSocketFactory();
        return ssf;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      return ServerSocketFactory.getDefault();
    }
    return null;
  }

  private void sendResponse(SSLSocket socket, Request request, String subject) throws IOException {
    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    RequestType reqType = request.getReq();
    Response response;

    // kolla division och typ av user här

    // if-sats med hjälpmetod/klass som kollar om user har tillåtelse att utföra sin
    // request
    if (true) {
      switch (reqType) {
        case CREATE:
          response = createFile();
          break;

        case WRITE:
          response = writeFile();
          break;

        case READ:
          response = readFile(request.getLog());
          break;

        case DELETE:
          response = deleteFile(request.getLog());
          break;

        default:
          response = new Response("Request failed");
      }
    } else {
      response = new Response("You don't have access for this request");
    }

    out.writeObject(response);

  }

  private Response createFile() {

    return new Response("test");
  }

  private Response writeFile() {

    return new Response("test");
  }

  private Response readFile(File log) throws FileNotFoundException {
    return new Response("Request excecuted", log);
  }

  private Response deleteFile(File log) {
    if (log.delete()) {
      return new Response("Request excecuted");
    } else {
      return new Response("Request failed");
    }
  }
}
