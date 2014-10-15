package eu.inn.biometric.signature.isoiec19794.y2007;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * IsoIec19794Signature.java is part of BioSignIn project
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

import eu.inn.biometric.signature.isoiec19794.exception.ChannelAttributeOutOfBoundException;
import eu.inn.biometric.signature.isoiec19794.exception.IsoException;
import eu.inn.java.extensions.RefObject;

public class IsoIec19794Signature {
	private IsoBody body = new IsoBody();
	private IsoHeader header = new IsoHeader();

	public IsoBody getBody() {
		return body;
	}

	public IsoHeader getHeader() {
		return header;
	}

	public static IsoIec19794Signature FromBytes(byte[] isoBytes) throws Exception {
		if (isoBytes == null) {
			throw new NullPointerException("isoBytes");
		}

		IsoIec19794Signature isoIec19794Signature = new IsoIec19794Signature();
		RefObject<Integer> headerLenght = new RefObject<Integer>();
		isoIec19794Signature.header = IsoHeader.fromBytes(isoBytes, headerLenght);

		isoIec19794Signature.body = IsoBody.fromBytes(isoIec19794Signature.header, isoBytes, headerLenght.argvalue);
		return isoIec19794Signature;
	}

	private void checkPointValue(IsoPoint pointToValidate, Channel channelToValidateAgainst) throws IsoException {
		if (!this.header.containsChannel(channelToValidateAgainst)) {
			if (pointToValidate.containsProp(channelToValidateAgainst)) {
				throw new IsoException(String.format(
						"Point %s include channel %s, but this channel is not setted in header", this.body.getPoints()
								.indexOf(pointToValidate), channelToValidateAgainst));
			}
		} else if (this.header.getChannel(channelToValidateAgainst).containsAttribute(ChannelAttribute.CONSTANT)) {
			if (pointToValidate.containsProp(channelToValidateAgainst)
					&& (((double) pointToValidate.getProperty(channelToValidateAgainst) > 0.5))) {
				throw new IsoException(String.format(
						"Point %s include channel %s, but this channel is setted as constant in header", this.body
								.getPoints().indexOf(pointToValidate), channelToValidateAgainst));
			}
		} else {
			if (!pointToValidate.containsProp(channelToValidateAgainst)) {
				throw new IsoException(String.format("Point %s miss declared channel %s", this.body.getPoints()
						.indexOf(pointToValidate), channelToValidateAgainst));
			}
			if (pointToValidate.getProperty(channelToValidateAgainst) < channelToValidateAgainst.getMinValue()
					|| pointToValidate.getProperty(channelToValidateAgainst) > channelToValidateAgainst.getMaxValue()) {
				throw new IsoException(String.format("Point %s has Channel %s out of bounds [%s <= %s <= %s]",
						this.body.getPoints().indexOf(pointToValidate), channelToValidateAgainst,
						channelToValidateAgainst.getMinValue(), pointToValidate.getProperty(channelToValidateAgainst),
						channelToValidateAgainst.getMaxValue()));
			}
		}
	}

	private void checkHeaderValue(boolean throwsOnChannelMissing, Channel channel, boolean throwsOnAttributeMissing,
			ChannelAttribute channelAttribute, double minValueToCheck, double maxValueToCheck) throws IsoException {
		if (!this.header.containsChannel(channel)) {
			if (throwsOnChannelMissing)
				throw new IsoException(String.format("Missing required channel %s", channel));
			else
				return;
		}
		ChannelDescription channelDesc = this.header.getChannel(channel);
		if (!channelDesc.containsAttribute(channelAttribute)) {
			if (throwsOnAttributeMissing)
				throw new IsoException(String.format("Missing Attribute %s in channel %s", channelAttribute, channel));
			else
				return;
		}
		double valueToCheck = channelDesc.getAttribute(channelAttribute);
		if (channelAttribute == ChannelAttribute.SCALING_VALUE) {
			minValueToCheck = 0;
			maxValueToCheck = Short.MAX_VALUE - Short.MIN_VALUE;
		}
		if (valueToCheck < minValueToCheck || valueToCheck > maxValueToCheck)
			throw new ChannelAttributeOutOfBoundException(channelAttribute, channel, valueToCheck, minValueToCheck,
					maxValueToCheck);
	}

	private void checkHeaderValue(boolean throwsOnChannelMissing, Channel channel, boolean throwsOnAttributeMissing,
			ChannelAttribute channelAttribute) throws Exception {
		checkHeaderValue(throwsOnChannelMissing, channel, throwsOnAttributeMissing, channelAttribute,
				channel.getMinValue(), channel.getMaxValue());
	}

	public byte[] toBytes() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			byte[] buffer = this.header.toBytes();
			baos.write(buffer, 0, buffer.length);
			byte[] buffer2 = this.body.toBytes();
			baos.write(buffer2, 0, buffer2.length);
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

	public void validate() throws Exception {
		if (this.header == null) {
			throw new IllegalStateException("Header is missing");
		}
		if (this.body == null) {
			throw new IllegalStateException("Body is missing");
		}

		this.checkHeaderValue(true, Channel.X, true, ChannelAttribute.SCALING_VALUE);
		this.checkHeaderValue(true, Channel.X, true, ChannelAttribute.MINIMUN_CHANNEL_VALUE);
		this.checkHeaderValue(true, Channel.X, true, ChannelAttribute.MAXIMUM_CHANNEL_VALUE);
		this.checkHeaderValue(true, Channel.Y, true, ChannelAttribute.SCALING_VALUE);
		this.checkHeaderValue(true, Channel.Y, true, ChannelAttribute.MINIMUN_CHANNEL_VALUE);
		this.checkHeaderValue(true, Channel.Y, true, ChannelAttribute.MAXIMUM_CHANNEL_VALUE);
		this.checkHeaderValue(false, Channel.Z, true, ChannelAttribute.SCALING_VALUE);
		this.checkHeaderValue(false, Channel.Z, true, ChannelAttribute.MINIMUN_CHANNEL_VALUE);
		this.checkHeaderValue(false, Channel.Z, true, ChannelAttribute.MAXIMUM_CHANNEL_VALUE);
		this.checkHeaderValue(false, Channel.F, true, ChannelAttribute.SCALING_VALUE);
		this.checkHeaderValue(false, Channel.F, true, ChannelAttribute.MINIMUN_CHANNEL_VALUE);
		this.checkHeaderValue(false, Channel.F, true, ChannelAttribute.MAXIMUM_CHANNEL_VALUE);
		if (!this.header.containsChannel(Channel.T) && !this.header.containsChannel(Channel.DT)) {
			throw new Exception("Missing mandatory channel (T or DT)");
		}
		if (this.header.containsChannel(Channel.T) && this.header.containsChannel(Channel.DT)) {
			throw new Exception("Cannot use T and DT channels simultaneously");
		}
		if (this.header.containsChannel(Channel.DT)) {
			this.checkHeaderValue(true, Channel.DT, true, ChannelAttribute.SCALING_VALUE);
			this.checkHeaderValue(true, Channel.DT, false, ChannelAttribute.CONSTANT, 0.0, 1.0);
		}
		if (this.header.containsChannel(Channel.T)) {
			this.checkHeaderValue(true, Channel.T, true, ChannelAttribute.SCALING_VALUE);
		}
		for (IsoPoint point : this.body.getPoints()) {
			for (int i = 15; i >= 0; i--) {
				Channel channel = Channel.fromInteger(i);
				this.checkPointValue(point, channel);
			}
		}
	}
}
