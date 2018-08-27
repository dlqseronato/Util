package thread;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ThreadManager implements Observer {
	protected int NUM_THREADS = 0;
	protected int numThreadsFinished = 0;

	public ThreadManager() {
	}

	public synchronized void executar(List<?> list, ThreadActionAbstract<?> action, Integer qtdThreads) {
		try {
			String threadName = null;
			for (int i = 0; i < qtdThreads; i++) {
				int begin = i * (list.size() / qtdThreads);
				int end = (i + 1) * (list.size() / qtdThreads);
				threadName = "Thread[" + (begin + 1) + "-" + end + "]";
				if (i == (qtdThreads - 1))
					end = list.size();
				List<?> aux = list.subList(begin, end);

				ThreadIterator ti = new ThreadIterator(this, aux, action, threadName);
				new Thread(ti).start();
				NUM_THREADS++;
			}

			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		numThreadsFinished++;
		if (numThreadsFinished == NUM_THREADS) {
			this.notifyAll();
		}
	}

}
