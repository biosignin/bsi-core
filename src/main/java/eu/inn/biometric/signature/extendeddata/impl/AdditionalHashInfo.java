package eu.inn.biometric.signature.extendeddata.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import eu.inn.biometric.signature.extendeddata.AbstractExtendedData;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
@XmlRootElement
public class AdditionalHashInfo extends AbstractExtendedData {

	private byte[] hashValue = new byte[] { 'a' };
	private String digestAlgorithm = "NO_ALGORITHM";
	private int offset;
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
