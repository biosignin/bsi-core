package eu.inn.biometric.signature.isoiec19794.exception;

import eu.inn.biometric.signature.isoiec19794.y2007.Channel;
import eu.inn.biometric.signature.isoiec19794.y2007.ChannelAttribute;

public class ChannelAttributeOutOfBoundException extends IsoException {

	private static final long serialVersionUID = 7231252282810049047L;

	public ChannelAttributeOutOfBoundException(ChannelAttribute channelAttribute, Channel channel, double valueToCheck,
			double minValueToCheck, double maxValueToCheck) {
		super(String.format("Attribute %s in channel %s is out of bounds [%s <= %s <= %s]", channelAttribute, channel,
				minValueToCheck, valueToCheck, maxValueToCheck));
	}

}
