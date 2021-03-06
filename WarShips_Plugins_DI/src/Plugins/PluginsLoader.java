package Plugins;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Loads compiled java class to the project from file with
 * full names of that (all packages) and allows to create
 * instances of that
 *
 * @param <T> Desired object (class or interface) to be loaded
 */
public class PluginsLoader<T> {

    public PluginsLoader() {}

    public void load(String fileName) {
        elements = new ArrayList<>();

        try {
            Scanner file = new Scanner(Paths.get(fileName));
            fillArray(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        counter = elements.size();
    }

    public void reload(String fileName) {
        elements.clear();

        try {
            Scanner file = new Scanner(Paths.get(fileName));
            fillArray(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        counter = elements.size();
    }

    private void fillArray(Scanner file) {
        while(file.hasNext()) {
            try {
                Class objectClass = Class.forName(file.next());
                elements.add(objectClass);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public T getInstance() {
        if (counter > 0) {
            counter -= 1;

            try {
                T instance = (T)elements.get(counter).newInstance();
                return instance;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean hasNext() {
        return (counter > 0);
    }

    public Iterator<Class> getIterator() {
        return elements.iterator();
    }

    private ArrayList<Class> elements;
    private int counter;
}
