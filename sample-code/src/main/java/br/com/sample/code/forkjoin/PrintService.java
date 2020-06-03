package br.com.sample.code.forkjoin;

public class PrintService {

	public void print(final String value) {
		System.out.println(Thread.currentThread().getName() + ": " + value + ". Total de processadores: "
				+ Runtime.getRuntime().availableProcessors());
	}

}
