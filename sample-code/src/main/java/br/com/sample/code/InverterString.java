package br.com.sample.code;

public class InverterString {

	public static void main(String[] args) {
		final String string = "FELIPE";
		System.out.println(reverseIterative(string));
		System.out.println(reverseRecur("NASCIMENTO", ""));
		System.out.println(reverseRecur2("NASCIMENTO", ""));
	}
	
	public static String reverseIterative(String originalString) {
		StringBuilder reverseString = new StringBuilder();
		for (int index = originalString.length() - 1; index >= 0; index--) {
			reverseString.append(originalString.charAt(index));
		}
		return reverseString.toString();
	}
	
	public static String reverseRecur(String originalString, String reverseString) {
		if (originalString == null || originalString.isEmpty()) {
			return reverseString;
		}
		reverseString = originalString.substring(0, 1).concat(reverseString);
		return reverseRecur(originalString.substring(1, originalString.length()), reverseString);
	}
	
	public static String reverseRecur2(String string, String reverse) {
		if (string.length() == reverse.length()) {
			return reverse;
		}
		return reverseRecur2(string, 
				string.substring(reverse.length(), reverse.length() + 1).concat(reverse));
	}

}
