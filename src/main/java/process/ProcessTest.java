package process;

import java.io.IOException;

public class ProcessTest {

	public ProcessTest() throws IOException, InterruptedException  {
		
		Runtime objRuntime = Runtime.getRuntime();
		Process pr = objRuntime.exec("notepad.exe alice.txt");
		
		Thread.sleep(2000);
		
		System.out.println("IsAlive: "+pr.isAlive());
		
		pr.destroy();
		
		Thread.sleep(500);
		System.out.println("IsAlive: "+pr.isAlive());
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new ProcessTest();
	}

}
