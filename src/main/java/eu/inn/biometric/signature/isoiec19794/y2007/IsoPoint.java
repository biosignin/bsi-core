package eu.inn.biometric.signature.isoiec19794.y2007;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import eu.inn.java.extensions.RefObject;
import eu.inn.java.extensions.UnsignedUtils;

public class IsoPoint {
	private Map<Channel, Integer> properties = new LinkedHashMap<Channel, Integer>();

	public void putProp(Channel c, Integer value)
	{
		properties.put(c, value); 
	}

	public boolean containsProp(Channel c) {
		return properties.containsKey(c);
	}
	
	public Integer getProperty(Channel c) {
		return properties.get(c);
	}
	
	static IsoPoint fromBytes(IsoHeader header, byte[] isoData,
			int offset, RefObject<Integer> pointLength) {
		int length = 0;
		IsoPoint point = new IsoPoint();
		for (int i = 15; i >= 0; i--) {
			Channel key = Channel.fromInteger(i);
			if (!header.containsChannel(key)) {
				continue;
			}
			int value = (((isoData[offset + length] << 8) & 0xFF00)
					| (isoData[(offset + length) + 1] & 0xFF))+key.getMinValue();			
			point.properties.put(key, value);
			length += 2;
		}
		pointLength.argvalue=length;
		return point;
	}

	byte[] toBytes() throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			for (int i = 15; i >= 0; i--) {			
				Channel key = Channel.fromInteger(i);
				if (!this.properties.containsKey(key)) {
					continue;
				}	
				int value = this.properties.get(key)-key.getMinValue();
				baos.write(UnsignedUtils.shortToUnsigned((short)value));
			}
			baos.flush();
//			baos.close();
			return baos.toByteArray();
		}

	}
}
