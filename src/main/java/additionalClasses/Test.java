package additionalClasses;

public class Test {

	public Test() throws InterruptedException {
		
		Runnable r = () -> {
			
		};
		
		new Thread(r).start();
		
		System.out.println();
		
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new Test();
	}

}
