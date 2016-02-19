package com.broduce.user.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CodeGenerator {

	private int base = 36;

	private char[] fbases = { 'l', 'm', 'h', 'z', 'd', 'k', '2', '8', '3', '7',
			'p', '0', 'c', '9', '6', 't', 'f', '5', 'j', 'x', 'v', 'a', 'r',
			'y', 'n', 'b', 'u', 's', 'g', 'w', 'o', 'i', 'q', '4', '1', 'e' };
	private Map<Character, Integer> rfbases = new HashMap<Character, Integer>();

	private char[] sbases = { '4', 'w', 'n', 'r', 'g', 'p', 't', 'k', 'f', 'i',
			'b', '6', 'd', 'j', 'v', 'l', '5', 'o', '7', 's', 'a', 'z', 'q',
			'u', 'y', '2', 'm', 'c', '9', 'x', '1', '3', '0', 'e', 'h', '8' };
	private HashMap<Character, Integer> rsbases = new HashMap<Character, Integer>();

	private char[] tbases = { 'i', 'j', '5', 'h', '9', 'b', 'n', 'r', 's', 'd',
			'v', '4', '3', '1', 'l', 'k', 'g', 'e', 't', 'q', '0', '8', 'z',
			'6', 'y', 'p', 'w', 'o', '2', '7', 'm', 'f', 'x', 'a', 'u', 'c' };
	private Map<Character, Integer> rtbases = new HashMap<Character, Integer>();

	public CodeGenerator() {
		for (int i = 0; i < fbases.length; i++) {
			rfbases.put(fbases[i], i);
		}
		for (int i = 0; i < sbases.length; i++) {
			rsbases.put(sbases[i], i);
		}
		for (int i = 0; i < tbases.length; i++) {
			rtbases.put(tbases[i], i);
		}
	}

	public String encode(long id) {
		StringBuilder sb = new StringBuilder();
		long du = id;
		// first 2 chars
		while (du != 0) {
			long nguyen = du % base;
			du = du / base;
			char c = '-';
			if (sb.length() < 2)
				c = fbases[(int) nguyen];
			else if (sb.length() < 4)
				c = sbases[(int) nguyen];
			else if (sb.length() < 6)
				c = tbases[(int) nguyen];
			sb.append(c);
			// System.out.println(nguyen + "\t" + du + "\t" + c);
		}
		while (sb.length() < 2) {
			sb.append(fbases[0]);
		}
		while (sb.length() < 4) {
			sb.append(sbases[0]);
		}
		while (sb.length() < 6) {
			sb.append(tbases[0]);
		}
		return sb.reverse().toString();
	}

	public long decode(String code) {
		int len = code.length();
		long du = 0;
		for (int i = 0; i < code.length(); i++) {
			int val = 0;
			if (i < 2) {
				val = rtbases.get(code.charAt(i));
			} else if (i < 4) {
				val = rsbases.get(code.charAt(i));
			} else if (i < 6) {
				val = rfbases.get(code.charAt(i));
			}
			du = du * base + val;
			// System.out.println(i + "\t" + code.charAt(i) + "\t" + du);
		}
		return du;
	}

	public static char[] shuffleArray(char[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			char a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
		return ar;
	}

	public static void print(char[] ar) {
		for (char c : ar) {
			System.out.print("'" + c + "',");
		}
	}

	public static void main(String args[]) {
		CodeGenerator gen = new CodeGenerator();
		// for (int i = 0; i < gen.tbases.length; i++) {
		// System.out.println("rtbases.put('" + gen.tbases[i] + "'," + i
		// + ");");
		// }
		// System.exit(0);

		// System.out.println(Integer.MAX_VALUE);
		// System.out.println(Long.MAX_VALUE);

		// System.out.println(gen.encode(3844));
		// System.out.println(gen.decode(gen.encode(3844)));
		// System.exit(0);
		// System.out.println(gen.decode("ii44vr"));
		for (long i = 0; i < Math.pow(36, 6); i++) {
			if (i % 10000000 == 0) {
				System.out.println(i);
			}
			String x = gen.encode(i);
			if (i != gen.decode(x)) {
				System.out.println(i + "\t" + x);
				System.exit(0);
			}
			// if (x.length() != 6) {
			// System.out.println(i + "\t" + x);
			// System.exit(0);
			// }
		}
	}

}
