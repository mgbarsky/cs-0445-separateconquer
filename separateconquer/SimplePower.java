package separateconquer;
/** @author Marina Barsky
* Demonstration of recursive power computation
* which uses divide-and-conquer idea. 
* A trace version of the recursive algorithm is also shown
* so you can better see the behavior of the recursive algorithm.
* This simple version only works with small integers !!!! 
*/  

import java.io.*;
public class SimplePower {
	
	// Method to generate a string of 2N blanks
	public static String indent(int N)	{
		String S = new String("");
		for (int i = 0; i < N; i++)
			S = S + "  ";
		return S;
	}
	
	//iterative (loopy) version of power
	public static int powerIterative(int X, int N) {
		int result = 1;
		for (int i = 0; i < N; i++)
			result = result*X;
		return result;
	}
	
	//recursive version of power - same complexity as iterative
	public static int powerRecursive(int X, int N) {
		if (N == 0)
			return 1;
		return X*powerRecursive(X, N-1);		
	}
	
	//recursive version of power with trace
	public static int powerRecursiveTr(int X, int N, int level) {
		String indent = indent(level);  // Get a string of spaces to show
		// level of the recursive call through indenting

		System.out.println(indent + "Power: "+ X +"^" + N);
		if (N == 0)	{
			System.out.println(indent + "Base case: N = " + N);
			System.out.println(indent + "Returning 1");
			return 1;
		}
		else	{
			System.out.println(indent + "Calling Power: "+ X +"^" + (N-1));
			int recResult = X*powerRecursiveTr(X, N-1, level+1);
			System.out.println(indent + "Recursive Power: "+ X +"^" + (N-1));
			System.out.println(indent + "Returning " + recResult);
			return recResult;
		}		
	}
	
	//divide-and-conquer version of power - log N running time
	public static int powerRecursiveDC(int X, int N) {
		if (N == 0)
			return 1;
		int recResultHalf  = powerRecursiveDC(X, N/2);
		if (N % 2 == 0) // N is even
			return recResultHalf*recResultHalf;
		else
			return X*recResultHalf*recResultHalf;				
	}
	
	//divide-and-conquer version of power with trace - log N running time
	public static int powerRecursiveDCTr(int X, int N, int level) {
		String indent = indent(level);  // Get a string of spaces to show
		// level of the recursive call through indenting

		System.out.println(indent + "Power: "+ X +"^" + N);
		if (N == 0)	{
			System.out.println(indent + "Base case: N = " + N);
			System.out.println(indent + "Returning 1");
			return 1;
		}
		else	{
			System.out.println(indent + "Calling Power: "+ X +"^(" + N + "/2)");
			int recResultHalf = powerRecursiveDCTr (X, N/2, level+1);
			System.out.println(indent + "Recursive Half Power: "+ X +"^" + (N/2) + " = " + recResultHalf);
			
			int recResult = 0;
			if (N % 2 == 0) { // N is even
				recResult = recResultHalf*recResultHalf;
				System.out.println(indent + "N=" + N + " Case even: Returning: "+ 
						recResultHalf +"*" + recResultHalf + "="+ recResult);											
			}
			else {
				recResult =  X*recResultHalf*recResultHalf;	
				System.out.println(indent + "N=" + N + " Case odd: Returning: "+ X +"*" +
						recResultHalf +"*" + recResultHalf + "="+ recResult);		
			}
			return recResult;
		}					
	}
	
	//BAD divide-and-conquer version of power with trace - too many recursive calls O(N) running time
	public static int powerRecursiveBadDCTr(int X, int N, int level) {
		String indent = indent(level);  // Get a string of spaces to show
		// level of the recursive call through indenting

		System.out.println(indent + "Power: "+ X +"^" + N);
		if (N == 0)	{
			System.out.println(indent + "Base case: N = " + N);
			System.out.println(indent + "Returning 1");
			return 1;
		}
		else	{
			System.out.println(indent + "Calling Power: "+ X +"^(" + N + "/2)");			
			int recResult1 = powerRecursiveBadDCTr (X, N/2, level+1);
			System.out.println(indent + "Calling Power: "+ X +"^(" + N + "/2)");
			int recResult2 = powerRecursiveBadDCTr (X, N/2, level+1);
			int recResult = recResult1 * recResult2;
			if (N % 2 == 0) { // N is even				
				System.out.println(indent + "N=" + N + " Case even: Returning: "+ 
						+ recResult);											
			}
			else {
				System.out.println(indent + "N=" + N + " Case odd: Returning: "+ X +"*" +
						recResult);		
				recResult =  X*recResult;	
				
			}
			return recResult;
		}					
	}
	
	public static void main (String [] args) throws IOException	{
		BufferedReader BR = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("Please enter number for base X (small)");
		int x = Integer.parseInt(BR.readLine());
		
		System.out.println("Please enter number for exponent N (small)");
		int n = Integer.parseInt(BR.readLine());
		
		System.out.println("Iterative Power " + x +"^" + n + " = " + powerIterative(x, n));
		System.out.println("Recursive Power " + x +"^" + n + " = " + powerRecursive(x, n));
		System.out.println("\nTracing recursive version:\n");
		int result = powerRecursiveTr(x, n, 0);	
		System.out.println("Recursive Power with trace " + x +"^" + n + " = " + result);
		
		System.out.println("\nDivide and conquer version");
		result = powerRecursiveDC(x, n);	
		System.out.println("Divide and conquer Power " + x +"^" + n + " = " + result);
		
		System.out.println("\nTracing divide-and-conquer version:\n");
		result = powerRecursiveDCTr (x, n, 0);
		System.out.println("Divide and conquer Power with trace " + x +"^" + n + " = " + result);
		
		System.out.println("\nTracing BAD divide-and-conquer version:\n");
		result = powerRecursiveBadDCTr (x, n, 0);
		System.out.println("Divide and conquer Bad Power with trace " + x +"^" + n + " = " + result);
	}

}
