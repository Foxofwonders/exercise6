package nl.ru.ai.exercise6;

import java.util.Stack;

public class Convert
{

  public static void main(String[] args)
  {
    /*
     * Test for tail recursion
     */
    xsy(3);
    System.out.println();
    /*
     * Test for left recursion
     */
    reverse("ykraps");
    System.out.println();
    /*
     * Test for middle recursion
     */
    downUp(5);
    System.out.println();
  }

  /** Prints a number of 'x's equal to the input integer, then prints a y.
   * @param i
   */
  private static void xsyOriginal(int i)
  {
	  assert i>0: "Integer must be greater than zero.";
	  
    if(i==0)
      System.out.print("y");
    else
    {
      System.out.print("x");
      xsyOriginal(i-1);
    }
  }
  
  /** Prints a number of 'x's equal to the input integer, then prints a y.
 * @param i
 */
private static void xsy(int i)
  {
	assert i>0: "Integer must be greater than zero.";
	
    while(i!=0)
    {
    	System.out.print("x");
    	i=i-1;
    }
    System.out.print("y");
  }
  
  /** Prints the reverse of the input string.
   * @param s
   */
  private static void reverseOriginal(String s)
  {
	  assert s!=null:"String must be declared.";
	  
    if(s.length()==0)
      return;
    else
    {
    	
    	reverseOriginal(s.substring(1));
      System.out.print(s.charAt(0));
    }
  }
  
  /** Prints the reverse of the input string.
 * @param s
 */
private static void reverse(String s)
  {
	assert s!=null:"String must be declared.";
	
	  Stack<String> stack = new Stack<String>();
	  while(s.length()!=0)
	  {
		  stack.push(s);
		  s=s.substring(1);
		  System.out.println(s);
	  }
	  while(!stack.isEmpty())
	  {
		  s=stack.pop();
		  System.out.print(s.charAt(0));
	  }
  }
  
  /** Prints an integer and all positive integers before it (no zero), prints an exclamation mark, and then prints all integers
   * from 1 up to and including the input integer (i). 
 * @param i
 */
  private static void downUpOriginal(int i)
  {
	  assert i>0: "Integer must be greater than zero.";
	  
    if(i==0)
      System.out.print("!");
    else
    {
      System.out.print(i);
      downUpOriginal(i-1);
      System.out.print(i);
    }
  }
  
  /** Prints an integer and all positive integers before it (no zero), prints an exclamation mark, and then prints all integers
   * from 1 up to and including the input integer (i). 
 * @param i
 */
private static void downUp(int i)
  {
	assert i>0: "Integer must be greater than zero.";
	
	  Stack<Integer> stack=new Stack<Integer>();
	  while(i!=0)
	  {
		  System.out.print(i);
		  stack.push(i);
		  i=i-1;
	  }
	  System.out.print("!");
	  while(!stack.isEmpty())
	  {
		  i=stack.pop();
		  System.out.print(i);
	  }
  }

}
