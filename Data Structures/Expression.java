package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
	
	
	
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	
    	
    String myDelims = " \t*+-/()";
    
    	
    	StringTokenizer newTokens = new StringTokenizer (expr, myDelims);
    	String tok = "";
    	boolean first = false;
    	// if expre == integer
    while (newTokens.hasMoreTokens())
   {
    		tok = newTokens.nextToken();
    		if (tok.contains("]"))
    		{
    			tok = tok.replace(']', ' ');
    			tok = tok.trim();
    			System.out.println("replaced");
    		}
    		
    		{System.out.println("KB:"+ tok);
    		
    		}
    		
    		
    		if (isNumber(tok))
    		{
    			//System.out.println(tok + "Number");
    			continue;
    		}
    		
 
    		
    		if (isVariable(tok))
    		{
    			Variable newVars = new Variable(tok);
    			vars.add(0, newVars);
    			//System.out.println("Variable added" + vars);
    			continue;
    			
    		}	
    		
    		
    		if(isArray(tok))
    			// if next value is not a number than next 
    		{
    			
    			StringTokenizer arraytok = new StringTokenizer (tok, "[");
    			System.out.println("in array loop : " + tok);
    			String currentTok = tok.substring(0, tok.length()-1);
    			System.out.println("CURRENT TOK = " + currentTok);
        		while (tok != null)
        			{
        				try 
        					{
        						tok = arraytok.nextToken();
        						System.out.println("Tok = " + tok);
        						
        						if (isNumber(tok) == true)
        						{
        							continue;
        						}
        						if (!arraytok.hasMoreTokens())
        						{	//JUST ADDED NOW
        							if (tok.equals(currentTok))
        							{
        								Array arrayInput = new Array (tok);
                						System.out.println("array added in loop");
                		    				arrays.add(0,arrayInput);
                		    				continue;
        							}
        							// JUST ADDED NOW
        							System.out.println(tok);
        							Variable newVars = new Variable(tok);
        							System.out.println("here : ");
        			    				vars.add(0, newVars);
        			    			//System.out.println("Variable adde2.txtd" + vars);
        			    				continue;
        						}
        						Array arrayInput = new Array (tok);
        						System.out.println("array added in loop");
        		    				arrays.add(0,arrayInput);
        		    				
        		    				
        						//tok = tok.replace("[", " ");
        						//tok = tok.trim();
        					} 
        				
        				catch (Exception e)
        					{
        						tok = null;
        					}
        				
        			}
        		
        		System.out.println("final tok = " + tok);
        	
    		}
    		
    }
    
    System.out.println("Variable added" + vars);
    System.out.println("Array added" + arrays);
    printVariables(arrays, vars);
    }
    
    
    private static boolean isVariable(String input)
    {
    		System.out.println("INITIAL INPUT = " + input);
    		if (input.contains("["))
    		{
    			return false;
    		}
    		
    		if (input.matches("^[a-zA-Z0-9]*$"))
    		{
    			return true;
    			
    		}
    		
    		return false;
    		
    		
    }
    
    private static boolean isValidVariable(String input,ArrayList<Variable>vars)
    {
    	    for (Variable g : vars)
		{
			if (g.name.equals(input)) 
				 return true;
		
		}
    		return false;
    }
    private static boolean isValidArray(String input, ArrayList<Array>arrays)
    {
    		System.out.println("ENTER ARRAY" + input);
    	    for (Array g : arrays)
		{
			if (g.name.equals(input)) 
				 return true;
		
		}
    		return false;
    }
    private static boolean isNumber(String input)
    {
    		
    		if(input.matches("^[0-9]*$"))
    		{
    			return true;
    		}
    		return false;
    }
    
    private static boolean isArray(String input)
    {
    		if (input.contains("["))
    		{ 	System.out.println("ARRAYS = " + input);
    			return true;
    		}
    		
    		return false;
    }
    
    
    private static int lookUpVar(String name, ArrayList<Variable> vars)
    {
    		for (Variable g : vars)
    			{
    				System.out.println("VAL = " +  g.value + "NAME" + g.name);
    				if (name.equals(g.name))
    				{
    					return g.value;
    				}
    			}
    		
    		 return -1;
    }
    
    
    private static Float lookUpArray (String name, int index , ArrayList<Array> arrays)
    {
    		for (Array a : arrays)
    		{
    			if (a.name.equals(name))
    			{
    				return new Float (a.values[index]);
    			}
    		}
    		return new Float  (-1);
    }
     
    
    private static float operate(String operator, float operand1, float operand2)
    {
    		if (operator.equals("+"))
    		{
    			return operand1 + operand2;
    		}
    		
    		if (operator.equals("-"))
    		{
    			return operand1 - operand2;
    		}
    		
    		if (operator.equals("*"))
    		{
    			return operand1 * operand2;
    		}
    		
    		if (operator.equals("/"))
    		{
    			if (operand2 == 0)
    			{
    				return 0;
    			}
    			
    			return operand1 / operand2;
    		}
    		return -1;
    }
    
    
    private static void printVariables(ArrayList<Array> arrays, ArrayList<Variable> vars)
    {	
    		for (Variable g : vars)
		{
			System.out.println("(232) Variable Name = " +  g.name + "   value = " + g.value);
		
		}
    		
    		for (Array a : arrays)
    		{
    			System.out.println("(238) Array Name = " + a.name + "    value = " + a.values );
    			if (a.values == null)
    			{
    				continue;
    			}
    			for (int b : a.values)
    			{
    				System.out.print(b + ", ");
    			}
    		}
    }
    
    	
    	
    	
    	//if 3 * a
    	// create a new variable, x = new variable x.name =a, value = 0;
    	// public string variable 
    	// know its an array if it has a bracket "["
    	// for something like xyz have to create 3 new variables with x,y,z
    	// 3 - 4*5 
    	// any variable followed by opening bracket - create a new array
    	//.matches to figure out if it is integer - if integer, continue
    	// tockenizer 
    // create a new variable and then vars. add
    // vars is array list variable, and variable is an element of that, vars is an ara
    
    //
    	
    	
    	
    	
    	
    	
    	
    	
    	
  
    	
    	
    	
    	
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	
    	
    	Stack <String> operatorStack = new Stack<String> ();
    	Stack <Float> operandStack = new Stack<Float> ();
    	
    parseExpression (expr, arrays, vars, operandStack, operatorStack);
    
    
    
    float evaluatedExpr = performOperations (operatorStack, operandStack,false);
    System.out.println("ANSWER = " + evaluatedExpr);
    	
  	
  
    
    	// following line just a placeholder for compilation
    	return evaluatedExpr;
    }
    
    
   /* private static float insidePar(String input)
    {
    		
    		int position1 = 0;
    		int position2 = 0;
    		
    	
    		for (int i = 0; i < input.length(); i++)
    		{
    			if(input.charAt(i) == '(')
    			{
    				position1 = i;
    				System.out.println("First = " + position1);
    				continue;
    			}
    			
    			if (input.charAt(i) == ')')
    			{
    				position2 = i;
    				System.out.println("LAST = " + position2 );
    			}
    			
    			
    			if (position1 != 0 && position2 != 0)
    			{
    				//evaluate what is inside the parenthesis
    			
    				
    			}
    			
    			
    		}
    		return 0;
    } */
    
    private static void parseExpression (String input, ArrayList<Array> arrays, ArrayList<Variable> vars, Stack<Float> operandStack, Stack<String> operatorStack)
    {
    		String myDelims = " \t*+-/()[]";
    		StringTokenizer newTokParse = new StringTokenizer (input, myDelims, true);
    		System.out.println("parseExpression: input="+input);
    		int openBraceCount =0;
    		
    		
    		while (newTokParse.hasMoreTokens())
    		   {
    		    		String tok = newTokParse.nextToken();
    		    		{
    		    			System.out.println("PARSE TOKEN" + tok);
    		    			if (tok.equals("+") || tok.equals("-") || tok.equals("*") || tok.equals("/"))
    		    			{
    		    				operatorStack.push(tok);
    		    				System.out.println(" PUSH OPERATOR");
    		    			}
    		    			
    		    			else if (tok.equals(" "))
    		    			{
    		    				continue;
    		    			}
    		    			
    		    			else if (isNumber(tok))
    		    			{
    		    				Float floatTok = new Float (tok);
    		    				operandStack.push(floatTok);
    		    				
    		    				System.out.println("PUSH NUM");
    		    				
    		    			}
    		    			
    		    			else if (isValidVariable(tok,vars))
    		    			{
    		    			
    		    				
    		    				float newTok = lookUpVar(tok, vars);
    		    				Float floatTok = new Float (newTok);
    		    				System.out.println("tok val" + floatTok);
    		    				
    		    				operandStack.push(floatTok);
    		    				System.out.println("Operand Stack = " + operandStack.peek());
    		    				
    		    			}
    		    			else if ( isValidArray(tok, arrays))
    		    			{
    		    				System.out.println("(476) Token Array"+tok);
    		    				operatorStack.push(tok);
    		    				
    		    			}
    		    			else if (tok.equals("("))
    		    			{
    		    				operatorStack.push(tok);
    		    			}
    		    			
    		    			else if (tok.equals(")") && !operatorStack.peek().equals("("))
    		    			{
    		    				String parOperator = "";
    		    				if ( operatorStack.isEmpty())
    		    					continue;
    		    				do {
    		    				System.out.println("Operator Stack:");
    		    				//operatorStack.print();
    		    				System.out.println("Operand Stack:");
    		    				//operandStack.print();
    		    				
    		    				
    		    				performOperations(operatorStack, operandStack, true);
    		    				if ( operatorStack.peek().equals("(") )
    		    						parOperator =operatorStack.pop();
    		    				
 /*   		    				Float val1 = new Float (0);
    		    				Float val2 = new Float (0);
    		    				
    		    				parOperator =operatorStack.pop();
    		    				
    		    				if (parOperator.equals("("))
    		    				{
    		    					break;
    		    				}
    		    				val2 = operandStack.pop();
    		    				val1 = operandStack.pop();
    		    				
    		    				float result = operate(parOperator, val1, val2);
    		    				operandStack.push(result);
  */
    		    					
    		    				} while(!parOperator.equals("("));
    		    				
    		    				
    		    			}
    		    			
    		    			else if (tok.equals(")") && operatorStack.peek().equals("("))
    		    			{	String parOperator = "";
    		    				System.out.println("TOK = " + tok);
    		    				operatorStack.pop();
    		    				
    		    			}
    		    			
    		    			
    		    			else if (tok.equals("["))
    		    					
    		    			{
    		    				System.out.println("EVAL ARRAY = " + tok + "   (507)");
    		    				operatorStack.push(tok);
    		    				
    		    			}
    		    			
    		    			else if (tok.equals("]"))
    		    			{
    		    				String arrOperator = "";
    		    				
    		    			do
    		    				{
    		    				
    		    					//Float val1 = new Float (0);
    		    					//Float val2 = new Float (0);
    		    				
    		    					if ( operatorStack.peek().equals("[") )
    		    						arrOperator =operatorStack.pop();
    		    					if (arrOperator.equals("["))
    		    					{
    		    						break;
    		    						
    		    					}
    		    					performOperations(operatorStack, operandStack, true);
    		    					//val2 = operandStack.pop();
    		    					//val1 = operandStack.pop();
    		    					
    		    				
    		    					//float result = operate(arrOperator, val1, val2);
    		    					//operandStack.push(result);
   
    		    				} while (!arrOperator.equals("["));
    		    				
    		    			    if (arrOperator.equals("["))
    		    			    {
    		    			    		String arrayName = operatorStack.pop();
    		    			    		Float floatingVar = operandStack.pop();
    		    			    		System.out.println("floating pt = "  +  floatingVar);
    		    			    		int arrayIndex = (int) Math.floor(floatingVar);
    		    			    		System.out.println("Array Index floor = " + arrayIndex);
    		    			    		Float arrResult = lookUpArray(arrayName, arrayIndex, arrays);
    		    			    		
    		    			    		operandStack.push(arrResult);
    		    			    		
    		    			    		
    		    			    		
    		    			    }
    		    				
    		    			}
    		    		
    		   }
    		    		
    		    		
    		    		
    }
    		
    		
    }
    
    
    private static float performOperations(Stack<String> operatorStack, Stack <Float> operandStack, boolean oneTime)
    {
    		String operator = "";
    		String peekedOperator = "";
    		Float saveOperand = (float) 0;
    		String saveOperator = "";
    		float result = 0;
    		
    		Float secondVal = (float) 0;
    		Float firstVal = (float) 0;
    		
    		while (operatorStack.isEmpty() == false)
    		{
    			
    			 System.out.println("1 = " + operatorStack.peek());
    			 operator= operatorStack.pop();
    			 if ((operatorStack.isEmpty() == false))
    					 
    			 {
    				 peekedOperator = operatorStack.peek();
    				 if (((operator.equals("+") || operator.equals("-")) && (peekedOperator.equals("*") || peekedOperator.equals("/"))))
    				 {
    					 saveOperator = operator;
    					 operator = operatorStack.pop();
    					// System.out.println("2 = " + operatorStack.peek());
    					 saveOperand = operandStack.pop();
    					 secondVal = operandStack.pop();
    					 firstVal = operandStack.pop();
    					 result = operate(operator, firstVal, secondVal);
    					 operandStack.push(result);
    					 operandStack.push(saveOperand);
    					 System.out.println("(532) Result" + result);
    					 operatorStack.push(saveOperator);
    				 }
    				 else if (operator.equals("-") && peekedOperator.equals("-"))
    				 {
    					 secondVal = operandStack.pop();
     				 firstVal = operandStack.pop();	
     				 result = operate("+",firstVal, secondVal);
     				 operandStack.push(result);
    				 }
    				 else if (operator.equals("+") && peekedOperator.equals("-"))
    				 {
    					 secondVal = operandStack.pop();
     				 firstVal = operandStack.pop();	
     				 result = operate("-",firstVal, secondVal);
     				 operandStack.push(result);
    				 }
    				 
    				 //inserted below now, not sure if correct position
    				 
    				 else if (operator.equals("(") || operator.equals(")"))
    				 {
    					 operatorStack.pop();
    				 }
    				 
    				 /*else if ((operator.equals("(") || operator.equals(")") && ((operatorStack.peek() == "+") || 
    						 (operatorStack.peek() == "-") || (operatorStack.peek() == "*") || (operatorStack.peek() == "/"))))
    				{
    					 saveOperand = operandStack.pop();
    					 secondVal = operandStack.pop();
    					 firstVal = operandStack.pop();
    					 result = operate(operator, firstVal,secondVal);
    					 operandStack.push(result);
    					 		
    				}
    				 
    				else if ((operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/")) && 
    						(operatorStack.peek() == "("))
    				{
    					saveOperator = operator;
    					operatorStack.pop();
    				} */
    				 
    				
    				else 
    				{
    					secondVal = operandStack.pop();
    					firstVal = operandStack.pop();	
    					result = operate(operator,firstVal, secondVal);
    					operandStack.push(result);
    				}  			 
    			
    			 }
    			 else 
    			 {
    				secondVal = operandStack.pop();
 				firstVal = operandStack.pop();	
 				result = operate(operator,firstVal, secondVal);
 				operandStack.push(result);
    			 } 
    		
    			 if ( oneTime == true)
    				 return (0);
    		}
    		
    			 System.out.println("RESULT = " + result);
    			 return operandStack.pop();
    		
    		
    }
    
    
    
    
   /* private static boolean operateFirst (char one, char two)
    {
    		
    		if (char.equals('*' || '/'))
    		{
    			return true;
    		}
    		
    		if ((char == ('+' || '-')) && 
    		
    		
    		return false;
    } */
    
    
    
}
    
    
    
    
    

