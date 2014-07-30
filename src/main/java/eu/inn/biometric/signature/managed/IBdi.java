package eu.inn.biometric.signature.managed;

public abstract interface IBdi {
	public abstract boolean isEncrypted();

	public abstract String toOutput() throws RuntimeException;

}
