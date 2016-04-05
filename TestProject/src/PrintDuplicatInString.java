import java.util.Arrays;


public class PrintDuplicatInString {

	public static void main(String[] args) {
		String testString="wabhhadwdfgdfgdfal";
		String tempString="";
		int count=0;
		int i;
		String tempp="";
		for( i=0;i<testString.length()-1;i++)
		{
			count=0;
			for(int j=0;j<testString.length();j++)
			{
				if(testString.charAt(i)== testString.charAt(j))
			       {
				    count++;
				    tempString=tempString+testString.charAt(i);
			       }
			
		    }
		
			if(count>1)
			{
				
				if(!tempp.contains(Character.toString(testString.charAt(i))))
				System.out.println(testString.charAt(i)+ " :" +(count));
				 tempp=tempp+testString.charAt(i);
					
			}
		}
		
		
		
		
	}

}
