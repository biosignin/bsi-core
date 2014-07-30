package eu.inn.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class JaxbSerializer<T> {
	private Marshaller marshaller;

	private Unmarshaller unmarshaller;
	private Class<T> classType;
	private boolean skipSerializer;
	private boolean skipDeserializer;

	public JaxbSerializer(Class<T> classe) {
		this(classe, new Class<?>[] {}, false, false);
	}

	private String classToXsd(Class<?> c) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(c);
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			SchemaOutputResolver sor = new SchemaOutputResolver() {
				@Override
				public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
					StreamResult result = new StreamResult(out);
					result.setSystemId("");
					return result;
				}
			};
			jaxbContext.generateSchema(sor);
			out.flush();
			ByteArrayInputStream bais = new ByteArrayInputStream(out.toByteArray());
			byte[] stringBytes = new byte[out.size()];
			Utils.readFully(bais, stringBytes);

			return new String(stringBytes);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public JaxbSerializer(Class<T> classe, Class<?>[] referencedClass, boolean skipSerializer,
			boolean skipDeserializer) {
		classType = classe;
		try {
			JAXBContext context;

			if (referencedClass == null) {
				context = JAXBContext.newInstance(classType);
			} else {
				context = JAXBContext.newInstance((Class<?>[]) Utils.addAll(referencedClass,
						new Class<?>[] { classe }));
			}
			this.skipDeserializer = skipDeserializer;
			this.skipSerializer = skipSerializer;
			if (!skipDeserializer) {
				unmarshaller = context.createUnmarshaller();

				StreamSource[] sources = new StreamSource[(referencedClass == null ? 0
						: referencedClass.length) + 1];
				sources[0] = new StreamSource(new StringReader(classToXsd(classe)));
				int i = 1;
				if (referencedClass != null)
					for (Class<?> c : referencedClass) {
						sources[i++] = new StreamSource(new StringReader(classToXsd(c)));
					}

				// TODO: da riaggiungere, 
//				 unmarshaller.setSchema(factory.newSchema(sources));
			}
			if (!skipSerializer) {

				marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			}
		} catch (Exception ex) {
			throw new RuntimeException("Cannot instantiate JaxbSerializer for class: " + classType.getName(),
					ex);
		}
	}

	@SuppressWarnings("unchecked")
	public T deserialize(File f) throws DeserializationException {
		try {
			if (skipDeserializer)
				throw new IllegalStateException("JaxbSerializer created with no support for deserialization");
			return (T) unmarshaller.unmarshal(f);
		} catch (JAXBException ex) {
			throw new DeserializationException(classType, "Cannot deserialize", f.getAbsolutePath(), ex);
		}
	}

	@SuppressWarnings("unchecked")
	public T deserialize(InputStream is) throws DeserializationException {
		try {
			if (skipDeserializer)
				throw new IllegalStateException("JaxbSerializer created with no support for deserialization");
			return (T) unmarshaller.unmarshal(is);
		} catch (JAXBException ex) {
			throw new DeserializationException(classType, "Cannot deserialize", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public T deserialize(String s) throws DeserializationException {
		if (skipDeserializer)
			throw new IllegalStateException("JaxbSerializer created with no support for deserialization");
		try {
			StringReader sr = new StringReader(s);
			return (T) unmarshaller.unmarshal(sr);
		} catch (JAXBException ex) {
			ex.printStackTrace();
			throw new DeserializationException(classType, "Cannot deserialize", ex);
		}
	}

	public void serialize(File f, T object) throws SerializationException {
		try {
			if (skipSerializer)
				throw new IllegalStateException("JaxbSerializer created with no support for serialization");
			marshaller.marshal(object, f);
		} catch (JAXBException ex) {
			throw new SerializationException(classType, "Cannot serialize", f.getAbsolutePath(), ex);
		}
	}

	public String serialize(T object) throws SerializationException {
		return serialize(object, false);

	}

	public String serialize(T object, boolean forceIndentation) throws SerializationException {
		try {
			if (skipSerializer)
				throw new IllegalStateException("JaxbSerializer created with no support for serialization");
			StringWriter sr = new StringWriter();
			marshaller.marshal(object, sr);
			String ret = sr.toString();
			if (forceIndentation) {
				try {
					ret = XmlUtil.getOuterXml(XmlUtil.loadXMLFromString(ret));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return ret;
		} catch (JAXBException ex) {
			throw new SerializationException(classType, "Cannot serialize", ex);
		}

	}
}
