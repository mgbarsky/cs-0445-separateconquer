package separateconquer;
/** @author John Ramirez 
* simplified version by M. Barsky
* Demonstration of Sequential Search using both an iterative version and a
* recursive version. A trace version of the recursive algorithm is also shown
* so you can better see the behavior of the recursive algorithm. 
*/  
import java.io.*;

public class SequentialSearch {
	
	public static Integer [] loadData(int num)	{
		Integer [] data = new Integer[num];
		for (int i = 0; i < num; i++)	{
			Integer item = Integer.valueOf((int) (Math.random() * 200));
			data[i] = item;
		}
		return data;
	}
	
	//iterative (loopy) version of the search
	public static <T extends Comparable<T>> int seqSearch(T [] a, T key) {
		for (int i = 0; i < a.length; i++)
			if (a[i].compareTo(key) == 0)
				return i;
		return -1;
	}
	
	// "Stub" method that calls the recursive method.  This is often used with
	// recursive methods so that user does not need to know / provide any extra
	// parameters required for the recursive implementation.
	public static int searchRecursive(Integer [] A, Integer key)	{
		return recSeqSearch(A, key, 0);
	}

	// In this version we need an extra parameter to indicate the current
	// position within the array.  With each recursive call, the position
	// increments by one until either the key is found or we get to the
	// end of the array.  Recursive implementations often need additional
	// parameters so that more state information can be passed from one
	// call to the next.
	public static <T extends Comparable<T>> int recSeqSearch(T [] a, T key, int index)	{
		if (index >= a.length)	// base case not found 
			return -1;
		else if (a[index].compareTo(key) == 0)	// base case found
			return index;
		else return recSeqSearch(a, key, index+1);	// recursive step
	}
	
	// Trace version of recursive sequential search
	public static <T extends Comparable<T>> int recSeqSearchTr(T [] a, T key, int index) {
		for (int i = 0; i < index; i++) System.out.print(" ");
		System.out.print("CURRENT: " + index + " LAST: " + (a.length-1));

		if (index >= a.length)	{
			System.out.println(" Base case not found");
			return -1;
		}
		else {
			System.out.print(" Key: " + key + " Curr: " + a[index]);
			if (a[index].compareTo(key) == 0)	{
				System.out.println(" Base case found: Return " + index);
				return index;
			}
			else {
				System.out.println(" Recursing... ");
				int ans = recSeqSearchTr(a, key, index + 1);
				for (int i = 0; i < index; i++) System.out.print(" ");
				System.out.println("Passing on " + ans);
				return ans;
			}
		}
	}
	
	public static void main (String [] args) throws IOException	{
		BufferedReader BR = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("Please enter size of the array");
		int num = Integer.parseInt(BR.readLine());
		Integer [] data = loadData(num);
		
		System.out.println("The data is: ");
		for (int i = 0; i < num-1; i++)	{
			System.out.print(data[i] + ", ");
		}
		System.out.println(data[num-1]);
		
		System.out.println("Please enter the item to search for:");
		Integer key = Integer.valueOf(BR.readLine());
		
		int loc = seqSearch(data, key);
		if (loc >= 0)
			System.out.println("Iteratively, " + key + " found at index " + loc);
		else
			System.out.println(key + " was not found ");
		
		
		loc = searchRecursive(data, key);
		if (loc >= 0)
			System.out.println("Recursively, " + key + " found at index " + loc);
		else
			System.out.println(key + " was not found ");
		
		System.out.println("\nTracing recursive version:\n");
		loc = recSeqSearchTr(data, key, 0);		
	}

 // Note that for this algorithm, the recursive solution and the iterative
 // solution have the same time complexity.  
	
}