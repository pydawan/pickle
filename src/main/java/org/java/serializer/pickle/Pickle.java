package org.java.serializer.pickle;

import org.tinylog.Logger;

import java.io.*;
import java.util.Base64;

/**
 * Class responsible for pickling (serialize) and unpickling (deserialize) objects.
 * It's implementation was based on Python's Pickle module.
 * 
 * @author Thiago Alexandre Martins Monteiro (thicmp@gmail.com)
 */
public abstract class Pickle {

    private Pickle() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Method that serializes the data in a file with append writting mode or not.
     * 
     * @param o        the object to be serialized.
     * @param f        the file where the serialization will occur.
     * @param append   sets the writting mode in the file to the append mode or not.
     */
    public static void dump(Object o, File f, boolean append) {
        // fos - File where the data will be serialized.
        // oos - Object responsible for writing the data in the file.
        if (o != null && f != null) {
            try (FileOutputStream fos = new FileOutputStream(f, append);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.flush();
                oos.writeObject(o);
            } catch (IOException e) {
                Logger.error(e.getMessage());
            }
        }
    }
    
    /**
     * Method that serializes the data in a file.
     * 
     * @param o   the object to be serialized.
     * @param f   the file where the serialization will occur.
     */
    public static void dump(Object o, File f) {
        dump(o, f, false);
    }
    
    /**
     * Method that serializes and stores an object in memory.
     * Then returns a string representing the serialized object.
     * 
     * @param o    the object to be serialized.
     * @return     a string representing the serialized object.
     */
    public static String dumps(Object o) {
        // baos - Reference to a byte sequence in the memory.
        // oos  - Reference to the serializer.
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(o); // Serializing the object.
            return Base64.getEncoder().encodeToString(baos.toByteArray()); // Converting a byte array to a string.
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
        return "";
    }
    
    /**
     * Method that deserializes an object in a specific way.
     * This method converts an object to a specific class type.
     * 
     * @param f      file where serialized object was stored.
     * @param c      class of the object that will be returned.
     * @param <T>    generic type
     * @return       the deserialized object.
     */
    public static <T> T load(File f, Class<T> c) {
        if (f != null && c != null && (f.exists() && f.isFile())) {
                try (FileInputStream fis = new FileInputStream(f); // Input stream of data from a file.
                     ObjectInputStream ois = new ObjectInputStream(fis)) { // Object that read data from a file.
                    Object o = ois.readObject(); // Reading the data.
                    return c.cast(o);
                } catch (IOException | ClassNotFoundException e) {
                    Logger.error(e.getMessage());
                }
        }
        return null;
    }
    
    /**
     * Method that deserialize an object in generic way.
     * 
     * @param f   file where serialized object was stored.
     * @return    the deserialized object.
     */
    public static Object load(File f) {
        return load(f, Object.class);
    }
    
    public static <T> T loads(String s, Class<T> c) {
        Object o = null;
        // Creating an input stream of data that comes from an array of bytes.
        try (ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(s.getBytes()));
             ObjectInputStream ois = new ObjectInputStream(bais)) { // Creating reader object.
            o = ois.readObject(); // Reading the data.
        } catch (ClassNotFoundException | IOException e) {
            Logger.error(e.getMessage());
        }
        return c.cast(o);
    }
    
    /**
     * Method that deserializes an object from a string.
     * 
     * @param s   string that represents the serialized object.
     * @return    the deserialized object.
     */
    public static Object loads(String s) {
        return loads(s, Object.class);
    }
    
}
