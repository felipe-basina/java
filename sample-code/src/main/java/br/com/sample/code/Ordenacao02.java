package br.com.sample.code;

import java.util.stream.IntStream;

public class Ordenacao02 {

	public static void main(String[] args) {
		int[] arr = new int[] { 5, 2, 9, 0, 7, 8, 11, 0, 11, 1 };
		
		for (int index = 0; index < arr.length; index++) {
			int minVal = arr[index];
			for (int innerIndex = arr.length - 1; innerIndex > index; innerIndex--) {
				int innerVal = arr[innerIndex];
				if (innerVal < minVal) {
					int temp = minVal;
					minVal = innerVal;
					arr[innerIndex] = temp;
					arr[index] = minVal;
				}
			}
		}
		IntStream.of(arr).forEach(value -> System.out.print(value + " "));
	}

}
