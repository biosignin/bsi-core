package eu.inn.java.extensions;

public class UnsignedUtils {
	
	private UnsignedUtils() {		
	}
	
	public static byte[] shortToUnsigned(short val)
	{
		return new byte[] {
			(byte) ((val & 0xff00) >> 8),
			(byte) (val & 0xff)
		};
	}
	
	
   
}
