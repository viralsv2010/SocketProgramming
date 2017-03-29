////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		I have took a Reference from the below sites to understand basic Socket Functionality.
//	
//
//		
//
//		http://codesamplez.com/programming/udp-programming-in-java 
//
//
//
//
//
//
//
//
//
//
//
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class client_java_tcp {

	public static void main(String[] args) throws IOException,SocketException {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("Enter server name or IP address:");
		String serverNameorIP = input.next();
		try {
			InetAddress ip = InetAddress.getByName(serverNameorIP);
			
			boolean val = ip.isReachable(100);
			
			if(val == true)
			{
//			boolean flag = false;
//		    Pattern pattern;
//		    Matcher matcher;
//
//		    final String IPADDRESS_PATTERN =
//				"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
//		    
//		    pattern = Pattern.compile(IPADDRESS_PATTERN);
//		    
//		    matcher = pattern.matcher(serverNameorIP);
//		    flag = matcher.matches();
//			if(flag == true && ip instanceof InetAddress)
//			{
				System.out.println("Enter port: ");
				Integer portNumber = input.nextInt();
				if(portNumber == null || portNumber < 0 || portNumber > 65535)
				{
					System.out.println("Invalid Port Number. Terminating");
				}
				else
				{
					System.out.println("Enter expression:");
					String expression = input.next();
					
					if(expression.contains("+") || expression.contains("-") || expression.contains("*") || expression.contains("/") )
					{
				
						try {
							Socket clientSocket = new Socket(serverNameorIP, portNumber);
					
							boolean connected = clientSocket.isConnected() && !clientSocket.isClosed();
					
							if(connected)
							{
								System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
				         
								OutputStream outToServer = clientSocket.getOutputStream();
								DataOutputStream sendingDataToServer = new DataOutputStream(outToServer);
				         
								sendingDataToServer.writeUTF(expression);
								
								clientSocket.setSoTimeout(1000);
								InputStream inToClient = clientSocket.getInputStream();
								DataInputStream comingFromServer = new DataInputStream(inToClient);
				         
								String answer = new String (comingFromServer.readUTF());
				         
								System.out.println("Answer is :: " + answer);
				         
								inToClient.close();
								comingFromServer.close();
								clientSocket.close();
							}
							else
							{
								System.out.println("Could not connect to server a. Terminating.");
							}
					
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							System.out.println("Could not connect to server b. Terminating.");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("Could not connect to server c. Terminating.");
							e.printStackTrace();
						}
						}
					else
					{
						System.out.println("Not a Valid Expression. Terminating.");
					}
				
			}


			}
			else
			{
				System.out.println("Could not connect to server. Terminating.");
			}
			
					} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			System.out.println("Could not connect to server. Terminating.");
			//e1.printStackTrace();
		}
		
				

	}

}

