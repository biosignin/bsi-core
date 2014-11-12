package eu.inn.serialization;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * JaxbSerializer.java is part of BioSignIn project
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


import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import eu.inn.biometric.signature.crypto.ICryptoProvider;

public class XmlSerializer<T> {
	private Serializer marshaller;

	private Class<T> classType;

	public XmlSerializer(Class<T> classe) {
		classType = classe;
		try {
			RegistryMatcher m = new RegistryMatcher();
			m.bind(byte[].class, new ByteArrayTransformer());
			marshaller = new Persister(m);
		} catch (Exception ex) {
			throw new RuntimeException("Cannot instantiate JaxbSerializer for class: " + classType.getName(), ex);
		}
	}

	public T deserialize(File f) throws DeserializationException {
		try {
			return marshaller.read(classType, f);
		} catch (Exception ex) {
			throw new DeserializationException(classType, "Cannot deserialize", f.getAbsolutePath(), ex);
		}
	}

	public T deserialize(InputStream is) throws DeserializationException {
		try {
			return marshaller.read(classType, is);
		} catch (Exception ex) {
			throw new DeserializationException(classType, "Cannot deserialize", ex);
		}
	}

	public T deserialize(String s) throws DeserializationException {
		try {
			return marshaller.read(classType, s);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DeserializationException(classType, "Cannot deserialize", ex);
		}
	}

	public void serialize(File f, T object) throws SerializationException {
		try {
			marshaller.write(object, f);
		} catch (Exception ex) {
			throw new SerializationException(classType, "Cannot serialize", f.getAbsolutePath(), ex);
		}
	}

	public String serialize(T object) throws SerializationException {
		try {
			StringWriter sr = new StringWriter();
			marshaller.write(object, sr);			
			return sr.toString();			
		} catch (Exception ex) {
			throw new SerializationException(classType, "Cannot serialize", ex);
		}

	}
}
