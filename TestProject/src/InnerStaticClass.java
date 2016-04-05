



class OuterClass
{
  String message="Hello";	
  static String onlyForStatic="Only for Static Inner Classs";
  
  public static void  show()
  {
	  System.out.print("Methhod in Outer class"); 
	  
  }
  
   public static class StaticInner {
	   
	   public void print()
	   {
		 System.out.print("Meaasge from static class" + onlyForStatic);  
		 show();
	   }
   }
	class Inner{
		
		public void printInner()
		   {
			 System.out.print("Meaasge from static class" + onlyForStatic);  
			   
		   }
		
	}   
	   
	  
	  
	  
	  
	  
  }




public class InnerStaticClass {

	public static void main(String arg[])
	{
		OuterClass.StaticInner staticobj=new  OuterClass.StaticInner();
		staticobj.print();
		
		OuterClass outerObj=new OuterClass();
		OuterClass.Inner innerObj=outerObj.new Inner();
		
		innerObj.printInner();
	}
	
	
	
}
