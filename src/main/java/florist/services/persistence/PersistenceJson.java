package florist.services.persistence;

import florist.models.Florist;

import java.io.*;

public class PersistenceJson {
    private static PersistenceJson instance;
    private final String file = "florist.txt";

    private PersistenceJson() {}

    public static PersistenceJson getInstance() {
        if (instance == null) instance = new PersistenceJson();

        return instance;
    }

    public void saveFlorist(Florist florist) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(florist);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public Florist loadFlorist() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Florist) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e);

            return null;
        }
    }
}
