/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.inn.serialization;

/*
 * #%L
 * BioSignIn (Biometric Signature Interface) Core [http://www.biosignin.org]
 * DeserializationException.java is part of BioSignIn project
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
