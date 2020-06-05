package br.com.sample.code;

public class DesafioNroPartidas {

	public static void main(String[] args) {
		int[] a = { 0, 7, 5, 2 } ;
		int[] b = { 6, 1, 5 };
		int count = 0;
		for (int index = 0; index < b.length; index++) {
			int reference = b[index];
			for (int inner = 0; inner < a.length; inner++) {
				int compare = a[inner];
				if (compare <= reference) {
					++count;
				}
			}
			System.out.println(count);
			count = 0;
		}
	}

}
