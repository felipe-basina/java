package br.com.sample.code;

import java.util.Arrays;

public class Teste {

	public static void main(String[] args) {
		for (int number : Arrays.asList(9, 529, 20, 15, 32, 483647, 2147483647, 6291457, 1610612737, 561892)) {
			String binary = convertToBinary(number);
			findMaxGap(number, binary);			
		}
	}
	
	private static String convertToBinary(int number) {
		return Long.toBinaryString(number);
	}
	
	private static void findMaxGap(long number, String binaryAsString) {
		int max = 0;
		
		StringBuilder sb = new StringBuilder();
		
		for (int index = 0; index < binaryAsString.length(); index++) {
			char character = binaryAsString.charAt(index);
			if (character == '0') {
				sb.append(character);
				continue;
			}
			
			if (sb.toString().length() > 0) {
				if (sb.toString().length() > max) {
					max = sb.toString().length();
				}
				sb = new StringBuilder();
			}
		}
		
		System.out.printf("%d : %s : %d\n", number, binaryAsString, max);
	}

}
