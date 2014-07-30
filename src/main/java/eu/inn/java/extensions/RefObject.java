package eu.inn.java.extensions;

public final class RefObject<T> {
	public T argvalue;

	public RefObject(T refarg) {
		argvalue = refarg;
	}
	public RefObject() {
	}
	
	public boolean isNull() {
		return argvalue==null;
	}
}
