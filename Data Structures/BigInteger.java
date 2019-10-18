package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		integer = integer.trim();
		boolean negpos = integer.matches("^[-+]?\\d+");
		if (negpos == false)
		{
			throw new IllegalArgumentException();
		}
		
		
		BigInteger DigitList  = new BigInteger ();
		DigitNode prev = null;
		boolean startOfDigit = false;
		if (integer.startsWith("+") || integer.startsWith("-"))
		{
			if (integer.startsWith("-"))
			{
				DigitList.negative = true;
			}
			
			integer = integer.substring(1, integer.length());
		
		}
		
		DigitNode a = null;
		DigitList.numDigits = 0;
		
		
		
		for (int i = 0; i < integer.length(); i++)
		{
			
			String digit = integer.substring(i, i+1);
			int result = Integer.parseInt(digit);
			if (startOfDigit == false && result==0)
			{
				continue;
			}
			startOfDigit = true;
			a = new DigitNode (result, null);
			DigitList.numDigits++;
			
			a.next = prev;
			prev = a;
			
		}
		
		DigitList.front = a;
		
		//System.out.println("IN parse = DigitList =" + DigitList+ "numDigits=" + DigitList.numDigits );
		return DigitList;
		
	
	//System.out.println("Enter a value:);
	//String value = **tell java to read line
			
		
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
	
		//System.out.println(isLarger(first,second));
		boolean larger = BigInteger.isLarger(first, second);
		if (larger == false)
		{
			BigInteger temp = first;
			first = second;
			second = temp;
		}
		BigInteger result = null;
		if (first.negative == false && second.negative==false)
		{
			result = BigInteger.plusFunction(first,second);
			result.negative = false;
		}
		if (first.negative == true && second.negative==true)
		{
			result= BigInteger.plusFunction(first, second);
			result.negative = true;
		}
		
		if (first.negative == true && second.negative==false)
		{
	
			result = BigInteger.minusFunction(first, second);
			result.negative = true;
		}
		if (first.negative == false && second.negative==true)
		{
			result = BigInteger.minusFunction(first, second);
			result.negative = false;
		}
	
		
		//one positive and one negative -- carry over backwards
		return result;
		
		
		
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		//return null; 
	}
	
	
	private static boolean isLarger(BigInteger first, BigInteger second)
	{//own helper for which is larger
		
		if (first.numDigits > second.numDigits)
		{
			return true;
		}
		if (second.numDigits > first.numDigits)
		{
			return false;
		}
		if (first.numDigits == second.numDigits)
		{
			DigitNode lastNodeFirst = first.front;
			DigitNode lastNodeSecond = second.front;
		
			for (int i = first.numDigits-1; i > 0; i--)
			{
				lastNodeFirst = first.front;
				lastNodeSecond = second.front;
				//System.out.println("i=" + i);
				for (int j = 0; j < i-1; j++)
				{
					//System.out.println("next j=" + j);
					lastNodeFirst = lastNodeFirst.next;
					lastNodeSecond = lastNodeSecond.next;
					
					
				}
				if (lastNodeFirst.digit > lastNodeSecond.digit)
				{
					return true;
				}
				if (lastNodeFirst.digit < lastNodeSecond.digit)
				{
					return false;
				}
				//otherwise continue if equals
				//not using boolean so absolute value
				
				
			}
		} 
		return true;
		//if first is larger than second
	}
	
	private static BigInteger plusFunction(BigInteger first, BigInteger second)
	{//own helper for two positive
		BigInteger resultBothPos = new BigInteger();
		DigitNode firstList = first.front;
		DigitNode secondList = second.front;
		DigitNode resultDigit = null;
		DigitNode lastNode = null;
		int firstDigitVal = 0;
		int secondDigitVal = 0;
		
		if (second.front == null)
		{
			return first;
		}
		
		//System.out.println("Numdigits" + first.numDigits);
		int carry = 0;
		for (int i =0; i < first.numDigits; i++)
			{
				if (firstList == null)
				{
					 firstDigitVal = 0;
				}
				else 
				{
					 firstDigitVal = firstList.digit;
				}
				if (secondList == null)
				{
					secondDigitVal = 0;
				}
				else 
				{
					 secondDigitVal = secondList.digit;
				}
				int sum = firstDigitVal + secondDigitVal + carry;
				carry = 0;
				if (sum > 9)
				{
					sum = sum %10;
					carry = 1;
					//System.out.println("sum carry");
				}
				
				resultDigit = new DigitNode (sum, null);
				//System.out.println("After newnode - sum" + sum);
				if (resultBothPos.front == null)
				{
					resultBothPos.front = resultDigit;
					resultBothPos.numDigits = 1;
				
					
				}
				else
				{
				lastNode = resultBothPos.front;
				for (int j = 0; j< resultBothPos.numDigits-1; j++)
				{
					lastNode = lastNode.next;
					
				}
					
				lastNode.next = resultDigit;
				resultBothPos.numDigits++;
				}
				firstList = firstList.next;
				
				if (secondList.next == null)
				{
					secondList.digit = 0;
				}
				else 
				{
					secondList = secondList.next;
				}
				
			}
		
				if (carry == 1)
					{
						DigitNode carry1 = new DigitNode(1,null);
						resultDigit.next = carry1;
					}
				
				
		
		return resultBothPos;
			
				
		
		
	}
	
	private static BigInteger minusFunction(BigInteger first, BigInteger second)
	{
		//helper for one positive and one negative
				BigInteger resultNeg = new BigInteger();
				DigitNode firstList = first.front;
				DigitNode secondList = second.front;
				DigitNode resultDigit = null;
				DigitNode lastNode = null;
				int sum = 0;
				
				
				if (second.front == null)
				{
					return first;
				}
				
				
		
				
				//System.out.println("Numdigits" + first.numDigits);
				int carry = 0;
				for (int i =0; i < first.numDigits; i++)
					{
						
						int firstDigitVal = firstList.digit;
						int secondDigitVal = secondList.digit;
						
						sum = firstDigitVal - secondDigitVal - carry;
						//changed this, orig sum was declared here only and not above
						if (sum < 0)
						{
							//System.out.println("carry=" + carry);
							sum = (firstDigitVal + 10) - secondDigitVal - carry;
							carry = 1;
							//System.out.println("sum=" + sum + "carry="+ carry);
						}
						
						else 
						{
							carry = 0;
						}
						
						resultDigit = new DigitNode (sum, null);
						//System.out.println("After newnode - sum" + sum);
						
						
						
						if ((i == first.numDigits-1) && (sum ==0))
						{
							continue;
						}
						
						
						
						if (resultNeg.front == null)
						{
							resultNeg.front = resultDigit;
							resultNeg.numDigits = 1;
							// KB Added
							
						}
						else
						{
						lastNode = resultNeg.front;
						for (int j = 0; j< resultNeg.numDigits-1; j++)
						{
							lastNode = lastNode.next;
							
						}
							
						lastNode.next = resultDigit;
						resultNeg.numDigits++;
						}
						firstList = firstList.next;
						
						if (secondList.next == null)
						{
							secondList.digit = 0;
						}
						else 
						{
							secondList = secondList.next;
						}
						
					}
				
				System.out.println("Result neg" + resultNeg);
				
				BigInteger zeroResult = deleteZeros(resultNeg);
					
				System.out.println("Zero result" + zeroResult);
				System.out.println(zeroResult.numDigits);
						
							
						
						return zeroResult;
						
						
						
				
				
	}
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		
		
		//System.out.println("Second num digits multiply=" + second.numDigits);
		//DigitNode firstDigit;
		
		
		int resultSign1 = 0;
		
		if (first.negative == true)
		{
			resultSign1 = -1;
		}
		
		int resultSign2 = 0;
		if (second.negative == true)
		{
			resultSign2 = -1;
		}
		
		
		BigInteger Pos1 = new BigInteger ();
		BigInteger Pos2 = new BigInteger();
		System.out.println(first.negative + ("firstline"));
		Pos1 = first;
		Pos1.negative = false;
		Pos2 = second;
		Pos2.negative = false;
		
		DigitNode secondDigit;
		
	
		
		boolean larger = BigInteger.isLarger(Pos1, Pos2);
		if (larger == false)
		{
			BigInteger temp = Pos1;
			Pos1 = Pos2;
			Pos2 = temp;
			//System.out.println("Larger"+BigInteger.isLarger(Pos1, Pos2));
		}
		
		
		if (Pos2.front == null)
		{
			BigInteger Zero = new BigInteger();
			return Zero;
		}
		BigInteger result = null;
		BigInteger finalResult = null;
		
		secondDigit = Pos2.front;
		
		//System.out.println("second.numdigits =" + second.numDigits);
		
		
		for (int i = 0; i < Pos2.numDigits; i++)
		{
			
			//System.out.println("Before multiply single digit= " + first + "second number =" + second);
		
			result = singleMultiply(Pos1, secondDigit.digit);
			//System.out.println("result.NumDigits=" + result.numDigits);
			
			
			secondDigit = secondDigit.next;
			//System.out.println(result);
			
			
			//System.out.println("Before Multiply Large result=" + result + "result.NumDigits" + result.numDigits);
			
			
			result = multiplyLarge(result, i);
			
			//System.out.println("After Multiply Large result=" + result + "result.NumDigits" + result.numDigits);
			
			
			if (finalResult == null)
			{
				finalResult = result;
			}
			
			else 
			{
				//System.out.println("result= " + result + " final= " + finalResult);
				BigInteger finalResult2;
				finalResult2 = add(result,finalResult);
				//System.out.println("result2= " + result + "final2=" + finalResult2);
				finalResult = finalResult2;
			}
			
			
			
		}
		
		if ((resultSign1==-1 && resultSign2==-1) || (resultSign1==0 && resultSign2==0))
		{
			finalResult.negative = false;
		}
		else
		{
			finalResult.negative = true;
		}
	
		
		
		
		return finalResult;
		
		
		
		
		
		
		
}
	
	private static BigInteger singleMultiply(BigInteger first, int singleDigit)
	{
		//helper method with single digit multiplier
		BigInteger resultMult = new BigInteger();
		DigitNode firstList = first.front;
		DigitNode resultDigit = null;
		DigitNode lastNode = null;
		int carry = 0;
		int product = 0;
		
		
		
		for (int i =0; i < first.numDigits; i++)
		{
			
			
			int firstDigitVal = firstList.digit;
			int secondDigitVal = singleDigit;
			
			product = firstDigitVal * secondDigitVal + carry;
			if (product > 9)
			{
				carry = product / 10;
				product = product %10;
				//System.out.println("sum carry");
			}
			
			else 
			{
				carry = 0;
			}
			
			resultDigit = new DigitNode (product, null);
			//System.out.println("After newnode - product" + product);
			if (resultMult.front == null)
			{
				resultMult.front = resultDigit;
				resultMult.numDigits = 1;
				
				
			}
			else
			{
			lastNode = resultMult.front;
			for (int j = 0; j< resultMult.numDigits-1; j++)
			{
				lastNode = lastNode.next;
				
			}
				
			lastNode.next = resultDigit;
			resultMult.numDigits++;
			}
			firstList = firstList.next;
			
						
		}
	
			if (carry >= 1)
				{
					DigitNode carry1 = new DigitNode(carry,null);
					//added carry value + product
					resultDigit.next = carry1;
					resultMult.numDigits++;
				
				}
			
			
	//System.out.println(resultMult.front.digit + "answer");
	return resultMult;
	}
	
	
	
	
	
	private static BigInteger multiplyLarge(BigInteger first, int count)
	{ // helper method
		
		for (int i = 0; i < count; i++)
		{
			//System.out.println("NumDigits loop=" + first.numDigits);
			DigitNode newZero = new DigitNode (0,null);
			newZero.next = first.front;
			first.front = newZero;
			first.numDigits++;
			
		}
		
		return first;
	}
	
	
	private static BigInteger deleteZeros(BigInteger first)
	{
		DigitNode lastNode = first.front;
		int cnt = first.numDigits - 1;
		for (int i = 0;i <  cnt;i++)
			{
			    lastNode = first.front;
				if (lastNode.next == null)
				{
					continue;
				}
				while (lastNode.next.next != null)
				{
					lastNode = lastNode.next;
					
				}
				
				if (lastNode.next.digit == 0)
				{
					lastNode.next = null;
					first.numDigits--;
					
				}
			}
		
		return first;
		
		
			
		
	}
		
	

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
}
