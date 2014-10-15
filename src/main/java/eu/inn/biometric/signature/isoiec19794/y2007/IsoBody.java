package eu.inn.biometric.signature.isoiec19794.y2007;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * IsoBody.java is part of BioSignIn project
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
import java.util.ArrayList;
import java.util.List;

import eu.inn.java.extensions.RefObject;

public class IsoBody {
	private byte[] extendedData;
	private List<IsoPoint> points = new ArrayList<IsoPoint>();

	IsoBody() {
	}

	public List<IsoPoint> getPoints() {
		return points;
	}

	static IsoBody fromBytes(IsoHeader header, byte[] bytesIso, int headerLength) {
		int offset = headerLength;

		if (bytesIso == null) {
			throw new NullPointerException("bytesIso");
		}

		IsoBody body = new IsoBody();

		boolean containsExtendedData = bytesIso[offset] != 0;
		offset++;
		int pointsOccurrence = ((bytesIso[offset] << 0x10) | (bytesIso[offset + 1] << 8))
				| (bytesIso[offset + 2] & 0xff);
		offset += 3;
		for (int i = 0; i < pointsOccurrence; i++) {
			RefObject<Integer> pointSize = new RefObject<Integer>();
			body.points.add(IsoPoint.fromBytes(header, bytesIso, offset,
					pointSize));
			offset += pointSize.argvalue;
		}
		if (containsExtendedData) {
			int extendedDataSize = (bytesIso[offset] << 8)
					| (bytesIso[offset + 1] & 0xff);
			offset += 2;
			body.extendedData = new byte[extendedDataSize];
			System.arraycopy(bytesIso, offset, body.extendedData, 0,
					extendedDataSize);
		}
		return body;
	}

	byte[] toBytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			boolean containsExtendedData = (this.extendedData != null && this.extendedData.length > 0);
			baos.write(containsExtendedData ? ((byte) 1) : ((byte) 0));
			baos.write((byte) (this.points.size() >> 0x10));
			baos.write((byte) (this.points.size() >> 8));
			baos.write((byte) this.points.size());
			for (IsoPoint point : this.points) {
				byte[] buffer = point.toBytes();
				baos.write(buffer, 0, buffer.length);
			}
			if (containsExtendedData) {
				baos.write(this.extendedData, 0, this.extendedData.length);
			}
			baos.flush();
			return baos.toByteArray();
//			baos.close();
//			return ret;
		}
		finally {
			try {
				baos.close();
			}
			catch (Exception ex) {}
		}
	}

	public byte[] getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(byte[] extendedData) {
		this.extendedData = extendedData;
	}

}
