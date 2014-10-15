package eu.inn.biometric.signature.extendeddata;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ExtendedData.java is part of BioSignIn project
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


import java.util.UUID;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class ExtendedData {

	@Attribute
	private String key = UUID.randomUUID().toString();

	public final String getKey() {
		return key;
	}
	
	public final void setKey(String key) {
		this.key = key;
	}


}
