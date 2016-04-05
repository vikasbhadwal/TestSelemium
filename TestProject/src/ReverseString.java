
public class ReverseString {

public static void main(String args[])
{
	
String one="Vikas Bhadwal";
String two="fghfgh";
two.toCharArray();
for(int i=one.length()-1;i>=0;i--)
{
two+=one.charAt(i);	
}

System.out.print(two);
}
}

