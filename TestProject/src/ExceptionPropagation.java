import java.io.IOError;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class ExceptionPropagation {

	public static void main(String[] args) {
		ExceptionPropagation obj=new ExceptionPropagation();
		
		
		try{
			obj.thridMethod();
		}
		
		catch(Exception e)
		{
			
			System.out.println("Handled in main method");
		}
		System.out.print("after thrid method");
		

	}

	void firstMethod() 
	{
		
		
		int a=50/0;
		
		
	}
	
	void secondMethod() 
	{
	
		firstMethod();
		
		
	}


  void thridMethod() 
  {
	  secondMethod();
	  
  }


}

  /*class MyExeception extends Exception
  {
	  
	 MyExeception() 
	{
		System.out.print("Execption for my side");
		
	}
	  
	
	 static void  throwExecption() throws MyExeception
	{
	throw new MyExeception();
	}
	
  }
  */
