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


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class client_java_udp {

	private final static int PACKETSIZE = 1024 ;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	      DatagramSocket clientSocket = null;

	      clientSocket = new DatagramSocket();
	
	  		Scanner input = new Scanner(System.in);
			System.out.println("Enter server name or IP address:");
			
			String serverNameorIP = input.next();
			try
			{
				InetAddress ip = InetAddress.getByName(serverNameorIP);
				boolean val = ip.isReachable(100);
			
				if(val == true)
				{
//				boolean flag = false;
//			    Pattern pattern;
//			    Matcher matcher;
//	
//			    final String IPADDRESS_PATTERN =
//					"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
//					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
//			    
//			    pattern = Pattern.compile(IPADDRESS_PATTERN);
//			    
//			    matcher = pattern.matcher(serverNameorIP);
//			    flag = matcher.matches();
//				if(flag == true && ip instanceof InetAddress)
//				{
					System.out.println("Enter port: ");
		
					Integer portNumber = input.nextInt();
		
					if(portNumber == null || portNumber < 0 || portNumber > 65535)
					{
						System.out.println("Invalid port number. Terminating.");
					}
			        else
			 		{
			 			System.out.println("Enter expression:");
		
			 			String expression = input.next();
		
			 			if(expression.contains("+") || expression.contains("-") || expression.contains("*") || expression.contains("/") )
			 			{
		
			 				try {
		
			 						InetAddress host = InetAddress.getByName(serverNameorIP) ;
		
			 						byte[] dataTosend = expression.getBytes();
		
			 						int a = expression.length();
			 						System.out.println("A :: " + a);
			 						String length = Integer.toString(a);
			 						byte[] lengthData = length.getBytes();
			 						System.out.println("Lengthdata is :: " + lengthData.length);
			 						// Creating the Length Packet.
			 						DatagramPacket lengthPacket = new DatagramPacket(lengthData, lengthData.length, host, portNumber);
			 						DatagramPacket expressionPacket =null;
		// 							int i=0;
		// 							while(i<3)
		// 							{
		//	 						lengthPacket.setData(lengthData);
		//	 						lengthPacket.setAddress(host);
			 						clientSocket.send(lengthPacket);
		//	 						clientSocket.setSoTimeout(1000);
		
			 						expressionPacket = new DatagramPacket(dataTosend, dataTosend.length, host, portNumber);
		
									System.out.println("After Expression packet has been created.");
			// 						System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
		
		//	   						int i = 0;
		//
		//								while(i<3)
		//		 						{
											System.out.println("Sending the Expression Packet.");
				 							clientSocket.send(expressionPacket);
		
				 							System.out.println("Receiving the Expression Packet.");
		//		 							lengthPacket.setData(new byte[PACKETSIZE]);
		//		 							lengthPacket.setLength(PACKETSIZE);
				 							System.out.println("a");
				 							DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
				 							System.out.println("d");
				 							clientSocket.receive(packet);
				 							System.out.println("b");
				 							String ack = new String(packet.getData());
				 							System.out.println("c");
				 							System.out.println("Ack String :: " + ack);
				 							if(ack == "ACK")
				 							{
				 								System.out.println("Acknowledment. ");
		//		 								break;
				 							}
				 							else
				 							{
		//		 								if(i==2)
		//		 								{
				 									System.out.println("Could not fetch result. Terminating.");
				 									//clientSocket.close();
		//		 									break;
		//		 								}
		//		 								i++;
		//		 								continue;
				 							}
		//		 				s
				 					//		clientSocket.setSoTimeout(1000);
		
		//		 							int outBuffer = clientSocket.getSendBufferSize();
		////
		////		 							int inBuffer  = clientSocket.getReceiveBufferSize();
		//
		//		 							System.out.println("Out Buffer :: " + outBuffer + " In Buffer :: " + inBuffer);
		//		 						}
		
				 						DatagramPacket Lenpacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
				 						clientSocket.receive(Lenpacket);
					 						byte[] n = Lenpacket.getData(); //99
					 						String s = new String(n).trim(); //99
					 						System.out.println("String :: " + s); 
					 						int lengthofExpression = Integer.parseInt(s);
					 						System.out.println("Length :: " + lengthofExpression);
					 						
											System.out.println("New Len Packet received.");
				 							
				 							
				 							
				 					expressionPacket.setData(new byte[PACKETSIZE]);
										
									System.out.println("New Packet receive ready.");
		
			 						clientSocket.receive(expressionPacket);
									System.out.println("New Packet received.");
		
		
			 						String answer = new String(expressionPacket.getData());
		
			 						System.out.println("Answer is :: " + answer);
			 						System.out.println("Expression Len :: " + expressionPacket.getLength());
			 						int expresssionLen = expressionPacket.getLength();
			 						InetAddress inetAddress = expressionPacket.getAddress();
			 						int clientportNumber = expressionPacket.getPort();
			 						
			 						if(lengthofExpression == expresssionLen)
			 						{
			 							System.out.println("Inside If");
			 							String ackLen = "ACK";
			 							byte buf[] = ackLen.getBytes();
			 							lengthPacket.setData(buf);
			 							lengthPacket.setLength(buf.length);
			 							lengthPacket.setAddress(inetAddress);
			 							lengthPacket.setPort(clientportNumber);
			 							clientSocket.send(lengthPacket);
			 						}
		
			 						clientSocket.close();
			 				} catch (UnknownHostException e) {
			 					// TODO Auto-generated catch block
			 					System.out.println("Could not connect to server. Terminating.");
			 				} catch (IOException e) {
			 					// TODO Auto-generated catch block
			 					System.out.println("Could not connect to server. Terminating.");
			 					e.printStackTrace();
			 				}
			 				finally
			 				{
			 					if(clientSocket != null)
			 					{
			 						clientSocket.close();
			 						input.close();
			 					}
			 				}
			 				
			 				}
		 			else
		 			{
		 				System.out.println("Not a Valid Expression. Terminating.");
		 			}
			 			
		 	}
					
	
				clientSocket.close();
	}
				else
				{
					System.out.println("Could not connect to server. Terminating.");
				}
		}
			catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					System.out.println("Could not connect to server. Terminating.");}
}
}
