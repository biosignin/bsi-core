/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.inn.serialization;

/**
 *
 * @author Paterna
 */
public class DeserializationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Class<?> classType;
    private String fileName = "";

    public DeserializationException(Class<?> classType, String message) {
        super(message);
        this.classType = classType;
    }

    public DeserializationException(Class<?> classType, String message, Throwable cause) {
        super(message, cause);
        this.classType = classType;
    }

    public DeserializationException(Class<?> classType, String fileName, String message) {
        super(message);
        this.classType = classType;
        this.fileName = fileName;
    }

    public DeserializationException(Class<?> classType, String fileName, String message, Throwable cause) {
        super(message, cause);
        this.classType = classType;
        this.fileName = fileName;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public String getFileName() {
        return fileName;
    }
}
