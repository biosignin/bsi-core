package eu.inn.biometric.signature.extendeddata.impl;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * AdditionalHashInfo.java is part of BioSignIn project
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


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import eu.inn.biometric.signature.extendeddata.ExtendedData;

@Root
public class AdditionalHashInfo extends ExtendedData {
	@Element
	private byte[] hashValue = new byte[] { 'a' };
	@Element
	private String digestAlgorithm = "NO_ALGORITHM";
	@Element
	private int offset;
	@Element
	private int length;

	public byte[] getHashValue() {
		return hashValue;
	}

	public String getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	public void setHashValue(byte[] hashValue) {
		this.hashValue = hashValue;
	}

	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
