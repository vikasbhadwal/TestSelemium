import java.util.Scanner;


public class ArmstrongNumber {
	public static void main(String args[])

	{
	System.out.print("Enter Number to check");	
	Scanner scanner=new Scanner(System.in);

	int checkNumber=scanner.nextInt();
	int number=checkNumber;
	int temp=0;
	 int remainder=0;
	 while(number>0)
	 {
		 remainder=number%10;
		 temp+=remainder*remainder*remainder;
		 number=number/10;
		 
		 
		 
		 
		 
	 }
	 System.out.print(temp);	
	if(temp==checkNumber)
		
	{
		System.out.print("Enter Number is armstrong number");	
		
	}

	else
	{
		System.out.print("Enter Number is not armstrong Number");	
		
	}
	
	}
	
	
	
}