package threads.utils.synchronizers;

import java.util.concurrent.Exchanger;

class ExchangerTest2 {
	
	public static void main(String args[]) {
		Exchanger<String> exgr = new Exchanger<String>();

		new UseString(exgr);
		new MakeString(exgr);
	}
}

class MakeString implements Runnable {
	Exchanger<String> ex;

	String str;

	MakeString(Exchanger<String> ex) {
		this.ex = ex;
		str = new String();
		new Thread(this).start();
	}

	public void run() {
		char ch = 'A';
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++)
				str += (char) ch++;
			try {
				str = ex.exchange(str);
				System.out.println("Produce String: "+str);
				Thread.sleep(200);
			} catch (InterruptedException exc) {
				exc.printStackTrace();
			}
		}
	}
}

class UseString implements Runnable {
	Exchanger<String> ex;

	String str;

	UseString(Exchanger<String> ex) {
		this.ex = ex;
		new Thread(this).start();
	}

	public void run() {

		for (int i = 0; i < 3; i++) {
			try {
				str = ex.exchange(new String());			//Empty string
				System.out.println("Consume String: "+str);
			} catch (InterruptedException exc) {
				exc.printStackTrace();
			}
		}
	}
}