#Biometric Signature Interface
Biometric Signature Interface (aka `bsi-core`) is a java library for manipulating Signature/sign time series data according to [ISO/IEC 19794-7:2007](http://www.iso.org/iso/home/store/catalogue_tc/catalogue_detail.htm?csnumber=38751)

## What you can do
* You can import from ISO
* You can build your own ISO from a list of [`ManagedIsoPoint`](src/main/java/eu/inn/biometric/signature/managed/ManagedIsoPoint.java) captured (Capturing will be in the scope of next projects so... stay tuned)
* You can build a generic structure where include your ISO and a list of custom extended data (like [`AdditionalHashInfo`](src/main/java/eu/inn/biometric/signature/extendeddata/impl/AdditionalHashInfo.java))
* You can encrypt/decrypt those structures using [`CMS PKCS7 standards`](https://en.wikipedia.org/wiki/Cryptographic_Message_Syntax)

## Examples
#### Import/Export from ISO
```java
import eu.inn.biometric.signature.device.CapturingComponent;
import eu.inn.biometric.signature.managed.ManagedIsoPoint;
import eu.inn.biometric.signature.managed.impl.IsoSignatureData;

void fromIso(byte[] isoBytes){	
	IsoSignatureData iso = IsoSignatureData.fromIso(isoBytes);
    CapturingComponent componentInfo = iso.getCapturingComponent();
    List<ManagedIsoPoint> points = getIsoPoints();
}

byte[] toIso(List<ManagedIsoPoint> points, CapturingComponent componentInfo){
	IsoSignatureData iso = IsoSignatureData.fromData(points, componentInfo);
    return iso.getIsoData(); //pure byte[] iso
}
```
#### Enrich with your own data
1) Let's define your custom data

```java
package eu.inn.examples;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import eu.inn.biometric.signature.extendeddata.AbstractExtendedData;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class GpsCoordinates extends AbstractExtendedData {	

	@XmlElement
    public double latitude;
    
	@XmlElement
    public double longitude;

    public GpsCoordinates() {
	}
    
    public GpsCoordinates(double latitude, double longitude) {
    	this.latitude = latitude;
        this.longitude = longitude;
    }
}
```
2) Enrich your ISO

```java
import eu.inn.biometric.signature.device.CapturingComponent;
import eu.inn.biometric.signature.managed.ManagedIsoPoint;
import eu.inn.biometric.signature.managed.impl.IsoSignatureData;
import eu.inn.examples.GpsCoordinates;

void enrich(IsoSignatureData iso){		
	GpsCoordinates coord = new GpsCoordinates(41.1212, 11.2323);
    coord.setKey("Coord1");
    iso.getExtendedDatas().add(coord); //add your pre-defined extended-data    
    System.out.println(iso.toOutput());
}
```
3) Export your data (yes, this is the output of "toOutput" method)
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<isoSignatureData>
    <extendedDatas>
        <gpsCoordinates componentClass="eu.inn.examples.GpsCoordinates" key="Coord1">
            <latitude>41.1212</latitude>
            <longitude>11.2323</longitude>
        </gpsCoordinates>
    </extendedDatas>
    <isoData>MIAGCSqGSIb3DQEHA6CAMIACAQAxg3MQswCQ........5w7LEtmv6OqFXiGbN9nf9vmAAAAAAAAAAAAAA=</isoData>
</isoSignatureData>
```
#### Encrypt/Decrypt
```java
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import eu.inn.biometric.signature.managed.impl.CMSEncryptedBdi;
import eu.inn.biometric.signature.managed.impl.IsoSignatureData;

void encrypt(IsoSignatureData iso, List<X509Certificate> publicKeys, int maxKeyLength){		
	CMSEncryptedBdi<IsoSignatureData> encrypted = CMSEncryptedBdi.encrypt(origIso, certs, 128);
    String base64Encrypted = encrypted.toOutput();
}

void decrypt(byte[] encryptedBytes, PrivateKey privateKey) {
	CMSEncryptedBdi<IsoSignatureData> enc = CMSEncryptedBdi.fromCMSbytes(encryptedBytes);
	enc.setPrivateKey(privateKey);			
	IsoSignatureData dec = enc.decrypt(IsoSignatureData.class);
}
```
## Requirements
* Java6
* [Simple Xml Serialization](http://simple.sourceforge.net/)
* [BSI Crypto Provider based on BC](https://github.com/biosignin/bc-crypto-bsi) (optional)

##### Include in your maven project
```xml
<project>
	[.......]
	<dependencies>
    	[.......]
		<dependency>
			<groupId>eu.inn.biometric.sign</groupId>
			<artifactId>bsi-core</artifactId>
			<version>0.0.2</version>
		</dependency>
		<dependency>   <!-- you can either use your own cryptographic implementation -->
			<groupId>eu.inn.biometric.sign</groupId>
			<artifactId>bc-crypto-bsi</artifactId>	
			<version>0.0.1</version>
			<scope>runtime</scope>
		</dependency>
	</dependencies>
	<distributionManagement>
		<repository>
			<id>InnoveryRelease</id>
			<name>Innovery Public Releases</name>
			<url>https://developers.innovery.net/artifactory/public-release</url>
		</repository>
	</distributionManagement>
</project>

```
