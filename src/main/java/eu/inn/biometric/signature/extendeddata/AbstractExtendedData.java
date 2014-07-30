package eu.inn.biometric.signature.extendeddata;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AbstractExtendedData {

	private String key = UUID.randomUUID().toString();

	public final String getKey() {
		return key;
	}

	@XmlAttribute
	public final void setKey(String key) {
		this.key = key;
	}

	public final void setComponentClass(String clazz) {	
		componentClass=clazz;
		if (this.getClass().equals(AbstractExtendedData.class)) {
			System.err.println("JAXBContext:: Cannot find class: " + clazz
					+ " - Please ensure you have added a ServiceProviderDescriptor");
			return;
		}
		if (!this.getClass().getName().equals(clazz))
			throw new IllegalAccessError("something goes wrong: " + clazz + " - " + this.getClass().getName());
	}

	private String componentClass=null;
	
	@XmlAttribute
	public final String getComponentClass() {
		if (componentClass==null)
			componentClass=this.getClass().getName();
		return componentClass;
	}

}
