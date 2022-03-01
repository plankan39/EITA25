package server;

import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;

import api.AuditLog;
import api.request.CreateLogRequest;
import api.request.LoginRequest;
import api.request.Request;
import api.request.Request.RequestType;
import api.response.Response;
import server.patient.Patient;
import server.users.Doctor;
import server.users.Nurse;
import server.users.User;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class server implements Runnable {
  private ServerSocket serverSocket;
  private static int numConnectedClients;
  private Map<Integer, Patient> patients; // <SSN, Patient>
  private Map<String, User> users; // <uName, User>
  private AuditLog auditLog;

  public server(ServerSocket ss) throws IOException {
    serverSocket = ss;
    numConnectedClients = 0;
    patients = new HashMap<>();
    users = new HashMap<>();
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
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

      LoginRequest loginReq;
      boolean granted = false;
      User user = new User("", -1, "");
      Response loginResp;
      do {
        loginReq = (LoginRequest) in.readObject();
        if (!loginReq.userName.equalsIgnoreCase("quit")) {
          break;
        }

        String uName = loginReq.userName;
        String pw = loginReq.password;

        if (users.containsKey(uName)) {
          user = users.get(uName);
          if (user.checkPassword(pw)) {
            granted = true;
          }
        }
        loginResp = new Response(granted);
        out.writeObject(loginResp);

      } while (!granted);

      Object req;
      Response response;
      while (true) {
        if (user.getSSN() == -1) {
          break;
        }
        req = in.readObject();

        if (req instanceof CreateLogRequest) {
          CreateLogRequest cReq = (CreateLogRequest) req;

          if (!(user instanceof Doctor) || !users.containsKey(cReq.nurse)) {
            response = new Response(false);
          } else {
            Patient patient;
            if (!patients.containsKey(cReq.patientSSN)) {
              patient = new Patient(cReq.patientSSN, cReq.patientName);
              patients.put(cReq.patientSSN, patient);
            } else {
              patient = patients.get(cReq.patientSSN);
            }

            patient.addJournalEntry((Doctor) user, (Nurse) users.get(cReq.nurse), cReq.log);
            response = new Response(true);

          }
        } else if (req instanceof ReadLogRequest) {

        } else if(req instanceof )

      }

      // måste lägga in hur auditen funkar
      // performs the request (if allowed) and sends back response
      sendResponse(socket, loginReq, subject);

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

  private SSLSocket setupConnection() {
    return null;
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
