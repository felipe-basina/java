package br.com.reactive.programming.part01;

import io.reactivex.Observable;

public class ObservableJust {

	public static void main(String[] args) {
		ObservableJust observableJust = new ObservableJust();
		observableJust.simple();
		observableJust.printAsDataSource();
	}
	
	private void simple() {
		// cria um Observable a partir de valores arbitrários
		Observable.just("first", "second", "third")
		    .subscribe(System.out::println);
	}
	
	//o Observable é criado com este valor
	// não é possĩvel "adiar" o processamento
	private void printAsDataSource() {
		Observable<Long> observable = Observable.just(System.currentTimeMillis());
		observable.subscribe(System.out::println);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		observable.subscribe(System.out::println);
	}

}
