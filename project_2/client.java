import java.net.*;
import java.io.*;
import javax.net.ssl.*;
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
    client cl = new client();
    cl.connect(args); //skapar förbindelse med server

    while(true){ 
      cl.sendReq(); //skickar förfrågningar till servern
      cl.recieveRes(); //tar emot svar från servern
      break; //behöver lägga till nånting som bryter loopen, kanske kan sendReq returnera en boolean?
    }


    cl.socket.close(); //stänger ner
  }

  private void connect(String[] args){
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
        ks.load(new FileInputStream("./certificates/users/doc1keystore"), password);
        // truststore password (storepass);
        ts.load(new FileInputStream("./certificates/users/doc1keystore"), password);
        kmf.init(ks, password); // user password (keypass)
        tmf.init(ts); // keystore can be used as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        factory = ctx.getSocketFactory();
      } catch (Exception e) {
        throw new IOException(e.getMessage());
      }
      socket = (SSLSocket) factory.createSocket(host, port);
      System.out.println("\nsocket before handshake:\n" + socket + "\n");

      /*
       * send http request
       *
       * See SSLSocketClient.java for more information about why
       * there is a forced handshake here when using PrintWriters.
       */

      socket.startHandshake();
  } catch (Exception e) {
    e.printStackTrace();
  }

}

  private void sendReq() throws IOException{
    BufferedReader in = null;
    ObjectOutputStream out = null;
    BufferedReader read = null;
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    read = new BufferedReader(new InputStreamReader(System.in));
    out = new ObjectOutputStream(socket.getOutputStream());
    System.out.println("For which patient do you want to operate?" + "/n" + ">");
    String patient = read.readLine();
    String msg;
    Request request;
    System.out.print("What do you want to do?" + "/n" + 
        "Press 1 to create a file, 2 to write to a file, 3 to read a file, 4 to delete a file and 9 to quit" + "/n" +
        ">");
        msg= read.readLine();
        if (msg.equalsIgnoreCase("quit")) {
          //break; // fixa denna
        }
        switch(Integer.parseInt(msg)){ //här behöver vi inte göra så mycket mer än att välja metod, när vi fått access gör vi resten
          //create new file
          case 1:
          System.out.println("Which nurse do you want to associate with the log?" + "/n" + ">");
          String nurse = read.readLine(); //hade velat söka bland våra nurses och se att namnet finns, då kan vi koppla till en nurse ist för string också
          System.out.println("Log entry:" + "/n" + ">");
          String comment = read.readLine();
          request = new Request(patient, "create", nurse); 
          out.writeObject(request);
          break;

          //write to existing file
          case 2:
          System.out.println("Path to desired file: ");
          String path = read.readLine();
          Request readReq = new Request(patient, "read", path);
          out.writeObject(readReq); //dessa två rader är för att man ska kunna läsa vad som står i filen innan vi skriver på
          System.out.println(in.readLine()); //behövs nånting som ser till att vi bara läser filer vi sen kan skriva till
          System.out.println("What do you want to add to the file?" + "/n" + ">");
          comment = read.readLine(); //borde inte comment behöva initeras? den initieras ju inuti ett annat case?
          request = new Request(patient, "write",  path);
          out.writeObject(request);
          break;
          
          //read file
          case 3:
          request = new Request(patient, "read"); //tänker nu att alla logs som user har access till skrivs ut, kanske ska välja fil?
          out.writeObject(request);

          break;
          
          //delete file
          case 4:
          System.out.println("Path to file that is to be deleted:");
          path = read.readLine();
          request = new Request(patient, path); 
          out.writeObject(request);

          break;

          case 9:

          break;

          default:
          System.out.println("Please enter enter one of the allowed numbers!");
          //Här vill vi skicka tillbaka användaren så den får välja om

        }
  
}

  private void recieveRes(){
    try {
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      Response response = (Response)in.readObject();
      System.out.println(response.getStatus()); //skriver ut status på response, dvs om den lyckats eller ej
      // TODO code to write out file if we wanted to read

    } catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
