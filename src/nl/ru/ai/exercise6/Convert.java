package nl.ru.ai.exercise6;

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

  private static void xsyOriginal(int i)
  {
    if(i==0)
      System.out.print("y");
    else
    {
      System.out.print("x");
      xsy(i-1);
    }
  }
  
  private static void xsy(int i)
  {
    if(i==0)
      System.out.print("y");
    else
    {
      System.out.print("x");
      xsy(i-1);
    }
  }
  
  private static void reverseOriginal(String s)
  {
    if(s.length()==0)
      return;
    else
    {
      reverse(s.substring(1));
      System.out.print(s.charAt(0));
    }
  }
  
  private static void reverse(String s)
  {
    if(s.length()==0)
      return;
    else
    {
      reverse(s.substring(1));
      System.out.print(s.charAt(0));
    }
  }
  
  private static void downUpOriginal(int i)
  {
    if(i==0)
      System.out.print("!");
    else
    {
      System.out.print(i);
      downUp(i-1);
      System.out.print(i);
    }
  }
  
  private static void downUp(int i)
  {
    if(i==0)
      System.out.print("!");
    else
    {
      System.out.print(i);
      downUp(i-1);
      System.out.print(i);
    }
  }

}