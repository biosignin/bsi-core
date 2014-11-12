package eu.inn.biometric.signature.managed.impl;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * CMSEncryptedBdi.java is part of BioSignIn project
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


import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.ServiceLoader;

//import org.bouncycastle.util.encoders.Base64;

import eu.inn.biometric.signature.crypto.ICryptoProvider;
import eu.inn.biometric.signature.managed.IBdi;
import eu.inn.biometric.signature.managed.IEncryptedBdi;
import eu.inn.serialization.ByteArrayTransformer;

public class CMSEncryptedBdi<T extends IBdi> implements IEncryptedBdi<T> {
	private PrivateKey pKey = null;
	public static ICryptoProvider provider = null;
	static {
		try {
			ServiceLoader<ICryptoProvider> loader = ServiceLoader.load(ICryptoProvider.class,
					ByteArrayTransformer.class.getClassLoader());
			provider = loader.iterator().next();
			provider.addProvider();
		} catch (Exception e) {
			System.err.println("No CryptoProvider defined");
			//e.printStackTrace();
		}
	}
	private byte[] cmsBytes;

	public static void setCryptoProvider(ICryptoProvider p)
	{
		provider = p;
		provider.addProvider();
	}
	public static <T extends IBdi> CMSEncryptedBdi<T> fromCMSbytes(byte[] cmsBytes) {
		CMSEncryptedBdi<T> ret = new CMSEncryptedBdi<T>();
		ret.cmsBytes = cmsBytes;
		return ret;
	}

	private CMSEncryptedBdi() {
	}

	public PrivateKey getPKey() {
		return pKey;
	}

	public void setPrivateKey(PrivateKey pKey) {
		this.pKey = pKey;
	}

	@SuppressWarnings("unchecked")
	public T decrypt(Class<T> clazz) {
		try {
			byte[] decData = provider.decrypt(cmsBytes, pKey);

			if (clazz == IsoSignatureData.class)
				return (T) IsoSignatureData.fromXmlDocument(new String(decData));
			throw new UnsupportedOperationException("Cannot decrypt object of type " + clazz.getName());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	public boolean isEncrypted() {
		return true;
	}

	public static <T extends IBdi> CMSEncryptedBdi<T> encrypt(T sdc, List<X509Certificate> certificate,
			Integer maxKeyLength) throws Exception {
		if (sdc.isEncrypted())
			throw new IllegalStateException("Object is already encrypted");
		
		return CMSEncryptedBdi.fromCMSbytes(provider.encrypt(sdc.toOutput().getBytes("UTF-8"), certificate,
				maxKeyLength));

	}

	@Override
	public String toOutput() throws RuntimeException {
		return new String(provider.b64Encode(cmsBytes));
	}

}
