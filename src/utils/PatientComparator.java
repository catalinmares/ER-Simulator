package utils;

import java.util.Comparator;

import entities.Patient;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class PatientComparator implements Comparator<Patient> {
    private static PatientComparator instance;

    private PatientComparator() {
    }

    /**
     * Singleton implementation of PatientComparator.
     * @return unique instance of the PatientComparator.
     */
    public static PatientComparator getInstance() {
        if (instance == null) {
            instance = new PatientComparator();
        }

        return instance;
    }

    /**
     * Sorts patients by Urgency.
     * In case of same Urgency, sorts patients by Severity.
     * In case of same Severity, sorts patints by Name.
     *
     * Used for sorting Examinations and Investigations Queue.
     */
    @Override
    public int compare(Patient p1, Patient p2) {
        int result = p2.getUrgency().getValue() - p1.getUrgency().getValue();

        if (result == 0) {
            result = p2.getState().getSeverity() - p1.getState().getSeverity();
        }

        if (result == 0) {
            result = p2.getName().compareTo(p1.getName());
        }

        return result;
    }
}
