package br.com.reactive.programming.part02;

import br.com.reactive.programming.Invoker;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Observables {

	private Observable<String> observable = Observable.just("ein", "zwei", "drei");
	
	public static void main(String[] args) {
		new Invoker(Observables.class).callMethods();
	}
	
	public void printSimple() {
		System.out.println("Observables.printSimple()");
		observable.subscribe(System.out::println);
	}
	
	public void printOnCompletedMessage() {
		System.out.println("Observables.printOnCompletedMessage()");
		observable.subscribe(System.out::println, // onNext 
				Throwable::printStackTrace, // onError
				() -> System.out.println("Hey, I completed")); // onCompleted
	}

	public void printFromOverrideOperations() {
		System.out.println("Observables.printFromOverrideOperations()");
		observable.subscribe(new Observer<String>() {

			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("Subscribed...");
			}

			@Override
			public void onNext(String t) {
				System.out.println(t);
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("Completed, again");
			}
			
		});
	}
	
}
