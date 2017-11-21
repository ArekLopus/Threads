package threads.utils.synchronizers;

import java.util.concurrent.Phaser;

public class PhaserTest {

	public static void main(String[] args) {
			   
		Phaser phaser = new Phaser();

		Thread t1 = new MyThread(phaser,300);
		Thread t2 = new MyThread(phaser,400);
		Thread t3 = new MyThread(phaser,900);
		t1.start();
		t2.start();
		t3.start();
		
	}
}
			 
class MyThread extends Thread {
			 
	Phaser phaser;
	int sleep;
			 
	MyThread(Phaser phaser, int sleep){
		this.phaser=phaser;
		this.sleep=sleep;
	}
			 
	public void run() {
		phaser.register();
		System.out.println(this.getName() + " registered(), sleeping: "+sleep);
		try {
			Thread.sleep(sleep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		phaser.arriveAndAwaitAdvance();
		System.out.println(this.getName() + " arriveAndAwaitAdvance(), sleeping: "+sleep);
		try {
			Thread.sleep(sleep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.getName() + " end");
	}
}