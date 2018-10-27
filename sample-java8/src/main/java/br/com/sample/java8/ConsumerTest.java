package br.com.sample.java8;

import java.util.function.Consumer;

public class ConsumerTest {

	public static void main(String[] args) {
		Consumer<String> c1 = arg -> {
			System.out.println(arg + "ok!");
		};
		c1.accept("TestConsumerAccept - ");
		
		Consumer<String> c2 = arg2 -> {
			System.out.println(arg2 + "ok!!");
		};
		
		Consumer<String> c3 = arg3 -> {
			System.out.println(arg3 + "ok!!!");
		};
		
		c1.andThen(c2).andThen(c3).accept("TestConsumerAfterThen - ");
	}

}
