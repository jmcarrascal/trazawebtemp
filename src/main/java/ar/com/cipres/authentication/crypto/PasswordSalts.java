package ar.com.cipres.authentication.crypto;

import java.security.SecureRandom;

public class PasswordSalts {

	public static final int SALT_LENGTH = 16;

	public static String nextSalt() {
		byte[] salt = new byte[SALT_LENGTH];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(salt);
		return salt.toString();
	}
}
