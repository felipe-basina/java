package br.com.reactive.programming.part01;

import io.reactivex.Observable;

public class ObservableCreate {

    //emitter é um argumento do tipo ObservableEmitter
    //essa função é executada quando um Subscriber se vincula ao Observable
    //emite valores para os Subscribers vinculados a esse Observable
	public static void main(String[] args) {
		Observable.create(emitter -> {
		    emitter.onNext("first");
		    emitter.onNext("second");
		    emitter.onNext("third");
		    //finaliza o Observable
		    emitter.onComplete();
		})
		.subscribe(System.out::println);
	}
	
}
