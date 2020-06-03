package br.com.sample.code;

public class RunLengthEncode {

	public static void main(String[] args) {
		final String stringToEncode = "aaaabbccca";
		System.out.println(getEncondedString(stringToEncode, 0, stringToEncode.charAt(0), new StringBuilder()));
	}
	
	private static String getEncondedString(String stringToEncode, int totalOfSameCharacter, char lastCharacter, StringBuilder encodedString) {
		if (stringToEncode.isEmpty()) {
			encodedString.append(totalOfSameCharacter).append(lastCharacter);
			return encodedString.toString();
		}
		
		char currentCharacter = stringToEncode.charAt(0);
		if (currentCharacter == lastCharacter) {
			totalOfSameCharacter += 1;
		} else {
			encodedString.append(totalOfSameCharacter).append(lastCharacter);
			totalOfSameCharacter = 1;
		}

		return getEncondedString(stringToEncode.substring(1), totalOfSameCharacter, currentCharacter, encodedString);
	}

}
