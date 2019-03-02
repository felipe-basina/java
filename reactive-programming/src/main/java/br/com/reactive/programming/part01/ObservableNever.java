package br.com.reactive.programming.part01;

import io.reactivex.Observable;

public class ObservableNever {

	public static void main(String[] args) {
		// cria um Observable que não emite nenhum evento
		Observable.never()
		    .subscribe(
		        System.out::println, //onNext
		        Throwable::printStackTrace, //onError
		        () -> System.out.println("OnCompleted")); //onCompleted
	}

}
