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


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;
//import javax.script.ScriptEngine;

public class server_java_tcp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			String portA = args[0];
				
				int port = Integer.parseInt(portA);
			ServerSocket Socket = new ServerSocket(port);
			Socket serverSocket = Socket.accept();
			
			System.out.println("Server is Listening .... ");

			String answerToSend = "";

			double answer = 0;
			
			InputStream s1In = serverSocket.getInputStream();
			DataInputStream dis = new DataInputStream(s1In);
			
			String oldnewExpressionFromClient = dis.readUTF();
			
			//String newExpressionFromClient = oldnewExpressionFromClient.replaceAll("\\s","");
			String newExpressionFromClient = removeSpace(oldnewExpressionFromClient);
			
			

			
			System.out.println("Expression at Client Side :: " + newExpressionFromClient);
			int ans=0;
			
			int indexOfPlus = newExpressionFromClient.indexOf("+");
			int indexOfMinus = newExpressionFromClient.indexOf("-");
			int indexOfMul = newExpressionFromClient.indexOf("*");
			int indexOfDiv = newExpressionFromClient.indexOf("/");
			
			char[] charArray = newExpressionFromClient.toCharArray();
			
			
			
			boolean flag = false;
			double count = 0;
			for(char c : charArray)
			{
				if(c == '+' || c == '-' || c == '*' || c == '/')
				{
					count++;
				}
			}
			
			if ( count > 1)
			{
				//String evaluate = newExpressionFromClient;
				
//			    ScriptEngineManager mgr = new ScriptEngineManager();
//			    ScriptEngine engine = mgr.getEngineByName("JavaScript");
			    // String foo = "40+2";
//			    answer = (Integer) engine.eval(evaluate);
			    //answer = Integer.parseInt(answer1);
				answer = eval(newExpressionFromClient);
			    
				answerToSend = String.valueOf(answer);
				System.out.println("Sender is sending the Answer :: " + answerToSend);

			}
			else
			{
				if(indexOfPlus != -1)
				{
					String[] numbers;
					String delimiter = "\\+";
					
					numbers = newExpressionFromClient.split(delimiter);
					
					int num1 = Integer.parseInt(numbers[0]);
					int num2 = Integer.parseInt(numbers[1]);
					answer = num1 + num2;
					System.out.println("the Answer :: " + answer);
					answerToSend = String.valueOf(answer);
					System.out.println("Sender is sending the Answer :: " + answerToSend);
					
				}
	
				else if(indexOfMinus != -1)
				{
					String[] numbers;
					String delimiter = "\\-";
					
					numbers = newExpressionFromClient.split(delimiter);
					
					int num1 = Integer.parseInt(numbers[0]);
					int num2 = Integer.parseInt(numbers[1]);
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
			OutputStream outToClient = serverSocket.getOutputStream();
			DataOutputStream sendingDatatoClient = new DataOutputStream (outToClient);
			System.out.println("After OutputStream:: " + answerToSend);
			String answerFinal = String.valueOf(answerToSend);
			System.out.println("After Converting to String :: " + answerFinal);

			StringBuffer sb = new StringBuffer();
			sb.append(answerFinal);
			sb.append("\n");
			if(answer > 0)
			{
				for(int i=0; i<answer ;i++)
				{
					sb.append("Socket Programming. \n");
				}
			}
			else
			{
				System.out.println("Answer is less than zero or Infinite so can not print Socket Programming.");
				sb.append("\n\nAnswer is less than zero or Infinite so can not print Socket Programming.");
			}
			String stringFinalAns = sb.toString();
			sendingDatatoClient.writeUTF(stringFinalAns); // Send a string to client
			// Close the connection, but not the server socket
			sendingDatatoClient.close();
			outToClient.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not connect. Terminating.");
		}
	}



public static String removeSpace(String s) {
	// TODO Auto-generated method stub
    String withoutspaces = "";
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) != ' ')
            withoutspaces += s.charAt(i);

    }
    return withoutspaces;
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


