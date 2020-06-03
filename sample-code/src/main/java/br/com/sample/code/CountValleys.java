package br.com.sample.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CountValleys {

	public static void main(String[] args) throws IOException {
		String steps = readFromInputStream();
		//String steps = "UDDDUDUU";
		
		int totalSteps = 0;
		int totalValleys = 0;
		boolean isGoingDown = true;
		
		for (int index = 0; index < steps.length(); index++) {
			char step = steps.charAt(index);
			if (totalSteps == 0) {
				isGoingDown = true;
				if (step == 'U') {
					isGoingDown = false;
				}
			}

			if (step == 'U') {
				totalSteps += 1;
			} else if (step == 'D') {
				totalSteps -= 1;
			}

			if (totalSteps == 0 && isGoingDown) {
				totalValleys += 1;
			}
		}

		System.out.println(totalValleys);
	}
	
	private static String readFromInputStream() throws IOException {
		InputStream is = CountValleys.class.getClassLoader().getResourceAsStream("input17.txt");
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

}
