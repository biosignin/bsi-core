package eu.inn.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

class Utils {

	private Utils() {
		
	}
    static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
	
	static <T> T[] addAll(T[] array1, T... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        final Class<?> type1 = array1.getClass().getComponentType();
        @SuppressWarnings("unchecked") // OK, because array is of type T
        T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)){
                throw new IllegalArgumentException("Cannot store "+type2.getName()+" in an array of "
                        +type1.getName(), ase);
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }
	
	
    static int readFully(InputStream input, byte[] buffer ) throws IOException {
    	int offset = 0;
    	int length=buffer.length;
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        }
        int remaining = length;
        while (remaining > 0) {
            int location = length - remaining;
            int count = input.read(buffer, offset + location, remaining);
            if (-1 == count) { // EOF
                break;
            }
            remaining -= count;
        }
        return length - remaining;
    }
   
}
