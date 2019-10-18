package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
			return buildTrieRecurse(allWords,  null, allWords);
		}
		
		
		
	private static TrieNode buildTrieRecurse(String[] inAllWords, TrieNode root, String [] origWords) {
		TrieNode rootTrie;
		if (root == null)
			rootTrie = new TrieNode (null,null,null);			
		else
			rootTrie = root;
		// make a copy of allWords
		String [] allWords = new String[inAllWords.length];
		for ( int g=0;g<inAllWords.length;g++)
			allWords[g] = inAllWords[g];
		
		
		printTrieDictionary (allWords);
		for (int i = 0; i < allWords.length; i++)
		{	
			if (allWords[i].equals(""))
			{
				continue;
			}
			String commonPrefix = findCommonPrefix(allWords, allWords[i]);
			System.out.println("Common Prefix = " + commonPrefix);
				
			if (commonPrefix == null || commonPrefix.equals(""))
			{
//				Indexes newIndex = new Indexes (i, (short) 0, (short) (allWords[i].length()-1 ));
				Indexes newIndex;
				if ( rootTrie.substr == null )
				{
					newIndex = new Indexes (
							i, 
				            (short)0,
				            (commonPrefix.length()==0?(short) (allWords[i].length()-1 ):(short)(commonPrefix.length()-1)));
				}
				else {
					newIndex = new Indexes (
						i, 
			            (short) (rootTrie.substr.endIndex+1+commonPrefix.length()),
			            (short)(origWords[i].length()-1));
				}
				TrieNode newNode = new TrieNode (newIndex, null, null);
					
					if (rootTrie.firstChild == null)
					{
						rootTrie.firstChild = newNode;
					}
					
					else 
					{
						System.out.println("here (48");
						TrieNode resultNode = getLastNode (rootTrie.firstChild);
						resultNode.sibling = newNode;				
						
					}
				}
				
				else if (commonPrefix != null || !commonPrefix.equals(""))
				{
					Indexes newIndex;
					if ( rootTrie.substr == null )
					{
						newIndex = new Indexes (
								i, 
					            (short)0,
					            (short)(commonPrefix.length()-1));
					}
					else {
						newIndex = new Indexes (
							i, 
				            (short) (rootTrie.substr.endIndex+1),
				            (short)(rootTrie.substr.endIndex+commonPrefix.length()));
					}
				    //       (short) (origWords[rootTrie.substr.wordIndex]).length()-1 ));
					TrieNode newNode = new TrieNode (newIndex, null, null);
					TrieNode resultNode = null;
					if (rootTrie.firstChild == null)
					{
						rootTrie.firstChild = newNode;
						newNode.sibling = null;
					}
					
					else 
					{
						resultNode= getLastNode(rootTrie.firstChild);
						resultNode.sibling = newNode;				
					}
					
					//recursively call the function to build tree
					String [] newWordList = commonPrefixArray(allWords, 0, commonPrefix);
					System.out.println("beg new word list");
					System.out.println("common Prefix ="+commonPrefix);
					printTrieDictionary (newWordList);
					System.out.println("new word list printed");
					// tree build complete
					
					TrieNode recursiveNode = buildTrieRecurse (newWordList, newNode,origWords);
					
					//delete used dictionary words, delete all words with common prefix
					for (int k = 0; k <allWords.length; k++)
					{
						if (allWords[k].startsWith(commonPrefix))
						{
							allWords[k] = "";
 						}
					}
					
					//print(resultNode, newWordList);
				}
				
			}
		
		
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return rootTrie;
	
	}
	
	private static String [] commonPrefixArray (String [] allWords, int startPrefix, String commonPrefix)
	{
		//NEW VERSION - 2
		ArrayList<String> commonPrefixArrayList = new ArrayList<String> ();
		int length = 0;
		printTrieDictionary(allWords);
		System.out.println("Start Prefix = " + startPrefix);
		System.out.println("Common Prefix = "  + commonPrefix);
		for (int i =0 ; i < allWords.length; i++)
		{
			
			
			if (!allWords[i].equals("") && allWords[i].substring(startPrefix,commonPrefix.length()).equals(commonPrefix))
			{
				System.out.println("(91)length = " + allWords[i].substring(startPrefix,commonPrefix.length()));
				commonPrefixArrayList.add(allWords[i].substring(commonPrefix.length()));
			}
			else 
			{
				commonPrefixArrayList.add(new String(""));
			}

		}
		length = commonPrefixArrayList.size();
		String [] commonPrefixArray = new String [length];
		commonPrefixArray = commonPrefixArrayList.toArray(new String[0]);
		
		return commonPrefixArray;
	}
	
	
	private static TrieNode getLastNode (TrieNode input)
	{	
		TrieNode returnNode = input;
		while (input != null)
		{
			returnNode = input;
			input = input.sibling;
			
			
		}
		return returnNode;
	}
	
	
	private static void printTrieDictionary (String [] allWords)
	{
		for (String g : allWords)
		{
			System.out.println(g);
		}
			
	}
	
	private static String findCommonPrefix (String [] allWords, String input)
	{
		String firstLetter = input.substring(0, 1);
		String noCommon = "";
		boolean notFirst = false;
		String result = "";
		int prevCount = 0;
		int count = 0;
		
		
		
		System.out.println("Input = " + input);
		result = input;
		
		for (int i = 1; i <= input.length() ; i++)
		{
			prevCount = count;
			count = 0;
			
			for (int j = 0; j < allWords.length; j++)
			{	
				if (allWords[j].length() < i)
				{
					continue;
				}
				if (input.substring(0,i).equals(allWords[j].substring(0, i)))
				{
					count++;
					result = input.substring(0,i);
				}
				else
					result = input.substring(0,i);
				
				
			}

			
			if (count > 1 && (prevCount==0 || prevCount == count))
			{
				notFirst = true;
				System.out.println("here (88)");
				continue;
			
			}
			if (notFirst != true && count == 1)
			{
				System.out.println("here (94)");
				//String result = input.substring(0,i+1);
				return noCommon;
			}
			
			if (notFirst == true && prevCount > count )
			{
				System.out.println("here (101)");
				result = input.substring(0,i-1);
				return result;
			}
			
		}
		
		System.out.println("out of loop return result");
		return result;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		
		/** COMPLETE THIS METHOD **/
		//ADDED NOW
		
		System.out.println("beg of completionList");
		
		//TrieNode firstChildRoot = root.firstChild;
		//TrieNode initialfirst = firstChildRoot;
		//boolean match = false;
		
		ArrayList<TrieNode> outList = new ArrayList<TrieNode> ();
		
		returnMatch (allWords, prefix, root, outList);
		ArrayList<TrieNode> finalList = outList;
		System.out.println("final list: " + finalList);
		System.out.println("outList" + outList);
//		while (!match)
//		{
//			System.out.println("in loop");
//			System.out.println(firstChildRoot.toString());
//			int begIndex = 0;
//			int endIndex = 1;
//			System.out.println("enter loop");
//			System.out.println("didnt enter");
//		}
		
//		while (firstChildRoot.firstChild != null)
//			{
//				System.out.println("Looking at " + firstChildRoot.substr);
//				if (allWords[firstChildRoot.substr.wordIndex].startsWith(prefix.substring(begIndex, endIndex)))
//				{
//					finalList.add(firstChildRoot);
//				}
//				
//				else 
//				{
//					if (firstChildRoot.sibling != null)
//					{
//						firstChildRoot = firstChildRoot.sibling;
//						continue;
//					}
//					
//					else
//					{
//						initialfirst = firstChildRoot.firstChild;
//						firstChildRoot = initialfirst;
//					}
//					
//				}
//			}
			
		
		//ADDED NOW
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		System.out.println("final list = " + finalList);

		if (finalList.size() == 0)
		{
			System.out.println("Returning null final List");
			finalList= null;
			return finalList;
		}
		return finalList;
	}
	
	
	private static boolean returnMatch (String [] allWords, String prefix, TrieNode root, ArrayList<TrieNode> outList)
	{
		boolean match = false;
		
		if (root == null)
		{
			return true;
		}
		TrieNode first = root.firstChild;
		ArrayList<TrieNode> finalList = outList;
		
		System.out.println("FIRST = " + allWords[first.substr.wordIndex]);
		
		if (first == null)
		{
			return true;
		}
		
		while (first != null && !match)
		{
			
			System.out.println("EFFICIENCY : " + first);
			//System.out.println("inside loop");
			int startIndex = first.substr.startIndex;
			int endIndex = first.substr.endIndex;
			int wordIndex = first.substr.wordIndex;
			
			//System.out.println("start index = " + startIndex);
			//System.out.println("End Index = " + endIndex);
			//System.out.println("word index = " + wordIndex);
			
			//System.out.println("PREFIX = " + prefix);
			System.out.println(allWords[wordIndex].substring(startIndex, endIndex + 1));
			System.out.println(first);
			if (prefix.length() <= allWords[wordIndex].substring(startIndex, endIndex + 1).length())
			{
				if (allWords[wordIndex].substring(startIndex,endIndex + 1).
						startsWith(prefix))
				{
					System.out.println("in loop less than");
//					finalList.add(first);
					returnAll(first, finalList);
					System.out.println("added to finalList");
					return true;
				}
				
			}
			
			else if (prefix.length() > allWords[wordIndex].substring(startIndex, endIndex).length())
			{
				
				System.out.println("all words substring" + allWords[wordIndex].substring(startIndex,endIndex + 1));
				System.out.println("prefix = " + prefix);
				//System.out.println("prefix substring" + prefix.substring(startIndex, endIndex + 1));
				
				if (allWords[wordIndex].substring(startIndex,endIndex + 1).
						startsWith(prefix.substring(0, endIndex - startIndex +1)))
				{
					System.out.println("in loop greater than");
					String newPrefix = prefix.substring(endIndex-startIndex + 1);
					//System.out.println("NEW PREFIX = " + newPrefix);
					//System.out.println("word = " + allWords[wordIndex].substring(startIndex,endIndex + 1));
					//System.out.println("comparing to = " + allWords[wordIndex].substring(endIndex + 1));
					//System.out.println("comparing to word = " + allWords[wordIndex]);
					match = returnMatch(allWords, newPrefix, first, outList);
						if (allWords[wordIndex].substring(endIndex + 1).
								startsWith(newPrefix))
						{
							
							//finalList.add(first);
							
							//returnMatch(allWords, newPrefix, first, outList);
							
						}
						
						
					
					
				}
			}
			
			
			//System.out.println("first.sibling" + first.sibling);
			
			first = first.sibling;
			
		}
		
	
	
	if (finalList.size() == 0)
	{
		System.out.println("Returning null final List");
		finalList= null;
		return true;
	}
	
	return true;
		
	
	
	
	
	
//	System.out.println("word = " + allWords[wordIndex].substring(startIndex,endIndex + 1));
//	if (newPrefix.length() < allWords[wordIndex].substring(startIndex,endIndex).length())
//	{
//		if (allWords[wordIndex].substring(endIndex + 1).
//				startsWith(newPrefix))
//		{
//			System.out.println("in loop greater than");
//			finalList.add(first);
//			
//			returnMatch(allWords, newPrefix, first, outList);
//		}
//		
//		
//	}
//			
		
		
	}
	
	private static void returnAll(TrieNode root, ArrayList<TrieNode> inputList)
	{
		ArrayList<TrieNode> finalList = inputList;
		if (root.firstChild == null)
		{
			finalList.add(root);
		}
		TrieNode first = root.firstChild;
		System.out.println("beginning of function first = " + first);
		while (first != null)
		{
			if (first.firstChild == null)
			{
				System.out.println("first.firstChild = " + first.firstChild);
				finalList.add(first);
				System.out.println("first.sibling = " + first.sibling);
				first = first.sibling;
				System.out.println("first end of loop" + first);
				
			}
			
			
			else if (first.firstChild != null)
			{
				System.out.println( "first = " + first);
				returnAll(first, finalList);
				first = first.sibling;
			}
			
		}
		
		System.out.println("returning");
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
