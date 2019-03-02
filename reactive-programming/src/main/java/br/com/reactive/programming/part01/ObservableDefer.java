package br.com.reactive.programming.part01;

import io.reactivex.Observable;

public class ObservableDefer {

	public static void main(String[] args) {
		ObservableDefer observableDefer = new ObservableDefer();
		observableDefer.simple();
		observableDefer.printAsDataSource();
	}
	
	private void simple() {
		// semelhante ao Observable.create, mas permite declarar dinamicamente como o Observable deve ser criado a cada subscrição
		// o argumento é um java.util.concurrent.Callable<? extends ObservableSource<? extends T>>.
		Observable<String> observable = Observable.defer(() -> (e) -> e.onNext("hello"));
		observable.subscribe(System.out::println);
		observable.subscribe(System.out::println);		
	}
	
	// o defer, pode adiar o código que é executado no onSubscribe
	private void printAsDataSource() {
		Observable<Long> observable = Observable.defer(() -> Observable.just(System.currentTimeMillis()));
		observable.subscribe(System.out::println);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		observable.subscribe(System.out::println);
	}

}
