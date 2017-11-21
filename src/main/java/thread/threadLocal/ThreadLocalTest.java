package thread.threadLocal;

import additionalClasses.User;

//Thread-local variables differ from their normal counterparts in that each thread that accesses one 
//( via its get() or set() ) has its own, independently initialized copy of the variable. 
public class ThreadLocalTest {
	
	private int i=0;

	public ThreadLocalTest() throws InterruptedException {
		
		final ThreadLocal<User> tl = new ThreadLocal<User>() {
		    protected User initialValue() {
		        return new User("Arek", "Garek");
		    }
		};
		
		Runnable r = () -> {
			User u = tl.get();
			u.setName("Arek "+i);
			System.out.println(u);
		};
		
		for(i=0; i<5; i++) {
			new Thread(r).start();
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("We've changed lacal users, ThreadLacoal has not changed: "+tl.get());
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new ThreadLocalTest();
	}

}
