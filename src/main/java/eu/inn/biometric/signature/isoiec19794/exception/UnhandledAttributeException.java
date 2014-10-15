package eu.inn.biometric.signature.isoiec19794.exception;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * UnhandledAttributeException.java is part of BioSignIn project
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
