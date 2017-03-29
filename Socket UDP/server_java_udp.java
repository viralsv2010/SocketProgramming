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
//		http://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form
// 		From the above site, I have used the code for evaluating the arithmetic expression. Code starts from line number 334 to 414.
//		Method name is :: public static double eval(final String str)
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


public class server_java_udp {

	private final static int PACKETSIZE = 1024 ;

	public static void main(String[] args){


			try {

				while(true)
				{
				
				String portA = args[0];
				
				int port = Integer.parseInt(portA);

				DatagramSocket serverSocket = new DatagramSocket(port);

				System.out.println("Server is Listening .... ");

				String answerToSend = "";

				double answer = 0.0;


				DatagramPacket lengthPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
//
//				serverSocket.receive(lengthPacket);
//
//				System.out.println("Length packet is :: " + lengthPacket.getData() + "Length is :: " + lengthPacket.getLength());


				//DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE );

				//DatagramPacket lengthPacket = new DatagramPacket(lengthArray, lengthArray.length, address, port)

				// Receiving the Length of the Packet.
				serverSocket.receive(lengthPacket);

				System.out.println("Length Packet data is : " + new String(lengthPacket.getData()));
				
				System.out.println("Length Packet length is :: " + lengthPacket.getLength());
				
				System.out.println("Length Packet Address is :: " + lengthPacket.getAddress());

			//	serverSocket.setSoTimeout(5000);

				InetAddress inetAddress = lengthPacket.getAddress();

				System.out.println("a");

				Integer clientportNumber = lengthPacket.getPort();

				System.out.println("c");
				byte[] n = lengthPacket.getData();
				String s = new String(n).trim();
				System.out.println("String :: " + s);
				int lengthofExpression = Integer.parseInt(s);
				System.out.println("Length :: " + lengthofExpression);

				System.out.println("b");

				System.out.println("Packet Received. Length is " + lengthofExpression) ;

				// Receiving Expression Packet.
				//byte[] expressionArray=null;
				DatagramPacket expressionPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
				System.out.println("d");
				serverSocket.receive(expressionPacket);

				System.out.println("Expression Packet Received.");

				int expressionLength = expressionPacket.getLength();

				System.out.println(" Data :: " + expressionPacket.getData() + " Length :: " + expressionPacket.getLength());

				String ExpressionFromClient = new String(expressionPacket.getData());
				
				String oldnewExpressionFromClient = ExpressionFromClient.trim();
				
				int lengthofString = oldnewExpressionFromClient.length();
				
				System.out.println(" oldnewExpressionFromClient " + oldnewExpressionFromClient);
				System.out.println("Length is :: " + lengthofExpression);
				System.out.println("Expression Len :: " + expressionLength);

				
				if(lengthofExpression == expressionLength)
				{
					System.out.println("Inside If");
						String ack = "ACK";
						byte buf[] = ack.getBytes();
//						lengthPacket.setData(buf);
//						lengthPacket.setLength(buf.length);
//						lengthPacket.setAddress(inetAddress);
//						lengthPacket.setPort(clientportNumber);
						
						DatagramPacket packet = new DatagramPacket(buf, buf.length, inetAddress, clientportNumber);
						System.out.println("before send" + packet.getData().toString());
						serverSocket.send(packet);
					
						
						//int datalength = oldnewExpressionFromClient.length();

						//
						// if(lengthofExpression != datalength)
						// {
						// 	System.out.println("Did not receive valid expression from client. Terminating.");
						// }

						//System.out.println("Data Received from Client :: InetAddress = " + inetAddress + " Port Number = " + clientportNumber + " Expression = " + oldnewExpressionFromClient);

						String newExpressionFromClient = oldnewExpressionFromClient.replaceAll("\\s","");

						System.out.println("Expression at Client Side :: " + newExpressionFromClient);

						int indexOfPlus = newExpressionFromClient.indexOf("+");
						System.out.println("Index of Plus :: " + indexOfPlus);
						int indexOfMinus = newExpressionFromClient.indexOf("-");
						System.out.println("Index of Minus :: " + indexOfMinus);
						int indexOfMul = newExpressionFromClient.indexOf("*");
						System.out.println("Index of Mul :: " + indexOfMul);
						int indexOfDiv = newExpressionFromClient.indexOf("/");
						System.out.println("Index of Div :: " + indexOfDiv);

						char[] charArray = newExpressionFromClient.toCharArray();
						System.out.println("After charArray");
						int count = 0;
						for(char c : charArray)
						{
							if(c == '+' || c == '-' || c == '*' || c == '/')
							{
								count++;
							}
						}
						System.out.println("Count Value :: " + count);
						if ( count > 1)
						{
							String evaluate = newExpressionFromClient;

//						    ScriptEngineManager mgr = new ScriptEngineManager();
//						    ScriptEngine engine = mgr.getEngineByName("JavaScript");
							answer = eval(oldnewExpressionFromClient);
						    //answer = (Integer) engine.eval(evaluate);

						   // System.out.println(engine.eval(evaluate));

							answerToSend = String.valueOf(answer);
							System.out.println("Sender is sending the Answer :: " + answerToSend);

						}
						else
						{
							if(indexOfPlus != -1)
							{
								String[] numbers;
								String delimiter = "\\+";
								System.out.println(" Before numbers array ");
								numbers = newExpressionFromClient.split(delimiter);
			          for(int i=0;i<numbers.length ; i++)
			          {
			            System.out.println("Numbers Array ::  Element " + i + " Data : " + numbers[i]);
			          }
			        //  System.out.println(" After numbers array ");
								int num1 = Integer.parseInt(numbers[0]);
			        //  System.out.println("Number 1 :: " + num1);
								int num2 = Integer.parseInt(numbers[1].trim());
			        //  System.out.println("Number 2 :: " + num2);
								answer = num1 + num2;
							//	System.out.println("the Answer :: " + answer);
								answerToSend = String.valueOf(answer);
							//	System.out.println("Sender is sending the Answer :: " + answerToSend);

							}

							else if(indexOfMinus != -1)
							{
								String[] numbers;
								String delimiter = "\\-";

								numbers = newExpressionFromClient.split(delimiter);

								int num1 = Integer.parseInt(numbers[0]);
								int num2 = Integer.parseInt(numbers[1].trim());
								answer = num1 - num2;
								System.out.println("the Answer :: " + answer);
								answerToSend = String.valueOf(answer);
								System.out.println("Sender is sending the Answer :: " + answerToSend);

							}

							else if(indexOfMul != -1)
							{
								String[] numbers;
								String delimiter = "\\*";

								numbers = newExpressionFromClient.split(delimiter);

								int num1 = Integer.parseInt(numbers[0]);
								int num2 = Integer.parseInt(numbers[1]);
								answer = num1 * num2;
								System.out.println("the Answer :: " + answer);
								answerToSend = String.valueOf(answer);
								System.out.println("Sender is sending the Answer :: " + answerToSend);

							}

							else if(indexOfDiv != -1)
							{
								String[] numbers;
								String delimiter = "\\/";

								numbers = newExpressionFromClient.split(delimiter);

								int num1 = Integer.parseInt(numbers[0]);
								int num2 = Integer.parseInt(numbers[1]);
								answer = num1 / num2;
								System.out.println("the Answer :: " + answer);
								answerToSend = String.valueOf(answer);
								System.out.println("Sender is sending the Answer :: " + answerToSend);

							}
						}


						String answerFinal = String.valueOf(answerToSend);
						System.out.println("After Converting to String :: " + answerFinal);

						StringBuffer sb = new StringBuffer();
						sb.append(answerFinal);
						sb.append("\n");
						if(answer > 0)
						{
							for(int i=0; i<answer ;i++)
							{
								sb.append("Socket Programming\n");
							}
						}
						else
						{
							System.out.println("Answer is less than zero or Infinite so can not print Socket Programming.");
							sb.append("\n\nAnswer is less than zero or Infinite so can not print Socket Programming.");
						}
						
						

						
						String stringFinalAns = sb.toString();

						System.out.println("Last step for String :: " + stringFinalAns);  // 5.0
						
						byte[] dataTosend = stringFinalAns.getBytes();
						

						int a = stringFinalAns.length(); //
						System.out.println("Length od String is :: " + a); // 99
						String length = Integer.toString(a); // 99
						byte[] lengthData = length.getBytes(); // 99
						System.out.println("Lengthdata is :: " + lengthData.length); //2
						
						
						System.out.println("Length Packet starting Sending.");
						lengthPacket.setData(lengthData);
						lengthPacket.setLength(lengthData.length);
						lengthPacket.setAddress(inetAddress);
						lengthPacket.setPort(clientportNumber);
						
						//sending Length Packet.
						serverSocket.send(lengthPacket);
						System.out.println("Length Packet starting Ended.");
						byte[] byteArrayData = stringFinalAns.getBytes();

						//packet.setData(byteArrayData);

			     // DatagramPacket packetTosend = new DatagramPacket(byteArrayData, byteArrayData.length, inetAddress, clientportNumber);
						System.out.println("Expression Packet starting Sending.");
						expressionPacket.setData(byteArrayData);
						expressionPacket.setLength(byteArrayData.length);
						expressionPacket.setAddress(inetAddress);
						expressionPacket.setPort(clientportNumber);
						System.out.println("Just before sending");
			      
						serverSocket.send(expressionPacket);
						System.out.println("Expression Packet starting Ended.");
						
						serverSocket.receive(expressionPacket);
						System.out.println("Got Ack from Client for the Result.");
						serverSocket.close();
				}
				else
				{
					System.out.println("Did not receive valid expression from client. Terminating.");
				}
			}
		}
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Could not connect. Terminating.");
				e.printStackTrace();
			}

	}

	public static double eval(final String str) {
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        double parse() {
	            nextChar();
	            double x = parseExpression();
	            System.out.println("Position :: " + pos + " Str Len :: " + str.length());
	            if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return x;
	        }

	        // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)`
	        //        | number | functionName factor | factor `^` factor

	        double parseExpression() {
	            double x = parseTerm();
	            for (;;) {
	                if      (eat('+')) x += parseTerm(); // addition
	                else if (eat('-')) x -= parseTerm(); // subtraction
	                else return x;
	            }
	        }

	        double parseTerm() {
	            double x = parseFactor();
	            for (;;) {
	                if      (eat('*')) x *= parseFactor(); // multiplication
	                else if (eat('/')) x /= parseFactor(); // division
	                else return x;
	            }
	        }

	        double parseFactor() {
	            if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus

	            double x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                x = Double.parseDouble(str.substring(startPos, this.pos));
	            } else if (ch >= 'a' && ch <= 'z') { // functions
	                while (ch >= 'a' && ch <= 'z') nextChar();
	                String func = str.substring(startPos, this.pos);
	                x = parseFactor();
	                if (func.equals("sqrt")) x = Math.sqrt(x);
	                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
	                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
	                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
	                else throw new RuntimeException("Unknown function: " + func);
	            } else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

	            return x;
	        }
	    }.parse();	
}
}