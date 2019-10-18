package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		
		HashMap<String,Occurrence> wordMap = new HashMap<String,Occurrence>();
		Scanner sc = new Scanner (new File(docFile));
		
		while (sc.hasNext())
		{
			String word = sc.next();
			word = getKeyword(word);
			
			if (word == null)
			{
				continue;
			}
			
			//check for noisewords
			
		
			
			Occurrence oc = new Occurrence (docFile ,1);
			Occurrence retOc;
			retOc = wordMap.putIfAbsent(word, oc);
			
			if (retOc != null)
			{
				retOc.frequency++;
				wordMap.replace(word, retOc);
			}
		}
		
		System.out.println("WORDMAP : " + wordMap);
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return wordMap;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		
		Set<String> keySet = kws.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext())
		{
			String keyString = (String) iter.next();
			Occurrence occ = kws.get(keyString);
			
			if (keywordsIndex.containsKey(keyString))
			{
				ArrayList<Occurrence> gOccurrence = keywordsIndex.get(keyString);
				gOccurrence.add(occ);
				insertLastOccurrence(gOccurrence);
				keywordsIndex.replace(keyString, gOccurrence);
				
			}
			
			else 
			{
				ArrayList<Occurrence> gOccurrence  = new ArrayList<Occurrence>();
				gOccurrence.add(occ);
				keywordsIndex.put(keyString, gOccurrence);
			}
		}
		
		System.out.println("Exiting mergeKeywords");
		System.out.println("WORDMAP : " + keywordsIndex);
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		
		System.out.println("entering getKeyword" );
		System.out.println("input = " + word);
		
		String retWord = "";
		HashSet<String> punctuation = new HashSet<String> (100);
		punctuation.add(".");
		punctuation.add(",");
		punctuation.add("?");
		punctuation.add(":");
		punctuation.add(";");
		punctuation.add("!");
		word = word.toLowerCase();
		
		retWord = word;
		
		for (int i =0; i < word.length(); i++)
		{
			if (punctuation.contains(word.substring(i, i+1)))
			{
				if (!word.substring(i).matches("^[.,?:;!]+$"))
				{	
					return null;
				}
					retWord = word.substring(0,i);
				
				break;
			}
					
		}
		
		if (!retWord.matches("^[a-z]+$"))
		{
			return null;
		}
		
		if (retWord.length() == 0)
		{
			return null;
		}
		
		
		//check for noise words
		
		if (noiseWords.contains(retWord))
		{
			return null;
		}
		
		System.out.println("retWord = " + retWord);
		return retWord;
		
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		//return null;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		
		
		ArrayList<Integer> retArrList = new ArrayList<>();
		int begin = 0;
		int last = occs.size() - 2;
		Occurrence occTobeInserted = occs.get(occs.size() - 1);
		int freeqTobeInserted = occTobeInserted.frequency;
		int mid = -1;
		while (begin <= last)
		{
			mid = (begin + last) / 2;
			retArrList.add(mid);
			if (occs.get(mid).frequency > freeqTobeInserted)
			{
				begin = mid + 1;
			}
			
			else if (occs.get(mid).frequency < freeqTobeInserted)
			{
				last = mid - 1;
			}
			
			else
			{
				break;
			}
		}
		
		if (mid > -1)
		{
			if (occs.get(mid).frequency >freeqTobeInserted)
			{
				occs.add(mid + 1, occTobeInserted);
			}
			
			else 
			{
				occs.add(mid,occTobeInserted);
				occs.remove(occs.size() - 1);
			}
		}
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return retArrList;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		
		kw1 = kw1.toLowerCase();
		kw2 = kw2.toLowerCase();
	
		ArrayList<Occurrence> keywords1 = keywordsIndex.get(kw1);
		ArrayList<Occurrence> keywords2 = keywordsIndex.get(kw2);
		
		Iterator<Occurrence> iL1 = null;
		Iterator<Occurrence> iL2 = null;
		
		
		if (keywords1 != null)	
		{
			iL1 = keywords1.iterator();
		}
		
		if (keywords2 != null)
		{
			iL2 = keywords2.iterator();
		}
		
		ArrayList<Occurrence> copyKeywords1 = new ArrayList<Occurrence> ();
		ArrayList<Occurrence> copyKeywords2 = new ArrayList <Occurrence> ();
		
		while(iL1 != null && iL1.hasNext())
		{
			
			Occurrence o = iL1.next();
			System.out.println("l1.next = " + o);
			copyKeywords1.add(o);
			
		}
		
		while (iL2 != null && iL2.hasNext())
		{
			Occurrence o = iL2.next();
			copyKeywords2.add(o);
			
		}
		
		iL1 = copyKeywords1.iterator();
		iL2 = copyKeywords2.iterator();
		
		while(iL2.hasNext())
		{
			copyKeywords1.add(iL2.next());
			insertLastOccurrence(copyKeywords1);
		}
		
		
		System.out.println("list 1 : " + copyKeywords1);
		
		ArrayList<String> retArrayList = new ArrayList<>();
		Iterator<Occurrence> iLRet= copyKeywords1.iterator();
		int i = 0;
		while (iLRet != null && iLRet.hasNext() && i<=4)
		{
			
			Occurrence o = iLRet.next();
			
			if (!retArrayList.contains(o.document))
			{	
				retArrayList.add(o.document);
				i++;
			}
		}
		
		System.out.println("copylist = " + copyKeywords1);
		
		System.out.println("Unique list = " + retArrayList);
		System.out.println("exxxit top5");
		
	
		
		
		//merge the two lists and sort
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		//SECOND
		return retArrayList;
	
	}
}
