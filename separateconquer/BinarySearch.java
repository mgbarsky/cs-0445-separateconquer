package separateconquer;
/** @author John Ramirez 
* simplified version by M. Barsky
* Demonstration of Binary Search using both an iterative version and
* a recursive version.  A recursive version of Sequential Search is
* also shown.  Trace versions of recursive Binary Search and recursive
* Sequential Search are shown to indicate the recursive calls and
* behavior of the algorithms.
*/  

//To increase the run-time stack size (necessary for recursive sequential
//search of a large array, say 222333) execute the program as follows:
//java -Xss10M BinarySearch
//The above increases the run-time stack size to 10 megabytes.  
// The default stack memory in JVM is 512K.
// In Eclipse: run configuration -> VM arguments: -Xss10M

import java.util.*;
import java.io.*;

public class BinarySearch {
	private Integer [] data;   //array to search in
	
	private int bicomps, 		//number of comparisons in iterative version of binary search
				brcomps, 		//number of comparisons in recursive version of binary search
				scomps, 		//number of comparisons in sequential search		
				reps, 
				bifound, 		//counts number of times item found by binary iterative search
				brfound, 		// - || -  by binary recursive search
				sfound;			// - || -  by sequential search
	
	private Integer item;		// the number to search for
    
	public BinarySearch(Integer [] data, int reps) 	{
		this.data = data;
		bicomps = 0; brcomps = 0; scomps = 0; // initialize total comps to 0
		this.reps = reps;  // how many searches will we try?
		bifound = 0; brfound = 0; sfound = 0;		
	}

	public void runTest()	{
		int loc;
		Integer item;
		for (int iter = 0; iter < reps; iter++)		{
			item = Integer.valueOf((int) (Math.random() * 1000));
			loc = binarySearchIterative(data, item);
			if (loc >= 0)
				bifound++;
			loc = binarySearchRecursive(data, item, 0, data.length-1);
			if (loc >= 0)
				brfound++;
			loc = seqSearchRecursive(data, item, 0);
			if (loc >= 0)
				sfound++;
		}
		
		double biave = (double)bicomps/reps;
		double brave = (double)brcomps/reps;
		double save = (double)scomps/reps;

		System.out.println("Iterative Binary Search: ");
		System.out.println("    Total comparisonss: " + bicomps);
		System.out.println("    Ave. comparisonss: " + biave);
		System.out.println("    Found: " + bifound + " out of " + reps);
		System.out.println("Recursive Binary Search: ");
		System.out.println("    Total comparisonss: " + brcomps);
		System.out.println("    Ave. comparisonss: " + brave);
		System.out.println("    Found: " + brfound + " out of " + reps);
		System.out.println("Recursive Sequential Search: ");
		System.out.println("    Total comparisonss: " + scomps);
		System.out.println("    Ave. comparisonss: " + save);
		System.out.println("    Found: " + sfound + " out of " + reps);
		System.out.println();
		
		if (data.length <= 32)	{
			System.out.println("Now tracing recursive Searches");
			item = Integer.valueOf((int) (Math.random() * 1000));
			loc = binarySearchTr(data, item, 0, data.length-1);
			if (loc >= 0)
				System.out.println("Recursively " + item + " has been found at loc " + loc);
			else
				System.out.println("Recursively " + item + " is not present ");
			System.out.println();			
		}
	}	

	// Iterative Binary Search (with which you should already be familiar)
	public <T extends Comparable<T>> int binarySearchIterative (T [] a, T obj) 	{
		int low = 0;
		int high = a.length-1;
		while (low <= high) {
			bicomps++;  
			int mid = (low + high)/2;
			T midItem = a[mid];
			int res = midItem.compareTo(obj);
			if (res < 0)
				low = mid + 1;
			else if (res > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -1;
	}

	public <T extends Comparable<T>> int binarySearchRecursive (T [] a, T obj, int low, int high) 	{
		int ans;
		if (low <= high) {  // if (low > high) the BASE CASE is reached		
			brcomps++;  // count one comparison for each recursive CALL

			int mid = (low + high)/2;
			T midItem = a[mid];

			// Note the recursive calls below -- we are calling the
			// same method, but using different parameters to
			// indicate a change in logical "size" of the array.  
			// In reality the physical array does not change -- we are simply
			// considering smaller and smaller pieces of it
			int res = midItem.compareTo(obj);
			if (res < 0)
				return (binarySearchRecursive(a, obj, mid + 1, high));
			if (res > 0)
				return (binarySearchRecursive(a, obj, low, mid - 1));
			
			//equal!
			return mid; //base case found
		}
		return -1; // base case not found
	}
    
	public <T extends Comparable<T>> int binarySearchTr (T [] a, T obj, int low, int high) 	{
		for (int i = 0; i < low; i++) System.out.print(" ");  // indent to indicate call level
		for (int i = low; i < high; i++) System.out.print("*");
		for (int i = high; i < a.length; i++) System.out.print(" ");
		System.out.print("LOW: " + low + " HIGH:" + high);

		int ans, temp;
		if (low <= high) 	{
			int mid = (low + high)/2;
			T midItem = a[mid];
			System.out.print(" MID: " + mid);
			System.out.print(" KEY: " + obj + " MIDITEM: " + midItem);
                  
			int res = midItem.compareTo(obj);
			if (res < 0)	{
				System.out.println(" RECURSE RIGHT");
				temp = binarySearchTr(a, obj, mid + 1, high);
				for (int i = 0; i < low; i++) System.out.print(" ");  // indent to indicate call level
				for (int i = low; i < high; i++) System.out.print("*");
				for (int i = high; i < a.length; i++) System.out.print(" ");
				System.out.println("PASSING ON " + temp);
				return (temp);
			}
			if (res > 0)	{
				System.out.println(" RECURSE LEFT");
				temp = binarySearchTr(a, obj, low, mid - 1);
				for (int i = 0; i < low; i++) System.out.print(" ");  // indent to indicate call level
				for (int i = low; i < high; i++) System.out.print("*");
				for (int i = high; i < a.length; i++) System.out.print(" ");
				System.out.println("PASSING ON " + temp);
				return (temp);
			}
			
			return mid;
		}
		System.out.println(" BASE CASE: Return -1");
		return -1;
	}

	// This is the same recursive sequential search as in SequentialSearch.java.  However,
	// I needed it as an instance method here so it could modify the scomps variable.
	public <T extends Comparable<T>> int seqSearchRecursive(T [] a, T key, int index)	{
		if (index >= a.length)
			return -1;
		scomps++;
		if (a[index].compareTo(key) == 0)
			return index;
		else return seqSearchRecursive(a, key, index+1);
	}
	
	public static Integer [] loadData(int num)	{
		Integer [] data = new Integer[num];
		for (int i = 0; i < num; i++) {
			Integer item = Integer.valueOf((int) (Math.random() * 1000));
			data[i] = item;
		}
		Arrays.sort(data);
		
		System.out.println("Data loaded");
		return data;
	}
	
	public static void main (String [] args) throws IOException	{
		BufferedReader BR = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("Please enter size of array: ");
		int num = Integer.parseInt(BR.readLine());
		Integer [] data = loadData(num);
		
		System.out.println("Please enter number of searches: ");
		int numReps = Integer.parseInt(BR.readLine());
		
		BinarySearch prog = new BinarySearch(data, numReps);
		prog.runTest();
	}
}