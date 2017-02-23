 /* Tester.java
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

import static org.junit.Assert.*;
import java.util.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester {
	
	
	public static void setUp() 
	{
		Main.initialize();
	}
	@Test
	public void dfs()
	{
		ArrayList<String> ladder = Main.getWordLadderDFS("smart", "money");
		Main.printLadder(ladder);
		HashSet<String> ladderSet = new HashSet<String>(ladder);
		assertEquals(ladder.size(), ladderSet.size());
	}
	@Test
	public void dfs_noladder() 
	{
		ArrayList<String> ladder = Main.getWordLadderDFS("start", "finish");
		Main.printLadder(ladder);
	}
}