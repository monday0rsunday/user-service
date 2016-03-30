package com.broship.user.security;

import java.util.Random;

public class RandomGenerator {

	public static String generate(int length) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer s = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = alphabet.charAt(random.nextInt(alphabet.length()));
			s.append(c);
		}
		return s.toString();
	}

}
