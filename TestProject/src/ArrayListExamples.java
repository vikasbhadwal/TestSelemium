import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;


public class ArrayListExamples {

	public static void main(String[] args) {
		
		ArrayList<String> testList=new ArrayList<String>();
		
		testList.add("Vikas");
		testList.add("Ntin");
		testList.add("Vikas");
		testList.add("Pankaj");
		
		HashSet<String> hashset=new HashSet<String>(testList);
		testList.clear();
		testList.addAll(hashset);
		
		System.out.print(hashset);
		
	/*	
		
		
		ArrayList<String> al=new ArrayList<String>();
		al.add("Vikas");
		al.add("Nitin");
		al.add("Pankaj");
		al.add("Vikas");
		System.out.println(al);
	  //System.out.println(al.size());
		String[] names=new String[al.size()];
		al.toArray(names);
		Arrays.asList(names);
	  //System.out.println(al);
	  //Iterator<String> ltr=al.iterator();
		ListIterator<String> litr=al.listIterator();
		
		while(litr.hasNext())
		{
			//System.out.print(litr.next());
			if(litr.next().equals("Pankaj"))
			{
				litr.remove();
			}
			
			if(litr.next().equals("Vikas"))
			{
				litr.set("Vikas Bhadwal");
			}
		}
		System.out.println(al);
	}

	ArrayList<String> testList=new ArrayList<String>();
	
	
	*/
	
	
}
}
