package com.broship.user.security;

/**
 * implementation of Base64 encoding/decoding specified in <a
 * href="https://tools.ietf.org/html/rfc2045#section-6.8">RFC2045, section
 * 6.8</a>
 * 
 * @author congnh
 * 
 */
public class Base64Dencoder {

	private static char[] BASE64_ENCODE_TABLE = new char[] { 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', '+', '/' };
	private static byte[] BASE64_DECODE_TABLE = new byte[] { -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, 62, -1, 63, -1, -1, 52, 53, 54, 55, 56, 57, 58,
			59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
			-1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,
			37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };

	private static char[] encode(byte c1, byte c2, byte c3) {
		char[] result = new char[4];
		// long s = (c1 << 16) + (c2 << 8) + c3;
		// System.out.println(Long.toBinaryString(s));
		// result[0] = (char) BASE64_ENCODE_TABLE[(int)((s & (0x3F << 18)) >>
		// 18)];
		// result[1] = (char) BASE64_ENCODE_TABLE[(int)((s & (0x3F << 12)) >>
		// 12)];
		// result[2] = (char) BASE64_ENCODE_TABLE[(int)((s & (0x3F << 6)) >>
		// 6)];
		// result[3] = (char) BASE64_ENCODE_TABLE[(int)((s & (0x3F << 0)) >>
		// 0)];
		// khong dung doan code bi comment o tren, li do convert kieu
		result[0] = BASE64_ENCODE_TABLE[((c1 & 0xFC) >> 2)];
		result[1] = BASE64_ENCODE_TABLE[((c1 & 0x03) << 4) | ((c2 & 0xF0) >> 4)];
		result[2] = BASE64_ENCODE_TABLE[((c2 & 0x0F) << 2) | ((c3 & 0xC0) >> 6)];
		result[3] = BASE64_ENCODE_TABLE[(c3 & 0x3F)];
		// System.out.println(Integer.toBinaryString(s));
		return result;
	}

	private static byte[] decode(char c1, char c2, char c3, char c4) {
		byte[] result = new byte[3];
		// int s;
		// if (c4 == '=') {
		// if (c3 == '=') {
		// s = (BASE64_DECODE_TABLE[c1] << 18) + (BASE64_DECODE_TABLE[c2] <<
		// 12);
		// result = new byte[]{(byte) ((s & (0xFF << 16)) >> 16)};
		// } else {
		// s = (BASE64_DECODE_TABLE[c1] << 18) + (BASE64_DECODE_TABLE[c2] << 12)
		// +
		// (BASE64_DECODE_TABLE[c3] << 6);
		// result = new byte[]{(byte) ((s & (0xFF << 16)) >> 16), (byte) ((s &
		// (0xFF
		// << 8)) >> 8)};
		// }
		// } else {
		// s = (BASE64_DECODE_TABLE[c1] << 18) + (BASE64_DECODE_TABLE[c2] << 12)
		// +
		// (BASE64_DECODE_TABLE[c3] << 6) + BASE64_DECODE_TABLE[c4];
		// result[0] = (byte) ((s & (0xFF << 16)) >> 16);
		// result[1] = (byte) ((s & (0xFF << 8)) >> 8);
		// result[2] = (byte) ((s & (0xFF << 0)) >> 0);
		// }
		result[0] = (byte) (((BASE64_DECODE_TABLE[c1] & 0x3F) << 2) | ((BASE64_DECODE_TABLE[c2] & 0x30) >> 4));
		result[1] = (byte) (((BASE64_DECODE_TABLE[c2] & 0x0F) << 4) | ((BASE64_DECODE_TABLE[c3] & 0x3C) >> 2));
		result[2] = (byte) (((BASE64_DECODE_TABLE[c3] & 0x03) << 6) | ((BASE64_DECODE_TABLE[c4] & 0x3F) >> 0));
		if (c4 == '=') {
			if (c3 == '=') {
				result = new byte[] { result[0] };
			} else {
				result = new byte[] { result[0], result[1] };
			}
		}
		// System.out.println(Integer.toBinaryString(s));
		return result;
	}

	public static String encode(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		StringBuilder result = new StringBuilder(bytes.length / 3 * 4 + 4);
		int i;
		int j;
		for (i = 0; i < bytes.length / 3; i++) {
			j = i * 3;
			result.append(encode(bytes[j], bytes[j + 1], bytes[j + 2]));
		}
		i = bytes.length - i * 3;
		if (i > 0) {
			if (i == 1) {
				char[] lasts = encode(bytes[bytes.length - 1], (byte) 0,
						(byte) 0);
				result.append(lasts[0]);
				result.append(lasts[1]);
				result.append('=');
				result.append('=');
			} else {// i==2
				char[] lasts = encode(bytes[bytes.length - 2],
						bytes[bytes.length - 1], (byte) 0);
				result.append(lasts[0]);
				result.append(lasts[1]);
				result.append(lasts[2]);
				result.append('=');
			}
		}
		return result.toString();
	}

	public static byte[] decode(String str) {
		if (str == null) {
			return null;
		}
		byte[] result;
		if (str.length() > 1 && str.charAt(str.length() - 1) == '=') {
			if (str.length() > 2 && str.charAt(str.length() - 2) == '=') {
				result = new byte[str.length() / 4 * 3 - 2];
			} else {
				result = new byte[str.length() / 4 * 3 - 1];
			}
		} else {
			result = new byte[str.length() / 4 * 3];
		}
		int i, j;
		for (i = 0; i < str.length() / 4 - 1; i++) {
			j = i * 4;
			System.arraycopy(
					decode(str.charAt(j), str.charAt(j + 1), str.charAt(j + 2),
							str.charAt(j + 3)), 0, result, i * 3, 3);
		}
		j = i * 4;
		if (i * 3 < result.length) {
			byte[] lasts = decode(str.charAt(j), str.charAt(j + 1),
					str.charAt(j + 2), str.charAt(j + 3));
			System.arraycopy(lasts, 0, result, i * 3, lasts.length);
		}
		return result;
	}
}
