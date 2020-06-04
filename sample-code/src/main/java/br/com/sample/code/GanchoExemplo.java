package br.com.sample.code;

public class GanchoExemplo {

	public static void main(String[] args) {
		Pai pai = new Filha(3);
		System.out.println(pai.doubleNumber());
	}

}

abstract class Pai {
	
	protected abstract int number();
	
	protected int doubleNumber() {
		return 2 * this.number();
	}
	
}

class Filha extends Pai {
	
	private int number;
	
	public Filha(int number) {
		this.number = number;
	}
	
	@Override
	protected int number() {
		return this.number;
	}
	
}
