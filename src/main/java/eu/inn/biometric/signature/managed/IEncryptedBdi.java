package eu.inn.biometric.signature.managed;

import eu.inn.biometric.signature.managed.IBdi;

public abstract interface IEncryptedBdi<T> extends IBdi {

	public T decrypt(Class<T> clazz) throws Throwable;

}
