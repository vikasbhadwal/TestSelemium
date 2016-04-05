import java.util.Scanner;
public class PalindromeNumber {
public static void main(String args[])

{
System.out.print("Enter Number to check");	
Scanner scanner=new Scanner(System.in);

int checkNumber=scanner.nextInt();
int number=checkNumber;
int reverse=0;

while(number!=0)
	
{
int remainder= number%10;
reverse=reverse*10 + remainder;
number=number/10;
}

System.out.print(reverse);	
if(reverse==checkNumber)
	
{
	System.out.print("Enter Number is Palindrome");	
}

}
}
