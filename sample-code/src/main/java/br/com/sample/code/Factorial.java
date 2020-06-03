package br.com.sample.code;

public class Factorial {

	public static void main(String[] args) {
		final long number = 4l;
		System.out.println(factorialOf(number, 1));
		System.out.println(factorialOf(number));
	}
	
	private static long factorialOf(long number) {
		if (number <= 0) {
			return 1;
		}
		return number * factorialOf(number - 1);
	}
	
	private static long factorialOf(long number, long factorial) {
		if (number <= 0) {
			return factorial;
		}
		long nextNumber = number - 1;
		return factorialOf(nextNumber, factorial * number);
	}

}
