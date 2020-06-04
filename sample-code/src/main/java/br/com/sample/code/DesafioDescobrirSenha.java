package br.com.sample.code;

import java.util.HashMap;
import java.util.Map;

public class DesafioDescobrirSenha {

	public static void main(String[] args) {
		System.out.println(processInput("3 onetwoone"));
		System.out.println(processInput("10 weatgeekhunterhelpyoufindingtopgeekhunterjobs"));
		System.out.println(processInput("3 believeyoucanandyouarehalfwaythere"));
	}

	public static String processInput(String inputLine) {
		String[] parcial = inputLine.split(" ");
		int tamanho = Integer.parseInt(parcial[0]);
		String palavra = parcial[1];

		Map<String, Integer> palavras = new HashMap<String, Integer>();

		for (int index = 0; index < palavra.length(); index++) {
			try {
				String senha = palavra.substring(index, index + tamanho);
				if (palavras.containsKey(senha)) {
					palavras.put(senha, palavras.get(senha) + 1);
				} else {
					palavras.put(senha, 1);
				}
			} catch (Exception ex) {
				break;
			}
		}

		int max = 0;
		String senha = "";
		for (Map.Entry<String, Integer> pMap : palavras.entrySet()) {
			if (pMap.getValue() > max) {
				max = pMap.getValue();
				senha = pMap.getKey();
			}
		}
		
		inputLine = senha;

		return inputLine;
	}

}
