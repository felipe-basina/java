package br.com.sample.code.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class RunPrintService {

	private static PrintService printService = new PrintService();
	
	public static void main(String[] args) {
		int availableProcessors = Runtime.getRuntime().availableProcessors(); 
		ForkJoinPool forkJoinPool = new ForkJoinPool(availableProcessors);
		forkJoinPool.submit(() -> getListNames().parallelStream()
				.forEach(name -> {
			printService.print(name);
		}));
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		forkJoinPool.shutdown();
	}
	
	private static List<String> getListNames() {
		List<String> names = new ArrayList<>();
		IntStream.range(0, 10001).forEach(index -> names.add("Name-" + index));
		return names;
	}

}
