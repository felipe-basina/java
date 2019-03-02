package br.com.reactive.programming.part01;

import io.reactivex.Observable;

public class ObservableError {

	public static void main(String[] args) {
		Observable.error(new RuntimeException("oops"))
	    .subscribe(
	        System.out::println, //onNext
	        Throwable::printStackTrace, //onError
	        () -> System.out.println("OnCompleted")); //onCompleted
	}

}
