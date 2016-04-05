import java.util.Arrays;
import java.util.stream.Collector.Characteristics;


public class ReverseName {

	public static void main(String[] args) {
		String name="Vikas Bhadwal";
		int f=0;
			for(int i=0;i<name.length()-1;i++)
				
			{ 
				if(Character.isWhitespace(name.charAt(i)))
				{
					 f=i;
					for(int j=i+1;j<name.length();j++,i++)
					{
						
						System.out.print(name.charAt(j));
					}
				
					System.out.print(" ");
				}
					
				
				
			}
			
    for(int k=0;k<f;k++)
				
			{ 
			
	System.out.print(name.charAt(k));

	}

}
}