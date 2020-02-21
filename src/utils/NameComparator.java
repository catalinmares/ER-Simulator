package utils;

import java.util.Comparator;

import entities.Patient;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class NameComparator implements Comparator<Patient> {

    private static NameComparator instance;

    private NameComparator() {
    }

    /**
     * Singleton implementation of NameComparator.
     * @return unique instance of the NameComparator.
     */
    public static NameComparator getInstance() {
        if (instance == null) {
            instance = new NameComparator();
        }

        return instance;
    }

    /*
     * For sorting by names alphabetically.
     */
    @Override
    public int compare(Patient p1, Patient p2) {
        return p1.getName().compareTo(p2.getName());
    }
}
