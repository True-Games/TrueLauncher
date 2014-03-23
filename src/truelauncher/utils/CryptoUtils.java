package truelauncher.utils;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptoUtils {

	private static byte[] ivBytes = new byte[]{25,63,23,13,15,65,25,28};
	public static String decryptString(String string) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, IOException, InvalidAlgorithmParameterException
	{
		byte[] keyBytes = getKey();
   	 	SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
   	 	IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
   	 	Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
   	 	cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
   	 	String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(string)));
   	 	return decryptedString;
    }
    public static String encryptString(String string) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException, InvalidAlgorithmParameterException
    {
   	 	byte[] keyBytes = getKey();
   	 	SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
   	 	IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
   	 	Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
   	 	cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
   	 	String encryptedString = Base64.encodeBase64String(cipher.doFinal(string.getBytes("UTF8")));
   	 	return encryptedString;
    }
    private static byte[] getKey() throws SocketException
    {
   	 	Enumeration<NetworkInterface> ni = java.net.NetworkInterface.getNetworkInterfaces();
   	 	if (ni.hasMoreElements())
   	 	{
   	 		byte[] mac = ni.nextElement().getHardwareAddress();
   	 		if (mac != null)
   	 		{
   	 			return Arrays.copyOf(mac, 8);
   	 		} else
   	 		{
   	 			return "aGorRiaA".getBytes();
   	 		}
   	 	} else
   	 	{
   	 		return "aGorRiaA".getBytes();
   	 	}
    }

}
