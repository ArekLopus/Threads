package thread.deadlockorisitlivelock;

public class Bin {

	private String contents; // holds 1 part
    private boolean available = false; // is part in bin?

    // Factory puts parts in bin
    public synchronized void putContents(String part) {
        while (available == true) {  // if old part is in bin
            try {
                wait();       // wait for Worker to take it
            } catch (InterruptedException e) { 
                e.printStackTrace();
            }
        }
        contents = part;  // put new part in bin
        available = true; // flag that new part is available
        //notifyAll();
    }

    // Worker takes parts out of bin
    public synchronized String getContents() {
        while (available == false) { // if no new parts in bin
            try {
                wait();            // wait for Factory to put it
            } catch (InterruptedException e) { 
                e.printStackTrace();
            } 
        }
        available = false;  // flag that part is unavailabile
        //notifyAll();
        return contents;    // return the part to Worker
    }
}
