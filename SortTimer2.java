/**
 * Program to do simple sort algorithm timing	
 * 
 * Adapted from CollectionTimer by 
 * @author Benjamin Kuperman (Fall 2013)
 * @author Philip Papadopoulos (Spring 2014)
 */

import java.util.*;
import java.io.*;

public class SortTimer2 {
	
	// default values for command line parameters
	private static final int NUMWORDS = 5000;
	private static final int INCREMENT = 5000;
	private static final int STEPS = 5;
	private static final int NUM_REPS = 5;
	private static final int BUBBLE = 0;
	private static final int INSERTION =  BUBBLE + 1;
	private static final int MERGE = INSERTION + 1;
	private static final int QUICK = MERGE + 1;
	private static final int HEAP = QUICK + 1;

	
	// set this to true to see some internal counts and status updates
	private static final boolean DEBUG = false;
	
	public static void main(String[] args) {
		
		boolean doPrint = false;
		// Make sure they specified a File and Algorithm to use 
		if (args.length < 2) {
			usage();
			System.exit(1);
		}
		int idx = 0;
		if (args[idx].equals("-p"))
		{
			doPrint = true;
			idx = 1;
		}
		
		// get the filenames for those
		String document = args[idx+0];
		// Which sort Algorithm
		int sortAlg = Integer.parseInt(args[idx+1]);
		if (sortAlg < BUBBLE || sortAlg > HEAP) {
			System.err.println("Unknown Sort Algorithm" + sortAlg);
			usage();
			System.exit(1);
		}
		
		int numwords = NUMWORDS;
		int increment = INCREMENT;
		int steps = STEPS;
		int reps = NUM_REPS;
		
		if (args.length >= idx+3) {
			numwords = Integer.parseInt(args[idx+2]);
		}
		if (args.length >= idx+4) { 
			increment = Integer.parseInt(args[idx+3]);
		}
		if (args.length >= idx+5) {
			steps = Integer.parseInt(args[idx+4]);
		}
		if (args.length >= idx+6 ) {
			reps = Integer.parseInt(args[idx+5]);
		}
				
		// Once times for the MyLinkedList
		doLoops(sortAlg, document, numwords, increment, steps, reps,doPrint);
		
	}
	
	private static void usage() {
		System.err.println("Usage: java  SortTimer2 [-p] <document> <sortAlg> [start] [increment] [steps] [reps]");
		
		System.err.format("\t -p	    - print the sorted list\n");
		System.err.format("\t document  - text file to sort \n");
		System.err.format("\t Algorithm - %d:bubble, %d:insertion: %d:merge, %d:quick %d:heap\n", 
				BUBBLE, INSERTION, MERGE, QUICK, HEAP);
		System.err.format("\t start     - number of words read in from  document\n");
		System.err.format("\t increment - number of words to add for each iteration \n");
		System.err.format("\t steps	    - number of iterations\n");
		System.err.format("\t reps      - number of times to repeat for timing \n");
	}
	
	public static String trimPunctuation(String s) {
		// remove all non letters or numbers from the start of the string
		s = s.replaceAll("^[^a-zA-Z0-9]+", "");
		
		// remove all non-letters or numbers from the end of the string
		s = s.replaceAll("[^a-zA-Z0-9]+$", "");
		
		return s;
	}
	
	/**
	 * Driver to loop through a number of work configurations.
	 * Actual work is performed in doWork()
	 * 
	 * @param document document to "spellcheck" against wordlist
	 * @param sortAlg algorithm to use for sorting 
	 * @param numwords initial number of words to read from the document
	 * @param increment amount to increase numwords by each time
	 * @param steps number of steps to perform
	 * @param reps number of repetitions to perform
	 * @param doPrint print the sorted array (Diagnostic) 
	 */
	private static void doLoops( int sortAlg, String document, int numwords,
				 int increment, int steps, int reps, boolean doPrint) {
		
		if (!doPrint)
		{
			System.out.printf("Document: %s\n sortAlg: %d\n", document, sortAlg);
			System.out.println("=======================================");
		}
		Sort12 sorter;
		for (int i = 1; i <= steps; i++) {
			// Reset the data structure
			long totalTime = 0;
			for ( int r = 0; r < reps; r++ ) {
				if ( sortAlg == BUBBLE) { 
					sorter = new Bubble12();
				}
				else if ( sortAlg == INSERTION ) {
					sorter = new Insertion12(); 
				}
				else if ( sortAlg == MERGE ) { 
					sorter = new Merge12(); 
				}
				else if ( sortAlg == QUICK ) { 
					sorter = new Quick12(); 
				}
				else if ( sortAlg == HEAP ) { 
					sorter = new HeapSort12(); 
				}
				else {
					throw new IllegalArgumentException( "Cannot instantiate sorter ");
				}
				if (DEBUG) {
					System.out.println( "REP " + r + ": " );
				}
				// do the work and keep track of the running time
				totalTime += doWork(sorter, document, numwords, doPrint);
			}
			long runtime = totalTime / reps;
		 
			// print the results in a table
			if (!doPrint)
				System.out.printf("%3d: %7d words in %7d milliseconds\n", i, numwords, runtime );
			
			// and increase for the next run
			numwords += increment;
		}
		
		if (!doPrint)
			System.out.println();
	}
	
	
	/**
	 * Do a simple "sort" to exercise different sort algorithms.
	 * 
	 * @param sorter Which sorter to use 
	 * @param document document to "spellcheck" against wordlist
	 * @param numwords initial number of words to read from the document
	 * @param doPrint print the sorted input 
	 * @return number of milliseconds taken to sort N words 
	 */
	private static long doWork(Sort12 sorter, String document, int numwords,
			boolean doPrint ) {
		long start, end;
		
		// now read in words
		int nwords = 0;
		ArrayList<String>  unsorted = new ArrayList<String>(numwords);
		
		// get the start time
		long totalTime = 0;
		
		try {

			// open up the document
			FileInputStream in = new FileInputStream(document);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			// make sure we are ready to go
			unsorted.clear();
			
			String line;
			// read in the document
			while((line = br.readLine())!= null && nwords < numwords)
			{
				String[] arr = line.split( " " );
				for ( String word : arr ) {
					word = SortTimer2.trimPunctuation(word);
					unsorted.add(nwords++, word);
					if (DEBUG) {
						if (nwords %1000 == 0 ) {
							System.out.printf("\rRead in %d words", nwords);
						}
					}
				}
			}
    	} catch (IOException e) {
			System.out.println("problem reading from wordlist:" + e.getMessage());
			return 0;
    	}
	
		// get the start time 
		start = System.currentTimeMillis();
		sorter.sort(unsorted);
		// get the end time 
		end = System.currentTimeMillis();
		// count the number of milliseconds elapsed
		totalTime += ( end-start );
		if (doPrint)
		{
			Iterator<String> iter = unsorted.iterator();
			while (iter.hasNext())	
				System.out.println(iter.next());
		}
			
		return totalTime;
		
	}
	
}
// vim:ts=4:sw=4:sw=78
