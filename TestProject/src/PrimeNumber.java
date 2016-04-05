import java.util.Scanner;


public class PrimeNumber {

	public static void main(String[] args) {
		System.out.print("Enter Number to check");	
		Scanner scanner=new Scanner(System.in);
        boolean flag=false;
		int checkNumber=scanner.nextInt();
		for(int i=2;i<checkNumber/2;i++)
		{
			if(checkNumber%i==0)
			{
				flag=true;
				break;
				
			}
			
		}
		if(flag)
		{
			System.out.print("Enter Number is not prime");
			
		}
		else
		{
			System.out.print("Enter Number is  prime");
			
		}
	}

}
