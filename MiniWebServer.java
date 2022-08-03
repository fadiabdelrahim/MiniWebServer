*
 * 3. 
 * > javac MiniWebserver.java
 * 
 * 4. 
 * > java MiniWebserver
 * 
 * 5. 
 * a. MiniWebserver.java
 * b. WebAdd.html
 * c. MiniWebChecklist.html
 * 
 */

import java.io.*;  // I/O libraries
import java.net.*; // Java network libraries

class ListenWorker extends Thread {    
  Socket sock;                 
  ListenWorker (Socket s) {sock = s;} 
  public void run(){	// this is run method. When we start something we need to have a run method to run this class
    PrintStream out = null;   // PrintStream is name of variable called out to format output
    BufferedReader in = null; // BufferReader is name of variable called in to format input
    try {
      out = new PrintStream(sock.getOutputStream()); // apply getOutputStream method to socket we get from listening server
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));  // get input from getInputStream method when applied to socket connection and read input  
      String getResponse = in.readLine();	// this will read the GET response
      int person = getResponse.indexOf("person");	// get position of person from form and get the response input for name
      int number1 = getResponse.indexOf("num1");	// get position of num1 from form and get the response input for number1
      int number2 = getResponse.indexOf("num2");// get position of num2 from form and get the response input for number 2
      int stop = getResponse.indexOf("HTTP");	// get the position of HTTP so we can use it as an end point to know where numb2 stops   
      String name = getResponse.substring(person + 7, number1 - 1);	// we are giving the start and end positions for the name data we need to retrieve from the string
      String n1 = getResponse.substring(number1 + 5, number2 - 1); // we are giving the start and end positions for the numb1 data we need to retrieve from the string
      String n2 = getResponse.substring(number2 + 5, stop - 1); // we are giving the start and end positions for the numb2 data we need to retrieve from the string     
      int numb1 = Integer.parseInt(n1);	// converting string number to integer
      int numb2 = Integer.parseInt(n2);	// converting string number to integer
      int total = numb1 + numb2;	// adding total of first and second numbers
      System.out.println(getResponse);	// print GET string line from the web site to console
      System.out.println("Name: " + name);	// print the name we retrieved from the get response string to console
      System.out.println("Total: " + total);	// printing the total of numb1 + numb2 to console      
      System.out.println("Sending the HTML Response now: " + Integer.toString(MiniWebserver.i) + "\n" );         
      String HTMLResponse = "<html><head> <link rel=\"icon\" href=\"data:,\"> </head> <h1> Hello Browser World! " + Integer.toString(MiniWebserver.i++) +  "</h1> <p><p> <hr> <p>";	// handle the facivon problems and integer.toString will increment the count on the web page
      String form ="<FORM method=\"GET\" action=\"http://localhost:2540/WebAdd.fake-cgi\">\n" + "\n" + "Enter your name and two numbers. My program will return the sum:<p>\n" + "\n" + "<INPUT TYPE=\"text\" NAME=\"person\" size=20 value="+ name +" ><P>\n" + "\n" + "<INPUT TYPE=\"text\" NAME=\"num1\" size=5 value="+ n1 +" > <br>\n" + "<INPUT TYPE=\"text\" NAME=\"num2\" size=5 value="+ n2 +" > <p>\n" + "\n" + "<INPUT TYPE=\"submit\" method=\"POST\" VALUE=\"Submit Numbers\">\n" + "\n" + "</FORM>";  // this is the code of the addnum web site added to java to create the form for the web page. It lets user press submit multiple times without need for refresh 
      String webName = "<h2>"+"Name: "+ name + "</h2>";  // put the name data back into the web page using HTML format
      String webTotal = "<h2>"+"Total: "+ total + "</h2>";	// put total of numb1 and numb2 back into the web page using HTML format
      out.println("HTTP/1.1 200 OK");
      out.println("Content-Length: 600"); // specify the length of the response HTML
      out.println("Content-Type: text/html \r\n\r\n"); // specify which type of data will respond from the HTML - MIME type
      out.println(HTMLResponse);	// print out the HTMLResponse to the web site 	
      out.println(form); // printing out the form data to be viewed on web site
      out.println(webName);	// printing out the name to be viewed on web site
      out.println(webTotal);	// printing out total of numb1 and numb2 to be viewed on web site                                   
      out.println("</html>"); // this is how we end the HTML 
      sock.close(); // close connection but server still running
    } catch (IOException x) { // catch any exception
      System.out.println("Error: Connection reset. Listening again..."); // print out message to screen
    }
  }
}

public class MiniWebserver {

  static int i = 0;

  public static void main(String a[]) throws IOException { // accepting arguments into the program
    int q_len = 6; // if there is more than 6 connections to server before can spawn thread it will throw away anything after 6
    int port = 2540; // port number used to access server
    Socket sock; // sock is allocated to socket

    ServerSocket serversock = new ServerSocket(port, q_len); // assign serversock to serverSocket type of object creating a new serverSocket and passing it port number and q length

    System.out.println("Fadi Abdelrahim's MiniWebserver running at 2540."); // print out message to screen
    System.out.println("Point Firefox browser to http://localhost:2540/abc.\n"); // print out message to screen
    while (true) { // this means it runs forever
      // wait for next connection
      sock = serversock.accept(); // when serverSocket accepts connection will put that connection into sock
      new ListenWorker (sock).start(); // spawn new ListenWorker thread and pass it the connection just got from sock
    }
  }
}
