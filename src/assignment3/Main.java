/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * David Bush
 * Dcb2474
 * 16220
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Spring 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> parseList = new ArrayList<String>();
		String input = keyboard.nextLine();
		if(input.equals("/quit"))
			return parseList;
		else {
			String[] words = input.split("\\s");
			if(words.length == 2) {
				parseList.add(words[0]);		//adding beginning word
				parseList.add(words[1]);		//adding ending word
			}
		}
		return parseList;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {	
    	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		Set<String> dict = makeDictionary();
		ArrayList<Node> visited = new ArrayList<Node>();
		Queue<Node> queue = new LinkedList<Node>();	
		Node root = new Node(start.toLowerCase());
		queue.add(root);
		while(!queue.isEmpty()) {														//Explore all Nodes in the Queue
			Node current = queue.remove();
			if(current.getWord() == end.toLowerCase()) {
				ArrayList<String> trace = new ArrayList<String>();
				trace.add(end);
				while(current != null) {
					trace.add(0, current.getWord());									//Traces backwards, adding string to beginning
					current = current.getParent();
				}
				trace.add(0, start);
				return trace;
			}
			for(int i = 0; i < start.length(); i++) {
				for(int j = 0; j < 26; j++) {
					String newWord = start.substring(0, i) + alphabet[j] + start.substring(i, start.length());	//Finding all possible permutations in the Dictionary
					if(dict.contains(newWord.toUpperCase()) || dict.contains(newWord.toLowerCase())) {
						Node addNode = new Node(newWord);
						if(!visited.contains(addNode)) {
							visited.add(addNode);
							addNode.setParent(current);
							queue.add(addNode);
						}
					}
				}
			}
		}
		ArrayList<String> failed = new ArrayList<String>();
		failed.add(start);
		failed.add(end);
		return failed;
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		if(ladder.size() == 2) {
			System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
		}
		String start = ladder.get(0);
		String end   = ladder.get(ladder.size() - 1);
		System.out.println("a " + (ladder.size()-2) + "-rung word ladder exists between " + start + " and " + end + ".");
		for(int i = 0; i < ladder.size(); i++) {
			System.out.println(ladder.get(i).toLowerCase());
		}
	}
	// TODO
	// Other private static methods here
	
}
