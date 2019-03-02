package br.com.reactive.programming.part01;

import io.reactivex.Observable;

public class ObservableRange {

	public static void main(String[] args) {
		Observable.range(0, 5)
			.subscribe(System.out::println);
	}

}
