package thread.clientSideLocking;

import java.util.ArrayList;
import java.util.List;

//http://rayfd.me/2007/11/11/when-a-synchronized-class-isnt-threadsafe/

//individual method calls of synchronized List and Vector are synchronized.
//But list.add() and list.remove() can be called in any order between the 2 threads

//The moral of the story is that just because a class is fully synchronized, doesn’t mean it’s threadsafe
//(UPDATE: as in doesn’t mean your code will be threadsafe from using it – thanks Alex)
public class MultiMethodSynchro {
	
	private List<Integer> list;
	
	public MultiMethodSynchro() throws InterruptedException {
		list = new ArrayList<>();		//It doesnt really matter which one we use with synchronized (list)
		//list = Collections.synchronizedList(new ArrayList<>());
		
		list.add(0);
		
		for(int i=0; i<3; i++) {
			new Thread(r).start();
		}
		
	}
	
	Runnable r = () -> {
		for(int i=0; i<1000; i++) {
			synchronized (list) {
				list.set(0, list.get(0)+1);
			}
			
		}
		System.out.println("Finished "+list.get(0));
	};
	
	public static void main(String[] args) throws InterruptedException {
		new MultiMethodSynchro();
	}

}
