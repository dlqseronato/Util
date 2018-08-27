package thread;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import util.Log;

public class ThreadIterator extends Observable implements Runnable {
	private List<?> list = null;
	private String threadName = null;
	@SuppressWarnings("rawtypes")
	private ThreadActionAbstract threadAction = null;
	
	public ThreadIterator(Observer o, List<?> l, ThreadActionAbstract<?> threadAction, String threadName) {
		this.list = l;
		this.threadName = threadName;
		this.threadAction = threadAction;
		this.addObserver(o);
	}
	
	@Override
	public void run() {
		Log.info(this.threadName+" - Início da execução da Thread.");
		if(this.list != null) {
			Iterator<?> it = this.list.iterator();
			while(it.hasNext()) {
				action(it.next());			
			}
		}	
		Log.info(this.threadName+" - Fim da execução da Thread.");
		this.setChanged();
		this.notifyObservers(threadAction.getClass());
	}
	
	@SuppressWarnings("unchecked")
	private void action(Object ob) {
		if (threadAction != null) threadAction.exec(ob);
	}

}
