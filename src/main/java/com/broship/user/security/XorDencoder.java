package com.broship.user.security;

/**
 * 
 * implementation of XOR encryption
 * 
 * @author congnh
 *
 */
public class XorDencoder {

	public static String encode(String message, String key) {
		message = Base64Dencoder.encode(message.getBytes());
		try {
			if (message == null || key == null)
				return null;

			char[] keys = key.toCharArray();
			char[] mesg = message.toCharArray();

			int ml = mesg.length;
			int kl = keys.length;
			char[] newmsg = new char[ml];

			for (int i = 0; i < ml; i++) {
				newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
			}
			mesg = null;
			keys = null;
			String temp = new String(newmsg);
			return Base64Dencoder.encode(temp.getBytes()).replace("/", "_")
					.replace("+", ".");
		} catch (Exception e) {
			return null;
		}
	}

	public static String decode(String message, String key) throws Exception {
		if (message == null || key == null)
			return null;
		char[] keys = key.toCharArray();
		String temp = new String(Base64Dencoder.decode(message
				.replace("_", "/").replace(".", "+")));
		char[] mesg = temp.toCharArray();

		int ml = mesg.length;
		int kl = keys.length;
		char[] newmsg = new char[ml];

		for (int i = 0; i < ml; i++) {
			newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
		}
		mesg = null;
		keys = null;
		return new String(Base64Dencoder.decode(new String(newmsg)));
	}
}
