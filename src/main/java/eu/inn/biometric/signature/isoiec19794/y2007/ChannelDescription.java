package eu.inn.biometric.signature.isoiec19794.y2007;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ChannelDescription.java is part of BioSignIn project
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

import eu.inn.biometric.signature.isoiec19794.exception.UnhandledAttributeException;
import eu.inn.java.extensions.RefObject;
import eu.inn.java.extensions.UnsignedUtils;

public class ChannelDescription {
	private Map<ChannelAttribute, Double> attributes = new LinkedHashMap<ChannelAttribute, Double>();
	private Channel channel;

	public ChannelDescription(Channel channel) {
		this.channel = channel;
	}

	public static ChannelDescription FromBytes(Channel channel, byte[] bytesIso, int offset,
			RefObject<Integer> channelLength) throws UnhandledAttributeException {
		if (bytesIso == null) {
			throw new NullPointerException("bytesIso");
		}

		ChannelDescription description = new ChannelDescription(channel);

		byte attributesInclusion = bytesIso[offset];
		int length = 1;
		for (int i = 7; i >= 0; i--) {
			if ((attributesInclusion & (((int) 1) << i)) == 0) {
				continue;
			}
			ChannelAttribute attribute = ChannelAttribute.fromValue(i);
			int valueFromBytes = (bytesIso[offset + length] << 8) & 0xFF00 | (bytesIso[(offset + length) + 1] & 0xFF);
			double realValue = 0.0;
			switch (attribute) {
			case RESERVED_FUTURE_USE:
			case LINEAR_COMPONENT_REMOVED:
				break;
			case CONSTANT:
			case STANDARD_DEVIATION:
				realValue = valueFromBytes;
				continue;
			case MEAN_CHANNEL_VALUE:
			case MAXIMUM_CHANNEL_VALUE:
			case MINIMUN_CHANNEL_VALUE:
				realValue = valueFromBytes + channel.getMinValue();
				break;
			case SCALING_VALUE: {
				int exponent = ((0xf800 & valueFromBytes) >> 11) - 0x10;
				int fractionField = 0x7ff & valueFromBytes;
				double mantissa = 1.0 + (((double) fractionField) / Math.pow(2.0, 11.0));
				realValue = mantissa * Math.pow(2.0, (double) exponent);
				break;
			}
			default:
				throw new UnhandledAttributeException(attribute);
			}
			length += 2;
			description.attributes.put(attribute, realValue);
		}

		channelLength.argvalue = length;
		return description;
	}

	byte[] toBytes() throws UnhandledAttributeException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			byte attributesInclusion = 0;
			for (int i = 7; i >= 0; i--) {
				ChannelAttribute key = ChannelAttribute.fromValue(i);
				if (this.attributes.containsKey(key)) {
					attributesInclusion = (byte) (attributesInclusion | ((byte) (((int) 1) << i)));
				}
			}
			baos.write(attributesInclusion);
			for (Map.Entry<ChannelAttribute, Double> attr : this.attributes.entrySet()) {
				double value = 0.0;
				switch (attr.getKey()) {
				case RESERVED_FUTURE_USE:
				case LINEAR_COMPONENT_REMOVED:
					break;

				case CONSTANT:
				case STANDARD_DEVIATION:
					value = attr.getValue();
					break;
				case MAXIMUM_CHANNEL_VALUE:
				case MINIMUN_CHANNEL_VALUE:
				case MEAN_CHANNEL_VALUE:
					value = attr.getValue() - this.channel.getMinValue();
					break;
				case SCALING_VALUE: {
					int mantissa = (int) Math.floor(Math.log(attr.getValue()) / Math.log(2.0));
					short fractorField = (short) (((attr.getValue() / Math.pow(2.0, (double) mantissa)) - 1.0) * 2048.0);
					value = (mantissa + 0x10) << 11;
					value += fractorField & 0x7ff;
					break;
				}
				default:
					throw new UnhandledAttributeException(attr.getKey());
				}
				baos.write(UnsignedUtils.shortToUnsigned((short) value));
			}

			baos.flush();
			// baos.close();
			return baos.toByteArray();
		}
		finally {
			try {
				baos.close();
			}
			catch (Exception ex) {}
		}

	}

	public Double getAttribute(ChannelAttribute attr) {
		return attributes.get(attr);
	}

	public boolean containsAttribute(ChannelAttribute attr) {
		return attributes.containsKey(attr);
	}

	public void putAttribute(ChannelAttribute attr, Double value) {
		attributes.put(attr, value);
	}

}
