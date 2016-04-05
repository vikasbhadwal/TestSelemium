import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class FindDuplicarionInString {

	public static void main(String args[])
	{
		
	String example="my is Name is is Vikas Bhadwal my";
	String test[]=example.split(" ");
	
    List<String> ls=new ArrayList<String>(Arrays.asList(test));
	ArrayList<String> temp=new ArrayList<String>();
	
	for(int i=0;i<ls.size();i++)
	{
		for(int j=i+1;j<ls.size();j++)
		{
			if(test[i].contains(test[j]))
			{
				temp.add(test[j]);
				ls.remove(j);
		    }
				
			
		}
		
	}
	
	
	System.out.print(ls);
	System.out.print(temp);
	
	String[] names= new String[ls.size()];
	ls.toArray(names);
	
	
	for(int i=0;i<names.length;i++)
	{
	System.out.print(names[i]+ " ");
	}
	}
	}

