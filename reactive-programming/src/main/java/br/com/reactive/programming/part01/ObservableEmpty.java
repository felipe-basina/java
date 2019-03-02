package br.com.reactive.programming.part01;

import io.reactivex.Observable;

public class ObservableEmpty {

	public static void main(String[] args) {
		// cria um Observable vazio, que emite apenas onCompleted
		Observable.empty()
		    .subscribe(
		        System.out::println, //onNext
		        Throwable::printStackTrace, //onError
		        () -> System.out.println("OnCompleted")); //onCompleted
	}

}
