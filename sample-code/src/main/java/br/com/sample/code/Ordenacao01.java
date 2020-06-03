package br.com.sample.code;

import java.util.stream.IntStream;

public class Ordenacao01 {

	public static void main(String[] args) {
		//int[] arr = new int[] { 3, 2, 7, 1, 5, 4 };
		int[] arr = new int[] { 5, 2, 9, 0, 7, 8, 11, 0, 11, 1 };

		for (int index = 0; index < arr.length; index++) {
			int minVal = arr[index];
			for (int innerIndex = index + 1; innerIndex < arr.length; innerIndex++) {
				int innerVal = arr[innerIndex];
				if (innerVal < minVal) {
					int temp = minVal;
					minVal = innerVal;
					arr[index] = minVal;
					arr[innerIndex] = temp;
				}
			}
		}
		IntStream.of(arr).forEach(value -> System.out.print(value + " "));
	}

}
