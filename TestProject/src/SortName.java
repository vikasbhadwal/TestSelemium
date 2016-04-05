import java.util.Arrays;


public class SortName {
public static void main(String args[])
{
	String name="vikas";
	
	char []nameArray= name.toCharArray();
	Arrays.sort(nameArray);
	
	
	for(int i=0;i<nameArray.length;i++)
	{
	System.out.print(nameArray[i]);	
	}
	}
}
