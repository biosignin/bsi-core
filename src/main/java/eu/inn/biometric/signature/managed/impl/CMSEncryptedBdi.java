package eu.inn.biometric.signature.managed.impl;

import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.crypto.Cipher;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.KeyTransRecipientInformation;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import eu.inn.biometric.signature.managed.IBdi;
import eu.inn.biometric.signature.managed.IEncryptedBdi;

public class CMSEncryptedBdi<T extends IBdi> implements IEncryptedBdi<T> {
	private PrivateKey pKey = null;
	static {
		if (Security.getProvider("BC") == null)
			Security.addProvider(new BouncyCastleProvider());
	}
	private byte[] cmsBytes;

	public static <T extends IBdi> CMSEncryptedBdi<T> fromCMSbytes(byte[] cmsBytes) {
		CMSEncryptedBdi<T> ret = new CMSEncryptedBdi<T>();
		ret.cmsBytes = cmsBytes;
		return ret;
	}

	private CMSEncryptedBdi() {
		// TODO Auto-generated constructor stub
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
			byte[] decData = decrypt();

			if (clazz == IsoSignatureData.class)
				return (T) IsoSignatureData.fromXmlDocument(new String(decData));
			throw new UnsupportedOperationException("Cannot decrypt object of type " + clazz.getName());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	private byte[] decrypt() {
		try {

			CMSEnvelopedData enveloped = new CMSEnvelopedData(cmsBytes);

			for (Object recip : enveloped.getRecipientInfos().getRecipients()) {
				try {
					KeyTransRecipientInformation rinfo = (KeyTransRecipientInformation) recip;
					byte[] decryptedDocument = rinfo.getContent(new JceKeyTransEnvelopedRecipient(pKey));
					return decryptedDocument;
				} catch (Exception ex) {
				}
			}
			throw new RuntimeException("Cannot decrypt");

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
		int keySize = Cipher.getMaxAllowedKeyLength("AES");
		if (maxKeyLength != null)
			if (keySize > maxKeyLength)
				keySize = maxKeyLength;
		String algIdentifier = CMSAlgorithm.AES128_CBC.getId();
		if (keySize >= 256)
			algIdentifier = CMSAlgorithm.AES256_CBC.getId();
		CMSEnvelopedDataGenerator gen = new CMSEnvelopedDataGenerator();
		for (X509Certificate cert : certificate)
			gen.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(cert));
		CMSTypedData data = new CMSProcessableByteArray(sdc.toOutput().getBytes("UTF-8"));
		CMSEnvelopedData enveloped = gen.generate(data, new JceCMSContentEncryptorBuilder(new ASN1ObjectIdentifier(
				algIdentifier)).build());
		return CMSEncryptedBdi.fromCMSbytes(enveloped.getEncoded());
	}

	@Override
	public String toOutput() throws RuntimeException {
		return new String(Base64.encode(cmsBytes));
	}

}
