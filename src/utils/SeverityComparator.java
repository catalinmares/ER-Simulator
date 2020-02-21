package utils;

import java.util.Comparator;

import entities.Patient;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class SeverityComparator implements Comparator<Patient> {

    private static SeverityComparator instance;

    private SeverityComparator() {
    }

    /**
     * Singleton implementation of SeverityComparator.
     * @return unique instance of the SeverityComparator.
     */
    public static SeverityComparator getInstance() {
        if (instance == null) {
            instance = new SeverityComparator();
        }

        return instance;
    }

    /**
     * Sorts patients by Severity.
     * In case of same Severity, sorts the patients by Name.
     */
    @Override
    public int compare(Patient p1, Patient p2) {
        int result = p2.getState().getSeverity() - p1.getState().getSeverity();

        if (result == 0) {
            result = p2.getName().compareTo(p1.getName());
        }

        return result;
    }
}
