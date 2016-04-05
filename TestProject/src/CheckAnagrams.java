
public class CheckAnagrams {

	public static void main(String[] args) {
		
		String firstString="maryt".toLowerCase();
		String secondString="axrmaty".toLowerCase();
		int count=0;
		int i;
		boolean flag=false;
	
		for( i=0;i<firstString.length()-1;i++)
		{  
			if(flag)
		     break;
		    count=0;
			for(int j=0;j<secondString.length();j++)
			{
				if(!firstString.contains(Character.toString(secondString.charAt(i))))
				{	
					flag=true;
				    break;
				
				}
				
				
				if(firstString.charAt(i)== secondString.charAt(j))
			       {
				    count++;
				    if(count>1)
				    {
				    	flag=true;
					    break;
				    }
				    
			       }
			}
				
				
		}
		if(flag)
		{
		System.out.println("noooooooooooooooooooo");
		}
		else
		{
			System.out.println("yesssssssssss");
		}
			
		}
		
		
		
		
	}


