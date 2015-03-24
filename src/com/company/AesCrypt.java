package com.company;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * <p> Набор инструментов для шифрования файла</p>
 */
public class AesCrypt {

    byte [] seed;

    public class EncryptResult {
        private byte[] input;

        public EncryptResult(byte[] input) {
            this.input = input;
        }

        public byte[] orThrow() throws IllegalBlockSizeException,
                InvalidKeyException, NoSuchAlgorithmException,
                NoSuchPaddingException, BadPaddingException {
            return encrypt();
        }

        public <E extends Throwable> byte[] orThrow(E e) throws E {
            try {
                return encrypt();
            } catch(Throwable ex) {
                throw e;
            }
        }

        public byte[] orReturn(byte[] def) {
            try {
                return encrypt();
            } catch (Throwable ex) {
                return def;
            }
        }

        private byte[] encrypt() throws NoSuchAlgorithmException,
                IllegalBlockSizeException, InvalidKeyException,
                NoSuchPaddingException, BadPaddingException {
            byte[] rawKey = getRawKey(seed);
            byte[] result = new byte[0];
            result = AesCrypt.encrypt(rawKey, input);
            return result;
        }
    }

    public class DecryptResult {
        byte[] encrypted;

        public DecryptResult(byte[] encrypted) {
            this.encrypted = encrypted;
        }

        public byte[] orThrow() throws IllegalBlockSizeException,
                InvalidKeyException, NoSuchAlgorithmException,
                NoSuchPaddingException, BadPaddingException {
            return decrypt();
        }

        public <E extends Throwable> byte[] orThrow(E e) throws E {
            try {
                return decrypt();
            } catch(Throwable ex) {
                throw e;
            }
        }

        public byte[] orReturn(byte[] def) {
            try {
                return decrypt();
            } catch (Throwable ex) {
                return def;
            }
        }

        private byte[] decrypt() throws NoSuchAlgorithmException,
                IllegalBlockSizeException, InvalidKeyException,
                NoSuchPaddingException, BadPaddingException {
            byte[] rawKey = new byte[0];
            rawKey = getRawKey(seed);
            byte[] enc = encrypted;
            byte[] result = new byte[0];
            result = AesCrypt.decrypt(rawKey, enc);
            return result;
        }
    }

    public AesCrypt(String password) {
        seed = password.getBytes();
    }

    public AesCrypt(byte[] password) {
        seed = password;
    }

    public EncryptResult encrypt(byte[] input) {
        return new EncryptResult(input);
    }

    public DecryptResult decrypt(byte[] encrypted) {
        return new DecryptResult(encrypted);
    }

    protected static byte[] getRawKey(byte[] password) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(password);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    protected static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    protected static String toHex(byte [] buffer) {
        return DatatypeConverter.printBase64Binary(buffer);
    }

    protected static byte[] toByte(String hex) {
        return DatatypeConverter.parseBase64Binary(hex);
    }

}