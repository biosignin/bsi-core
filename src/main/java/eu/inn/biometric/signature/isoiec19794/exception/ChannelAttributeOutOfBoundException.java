package eu.inn.biometric.signature.isoiec19794.exception;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ChannelAttributeOutOfBoundException.java is part of BioSignIn project
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
