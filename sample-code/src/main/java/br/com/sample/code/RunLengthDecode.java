package br.com.sample.code;

public class RunLengthDecode {

	public static void main(String[] args) {
		String stringToDecode = "4a2b3c1a";
		System.out.println(getDecodedString(stringToDecode, new StringBuilder()));
//		stringToDecode = "4a2b3ca";
//		System.out.println(getDecodedString(stringToDecode, new StringBuilder()));
//		stringToDecode = "ab2dc";
//		System.out.println(getDecodedString(stringToDecode, new StringBuilder()));
	}
	
	private static String getDecodedString(String stringToDecode, StringBuilder stringDecoded) {
		if (stringToDecode.isEmpty()) {
			return stringDecoded.toString();
		}
		
		char total = stringToDecode.charAt(0);
		char character = stringToDecode.charAt(1);
		
		for (int index = 0; index < Integer.parseInt(String.valueOf(total)); index++) {
			stringDecoded.append(character);
		}
		
		return getDecodedString(stringToDecode.substring(2), stringDecoded);
	}

}
