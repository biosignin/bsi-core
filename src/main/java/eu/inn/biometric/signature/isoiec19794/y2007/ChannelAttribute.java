package eu.inn.biometric.signature.isoiec19794.y2007;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ChannelAttribute.java is part of BioSignIn project
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



public enum ChannelAttribute
{
    RESERVED_FUTURE_USE,
    LINEAR_COMPONENT_REMOVED,
    CONSTANT,
    STANDARD_DEVIATION,
    MEAN_CHANNEL_VALUE,
    MAXIMUM_CHANNEL_VALUE,
    MINIMUN_CHANNEL_VALUE,
    SCALING_VALUE;
    
    public static ChannelAttribute fromValue(int x) {
		switch (x) {
		case 0:
			return RESERVED_FUTURE_USE;
		case 1:
			return LINEAR_COMPONENT_REMOVED;
		case 2:
			return CONSTANT;
		case 3:
			return STANDARD_DEVIATION;
		case 4:
			return MEAN_CHANNEL_VALUE;
		case 5:
			return MAXIMUM_CHANNEL_VALUE;
		case 6:
			return MINIMUN_CHANNEL_VALUE;
		case 7:
			return SCALING_VALUE;
		
		}
		return null;
	}
}