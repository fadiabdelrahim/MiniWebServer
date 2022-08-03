import java.io.*;  // Get the Input Output libraries
import java.net.*; // Get the Java networking libraries

class ListenWorker extends Thread {    // Class definition
  Socket sock;                   // Class member, socket, local to ListnWorker.
  ListenWorker (Socket s) {sock = s;} // Constructor, assign arg s to local sock
  public void run(){
    PrintStream out = null;   // Input from the socket
    BufferedReader in = null; // Output to the socket
    try {
      out = new PrintStream(sock.getOutputStream());
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      
      String getResponse = in.readLine();	// this will read the GET response
      int person = getResponse.indexOf("person");	// get position of person from form and get the response input for name
      int number1 = getResponse.indexOf("num1");	// get position of num1 from form and get the response input for number1
      int number2 = getResponse.indexOf("num2");	// get position of num2 from form and get the response input for number 2
      String name = getResponse.substring(person + 7, number1 - 1);
      String numb1 = getResponse.substring(number1 + 5, number2 - 1);
      String numb2 = getResponse.substring(number2 + 5, number2 + 6);
      System.out.println(getResponse);
      System.out.println(name + "\n");
      System.out.println(number1 + "\n");
      System.out.println(number2 + "\n");    
      int firstNumber = Integer.parseInt(numb1);
      int secondNumber = Integer.parseInt(numb2);	
      int total = firstNumber + secondNumber;	// adding total of first and second numbers
      System.out.println(total);	// printing the total out to screen
      
      System.out.println("Sending the HTML Reponse now: " + Integer.toString(MiniWebserver.i) + "\n" );         
      String HTMLResponse = "<html><head> <link rel=\"icon\" href=\"data:,\"> </head> <h1> Hello Browser World! " + Integer.toString(MiniWebserver.i++) +  "</h1> <p><p> <hr> <p>";	// use the html response to handle the error and integer.toString will increment the count on the web page

      String formResponse ="<FORM method=\"GET\" action=\"http://localhost:2540/WebAdd.fake-cgi\">\n" +
              "\n" +
              "Enter your name and two numbers. My program will return the sum:<p>\n" +
              "\n" +
              "<INPUT TYPE=\"text\" NAME=\"person\" size=20 value="+ name +" ><P>\n" +
              "\n" +
              "<INPUT TYPE=\"text\" NAME=\"num1\" size=5 value="+ number1 +" > <br>\n" +
              "<INPUT TYPE=\"text\" NAME=\"num2\" size=5 value="+ number2 +" > <p>\n" +
              "\n" +
              "<INPUT TYPE=\"submit\" method=\"POST\" VALUE=\"Submit Numbers\">\n" +
              "\n" +
              "</FORM>";  // this is the source code of the addnum web site added to java to create the form for the web page

      String resName = "<h2>"+"Name =>"+ name + "</h2>";  // put the name data back into the web page using html format
      String resNumber1 = "<h2>"+"First Number =>"+ number1 + "</h2>";	// put the number1 data back into the web page using html format
      String resNumber2 = "<h2>"+"Second Number =>"+ number2 + "</h2>";	// put the number2 data back into the web page using html format
      String resTotal = "<h2>"+"Total =>"+ total + "</h2>";	// put total of number1 and number2 back into the web page using html format

      out.println("HTTP/1.1 200 OK");
      //out.println("Connection: close"); // Can fool with this.
      //int Len = HTMLResponse.length();
      //out.println("Content-Length: " + Integer.toString(Len));
      out.println("Content-Length: 800"); 
      out.println("Content-Type: text/html \r\n\r\n");
      out.println(HTMLResponse);
	
      out.println(formResponse);         
      out.println(resName);
      out.println(resNumber1);
      out.println(resNumber2);
      out.println(resTotal);
	
      /* for(int j=0; j<6; j++){ // Echo some of the request headers for fun
			out.println(in.readLine() + "<br>\n"); 
		}   
		*/                                    
      out.println("</html>"); // this is how we end the html 
      sock.close(); // close this connection, but not the server;
    } catch (IOException x) {
      System.out.println("Error: Connetion reset. Listening again...");
    }
  }
}

public class MiniWebserver {

  static int i = 0;

  public static void main(String a[]) throws IOException {
    int q_len = 6; /* Number of requests for OpSys to queue */
    int port = 2540;
    Socket sock;

    ServerSocket servsock = new ServerSocket(port, q_len);

    System.out.println("Fadi Abdelrahim's MiniWebserver running at 2540.");
    System.out.println("Point Firefox browser to http://localhost:2540/abc.\n");
    while (true) {
      // wait for the next client connection:
      sock = servsock.accept();
      new ListenWorker (sock).start();
    }
  }
}
