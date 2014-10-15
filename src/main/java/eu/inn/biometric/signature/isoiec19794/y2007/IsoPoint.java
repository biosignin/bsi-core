package eu.inn.biometric.signature.isoiec19794.y2007;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * IsoPoint.java is part of BioSignIn project
 * %%
 * Copyright (C) 2014 Innovery SpA
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */


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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			for (int i = 15; i >= 0; i--) {			
				Channel key = Channel.fromInteger(i);
				if (!this.properties.containsKey(key)) {
					continue;
				}	
				int value = this.properties.get(key)-key.getMinValue();
				baos.write(UnsignedUtils.shortToUnsigned((short)value));
			}
			baos.flush();
			return baos.toByteArray();
		}
		finally {
			try {
				baos.close();
			}
			catch (Exception ex) {}
		}

	}
}
