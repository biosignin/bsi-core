package eu.inn.biometric.signature.isoiec19794.y2007;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * IsoHeader.java is part of BioSignIn project
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
import java.util.LinkedHashMap;
import java.util.Map;

import eu.inn.biometric.signature.isoiec19794.exception.IsoException;
import eu.inn.java.extensions.RefObject;
import eu.inn.java.extensions.UnsignedUtils;

public class IsoHeader {
	private Map<Channel, ChannelDescription> channels = new LinkedHashMap<Channel, ChannelDescription>();
	private final static byte[] FORMAT_IDENTIFIER = new byte[] { 0x53, 0x44, 0x49, 0 };
	private byte reserved = 0;
	private final static byte[] VERSION_NUMBER = new byte[] { 0x20, 0x31, 0x30, 0 };

	IsoHeader() {
	}

	public ChannelDescription getChannel(Channel channel) {
		return channels.get(channel);
	}

	public void putChannel(Channel channel, ChannelDescription description) {
		channels.put(channel, description);
	}

	public boolean containsChannel(Channel channel) {
		return channels.containsKey(channel);
	}

	static IsoHeader fromBytes(byte[] bytesIso, RefObject<Integer> headerLength) throws IsoException {
		if (bytesIso == null) {
			throw new NullPointerException("bytesIso");
		}
		if (bytesIso.length < (FORMAT_IDENTIFIER.length + VERSION_NUMBER.length)) {
			throw new IsoException(
					"Invalid length for iso header. Must be at least equal to format identifier more version number");
		}
		IsoHeader header = new IsoHeader();
		for (int i = 0; i < FORMAT_IDENTIFIER.length; i++) {
			if (FORMAT_IDENTIFIER[i] != bytesIso[i]) {
				throw new IsoException("Unexpected format identifier");
			}
		}
		for (int j = 0; j < VERSION_NUMBER.length; j++) {
			if (VERSION_NUMBER[j] != bytesIso[j + FORMAT_IDENTIFIER.length]) {
				throw new IsoException("Unexpected version number");
			}
		}
		int length = FORMAT_IDENTIFIER.length + VERSION_NUMBER.length;
		int channelsInclusion = (bytesIso[length] << 8) & 0xFF00 | (bytesIso[length + 1] & 0xFF);
		length += 2;
		for (int k = 15; k >= 0; k--) {
			if ((channelsInclusion & (((int) 1) << k)) != 0) {
				RefObject<Integer> channelLength = new RefObject<Integer>();
				Channel channel = Channel.fromInteger(k);
				ChannelDescription description = ChannelDescription.FromBytes(channel, bytesIso, length, channelLength);
				length += channelLength.argvalue;
				header.channels.put(channel, description);
			}
		}
		header.reserved = bytesIso[length];
		length++;
		headerLength.argvalue = length;
		return header;
	}

	byte[] toBytes() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(FORMAT_IDENTIFIER, 0, FORMAT_IDENTIFIER.length);
			baos.write(VERSION_NUMBER, 0, VERSION_NUMBER.length);
			short channelInclusion = 0;
			for (int i = 15; i >= 0; i--) {
				if (this.channels.containsKey(Channel.fromInteger(i))) {
					channelInclusion = (short) (channelInclusion | ((short) (((int) 1) << i)));
				}
			}
			baos.write(UnsignedUtils.shortToUnsigned((short) channelInclusion));
			for (int j = 15; j >= 0; j--) {
				if (this.channels.containsKey(Channel.fromInteger(j))) {
					ChannelDescription cd = this.channels.get(Channel.fromInteger(j));
					byte[] buffer = cd.toBytes();
					baos.write(buffer, 0, buffer.length);
				}
			}

			baos.write(this.reserved);
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
}
