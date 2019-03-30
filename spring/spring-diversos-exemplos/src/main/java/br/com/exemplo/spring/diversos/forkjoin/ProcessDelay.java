package br.com.exemplo.spring.diversos.forkjoin;

public class ProcessDelay {

	public static void doDelay(long timeInMilliseconds) {
		try {
			Thread.sleep(timeInMilliseconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
