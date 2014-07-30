package eu.inn.biometric.signature.isoiec19794.exception;

import eu.inn.biometric.signature.isoiec19794.y2007.ChannelAttribute;

public class UnhandledAttributeException extends IsoException {

	private static final long serialVersionUID = 1475784456611940911L;
	private ChannelAttribute attribute;

	public ChannelAttribute getAttribute() {
		return attribute;
	}

	public UnhandledAttributeException(ChannelAttribute attribute) {
		super("Unhandled " + attribute.toString());
		this.attribute = attribute;
	}

}
