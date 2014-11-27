package eu.inn.serialization;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * ByteArrayTransformer.java is part of BioSignIn project
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


import java.util.ServiceLoader;

import org.simpleframework.xml.transform.Transform;

import eu.inn.biometric.signature.crypto.ICryptoProvider;
import eu.inn.biometric.signature.managed.impl.CMSEncryptedBdi;

public class ByteArrayTransformer implements Transform<byte[]> {

	private static ICryptoProvider provider = null;
	static {

		try {
			ServiceLoader<ICryptoProvider> loader = ServiceLoader.load(ICryptoProvider.class,
					ByteArrayTransformer.class.getClassLoader());
			provider = loader.iterator().next();
		} catch (Exception e) {
			System.err.println("No CryptoProvider defined");
			//e.printStackTrace();
			System.out.println("PRENDO IL PROVIDER SETTATO IN CMSEncryptedBdi");
			provider =CMSEncryptedBdi.provider;
		}

	}

	@Override
	public byte[] read(String value) throws Exception {
		return provider.b64Decode(value.getBytes());
	}

	@Override
	public String write(byte[] value) throws Exception {
		return new String(provider.b64Encode(value));
	}

}
