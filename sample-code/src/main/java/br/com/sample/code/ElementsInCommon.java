package br.com.sample.code;

import java.util.HashMap;
import java.util.Map;

public class ElementsInCommon {

	public static void main(String[] args) {
		final int[] arr1 = new int[] { 1, 3, 5, 7, 13 };
		final int[] arr2 = new int[] { 2, 4, 6, 8, 9, 10, 11, 12, 13, 14 };
		
		int max = arr1.length;
		if (max < arr2.length) {
			max = arr2.length;
		}
		
		Map<Integer, Integer> totalValues = new HashMap<>();
		
		for (int index = 0; index < max; index++) {
			if (index < arr1.length) {
				int valFrom1 = arr1[index];
				
				if (totalValues.get(valFrom1) == null) {
					totalValues.put(valFrom1, 1);
				} else {
					totalValues.put(valFrom1, totalValues.get(valFrom1) + 1);
				}
			}
			
			if (index < arr2.length) {
				int valFrom2 = arr2[index];
				
				if (totalValues.get(valFrom2) == null) {
					totalValues.put(valFrom2, 1);
				} else {
					totalValues.put(valFrom2, totalValues.get(valFrom2) + 1);
				}
			}
		}
		
		Map<Integer, Integer> mapCommonValuesTotal = new HashMap<>();
		
		for (Map.Entry<Integer, Integer> commonsValues : totalValues.entrySet()) {
			if (commonsValues.getValue() > 1) {
				mapCommonValuesTotal.put(commonsValues.getKey(), commonsValues.getValue());
			}
		}

		System.out.println(mapCommonValuesTotal);
	}

}
