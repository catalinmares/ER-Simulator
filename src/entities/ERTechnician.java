package entities;

import entities.enums.InvestigationResult;
import entities.enums.Status;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class ERTechnician {
    private final int c1 = 75;
    private final int c2 = 40;
    private boolean isAvailable = true;

    public final boolean isAvailable() {
        return isAvailable;
    }

    public final void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    /**
     * ER Technician examines the patient.
     * @param patient from Investigations Queue.
     */
    public final void examinate(Patient patient) {
        // Patient must be operated.
        if (patient.getState().getSeverity() > c1) {
            patient.setResult(InvestigationResult.OPERATE);

          // Patient must be sent home with a treatment.
        } else if (patient.getState().getSeverity() <= c2) {
            patient.setResult(InvestigationResult.TREATMENT);

          // Patient must be hospitalized.
        } else {
            patient.setResult(InvestigationResult.HOSPITALIZE);
        }

        // After examination, patient is sent back to Examinations Queue.
        patient.setStatus(Status.EXAMINATIONSQUEUE);
    }
}
