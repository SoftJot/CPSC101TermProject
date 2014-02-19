
package clientservercolorconvert;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientServerColorConvert {

    public static StringBuffer queue = new StringBuffer();
    public static final int threadsInPool = 5;
    
     public int doFromQueue() throws InterruptedException {
        synchronized (queue) {
            while (queue.length()>5) {
                queue.wait();
            }    
            //convert image
            queue.notify();
        }
        return 1;
     }    

     public static void main(String args[]) {
        
        final ClientServerColorConvert convertServer = new ClientServerColorConvert();

        List<Thread> threadPool = new LinkedList<>();

        for (int i = 0; i < threadsInPool; i++) {
             Thread workerThread = new Thread() {
                @Override
                public void run() {
					try {
						while (convertServer.doFromQueue() > 1) {
							try { Thread.sleep(100); } catch (InterruptedException e) { }
						}
					} catch (InterruptedException ex) {
						Logger.getLogger(ClientServerColorConvert.class.getName()).log(Level.SEVERE, null, ex);
					}
                }
            };
            threadPool.add( workerThread );
            workerThread.start();
        }

    }
}

class Client {
	
	private static final int custThreads = 5;

	private int request() throws InterruptedException {
        synchronized (ClientServerColorConvert.queue) {
            while (ClientServerColorConvert.queue.length()>ClientServerColorConvert.threadsInPool){
                ClientServerColorConvert.queue.wait();
            } 
            ClientServerColorConvert.queue.append(1);
            System.out.println(ClientServerColorConvert.queue);
            ClientServerColorConvert.queue.notify();
	    }
		return 0;
	}
		
	public static void Client() {
			
		final Client client = new Client();

		List<Thread> clientList = new LinkedList<>();
		
		for (int i = 0; i < custThreads; i++) {
			Thread clientThread = new Thread() {
			@Override
			public void run() {
				try {
					while (client.request() > 0) {
						try { Thread.sleep(100); } catch (InterruptedException e) { }
					}
				} catch (InterruptedException ex) { }
			}
		};
		
		clientList.add( clientThread );
        clientThread.start();
		
        }
	}

}