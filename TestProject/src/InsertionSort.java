
public class InsertionSort {

	public static void main(String[] args) {
		 int a[]={8,5,1,7,9};
		 
	for(int i=0;i<a.length;i++)
	
	{
		int value=a[i];
		int index=i+1;
		while(index>0 && value>a[index])
			
		{
			a[index-1]=a[index];
			index--;
			
		}
		
	}
		 

	
	for(int i=0;i<a.length;i++)
		
	{
		System.out.print(a[i]);
		
	}
	}
}
