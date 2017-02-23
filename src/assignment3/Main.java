/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * David Bush
 * Dcb2474
 * 16220
 * Minsu Roh
 * mr54448
 * 16220
 * Slip days used: <1>
 * Git URL:https://github.com/Zarodd/
 * Spring 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	static HashSet<String> visited;
	static ArrayList<String> ladder;
	static Set<String> dict;
	
	static boolean found;
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
		while(true) {
			initialize();
			ArrayList<String> input = parse(kb);
			if(input.isEmpty()) {
				return;
			}
			else if(input.get(0) == "/quit") {
				return;
			}
			else {
				ArrayList<String> ladderBFS = getWordLadderBFS(input.get(0), input.get(1));
				printLadder(ladderBFS);
				
				ArrayList<String> ladderDFS = getWordLadderDFS(input.get(0), input.get(1));
				ladderDFS.add(0, input.get(0));
				printLadder(ladderDFS);
			}
		}
	}
	
	public static void initialize() {
		visited = new HashSet<String>();
		ladder = new ArrayList<String>();
		found = false;
		dict = makeDictionary();
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
	
	public static ArrayList<Node> Collect(String start)
	{	
		ArrayList<Node> explore = new ArrayList<Node>();
		char [] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		for(int i = 1; i <= start.length(); i++) {
			for(int j = 0; j < 26; j++) {
				String newWord = start.substring(0, i-1) + alphabet[j] + start.substring(i, start.length());
				if(dict.contains(newWord.toUpperCase())) {
					Node N = new Node(newWord);
					explore.add(N);
				}
			}
		}
		return explore; // tree has all the nodes.
	}
	public static boolean DifferentByOne(String n, String m)
	{	
		boolean isdiff = false;
		if(n.length() != m.length())
		{
			return false;
		}
		for (int i=0; i<n.length();i++)
		{
			if(n.charAt(i) != m.charAt(i)); // check if its same by position
				{
					if(isdiff)
					{
						return false;
					}
				  isdiff = true;
				}
		}
	return isdiff;
	}
	
	
	
	public static ArrayList<String> getWordLadderDFS(String start, String end)
	{	
		//ArrayList<Node> list = new ArrayList<Node>();
		visited.add(start);
		ArrayList<Node> explore;			//for parent
		Node cur = new Node(start.toLowerCase());
		
		explore = Collect(start);
		for(Node n: explore) {
			
			if(visited.contains(n.getWord())) {
				continue;
			}
			visited.add(n.getWord());
			if(n.getWord().equalsIgnoreCase(end)) {
				ladder.add(0, end);
				found = true;
				return ladder;	
			}
			getWordLadderDFS(n.getWord(), end);
			if(found) {
				ladder.add(0, n.getWord());
				return ladder;
			}
		}
		return null;
}
		

    public static ArrayList<String> getWordLadderBFS(String start, String end) {	
    	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		Set<String> dict = makeDictionary();
		ArrayList<String> visited = new ArrayList<String>();
		Queue<Node> queue = new LinkedList<Node>();	
		Node root = new Node(start.toLowerCase());
		queue.add(root);
		while(!queue.isEmpty()) {														//Explore all Nodes in the Queue
			Node current = queue.remove();
			if(current.getWord().equalsIgnoreCase(end)) {
				ArrayList<String> trace = new ArrayList<String>();
				while(current != null) {
					trace.add(0, current.getWord());									//Traces backwards, adding string to beginning
					current = current.getParent();
				}
				return trace;
			}
			for(int i = 1; i <= start.length(); i++) {
				for(int j = 0; j < 26; j++) {
					String newWord = current.getWord().substring(0, i - 1) + alphabet[j] + current.getWord().substring(i, current.getWord().length());	//Finding all possible permutations in the Dictionary
					if(dict.contains(newWord.toUpperCase())) {
						Node addNode = new Node(newWord);
						if(!visited.contains(newWord)) {								//Check if previously visited
							visited.add(newWord);
							addNode.setParent(current);									//Set Parent of the new node
							current.addChildren(addNode);								//Add new node to children of current node
							queue.add(addNode);
						}
					}
				}
			}
		}
		ArrayList<String> failed = new ArrayList<String>();								//Only reaches this if no path is found, so generate error list
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
		if(ladder == null) {
			System.out.println("no word ladder can be found between " + " and " + ".");
			return;
		}
		else if(ladder.size() == 2) {
			System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
			return;
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
