package br.com.sample.code;

public class JumpingOnTheClouds {

	//private static int[] clouds = new int[] { 0, 0, 1, 0, 0, 1, 0 };
	private static int[] clouds = new int[] { 0, 0, 0, 0, 1, 0 };
	
	public static void main(String[] args) {
		int totalJumps = 0;
		for (int index = 0; index < clouds.length - 1; index++) {
			int current = clouds[index];
			int next = index + 1 < clouds.length ? clouds[index + 1] : 0;
			int nextNext = index + 2 < clouds.length ? clouds[index + 2] : 0;
			
			if (current == next || current == nextNext) {
				totalJumps += 1;
				if (current == nextNext) {
					index++;
				}
			}
		}
		System.out.println(totalJumps);
	}

}
