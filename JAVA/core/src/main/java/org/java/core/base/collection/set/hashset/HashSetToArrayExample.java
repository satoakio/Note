package org.java.core.base.collection.set.hashset;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ��ʱ���Ǳ��뽫HashSetת��Ϊ���飬��֮��Ȼ.(vice-versa)
 *
 */
public class HashSetToArrayExample {

	public static void main(String[] args) {
		Set<Integer> ints = new HashSet<>();
		for(int i=0; i<10; i++){
			ints.add(i);
		}
		System.out.println("ints set = "+ints);
		
		// set to array JdbcQuickStartExample
		Integer[] intArray = new Integer[ints.size()];
		intArray = ints.toArray(intArray);
		System.out.println("intArray = "+Arrays.toString(intArray));
		ints.remove(0);ints.remove(1);
		System.out.println("intArray = "+Arrays.toString(intArray));
		
		
		//array to set JdbcQuickStartExample
		ints = new HashSet<>(Arrays.asList(intArray));
		System.out.println("ints from array = "+ints);
		ints.remove(0);ints.remove(1);
		System.out.println("ints from array after remove = "+ints);
		System.out.println("intArray = "+Arrays.toString(intArray));


	}

}
