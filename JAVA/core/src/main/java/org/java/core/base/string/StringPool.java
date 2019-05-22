package org.java.core.base.string;

/**
 * Java String Pool JdbcQuickStartExample
 *
 */
public class StringPool {
	public static void main(String[] args) {
		String s1 = "Cat";
		String s2 = "Cat";
		String s3 = new String("Cat");
		
		System.out.println("s1 == s2 :" + (s1 == s2));
		System.out.println("s1 == s3 :" + (s1 == s3));
	}
}
