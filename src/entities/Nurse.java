package entities;

import java.util.ArrayList;

import entities.enums.IllnessType;
import utils.UrgencyEstimator;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class Nurse {
    private boolean isAvailable;
    private UrgencyEstimator urgEst = UrgencyEstimator.getInstance();
    private ArrayList<Patient> curedPatients = new ArrayList<Patient>();

    public Nurse() {
        this.isAvailable = true;
    }

    public final boolean isAvailable() {
        return isAvailable;
    }

    public final void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public final boolean isCuringPatients() {
        return (curedPatients.size() != 0);
    }

    public final ArrayList<Patient> getCuredPatients() {
        return curedPatients;
    }

    public final void setCuredPatients(ArrayList<Patient> curedPatients) {
        this.curedPatients = curedPatients;
    }

    /**
     * Nurse consults patient to determine urgency.
     * @param patient from Triage Queue.
     */
    public final void calculateUrgency(Patient patient) {
        IllnessType illnessType = patient.getState().getIllnessName();
        int severity = patient.getState().getSeverity();
        patient.setUrgency(urgEst.estimateUrgency(illnessType, severity));
    }

    /**
     * Nurse gives the treatment to her patient and calculates the rounds remaining.
     */
    public final void treatPatients() {
        for (Patient patient : curedPatients) {
            patient.setTreatment(patient.getTreatment() - 1);
            patient.getState().setSeverity(patient.getState().getSeverity() - Doctor.T);
        }
    }
}
