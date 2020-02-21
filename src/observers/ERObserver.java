package observers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import entities.Doctor;
import entities.Nurse;
import entities.Patient;
import entities.enums.Status;
import simulator.EmergencyRoom;
import utils.NameComparator;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class ERObserver implements Observer {

    private static ERObserver instance;
    private static NameComparator nameComparator = NameComparator.getInstance();

    private ERObserver() {
    }

    /**
     * Singleton implementation of ERObserver.
     * @return unique instance of the Observer.
     */
    public static ERObserver getInstance() {
        if (instance == null) {
            instance = new ERObserver();
        }

        return instance;
    }

    /**
     * After each round, we print the patients status, the nurses and doctors activity.
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        EmergencyRoom er = (EmergencyRoom) arg0;

        System.out.printf("~~~~ Patients in round %d ~~~~\n", er.getRound());

        // List of patients in the hospital.
        ArrayList<Patient> roundPatients = new ArrayList<Patient>();

        // Add the patients that came to the hospital until now.
        for (Patient patient : er.getIncomingPatients()) {
            if (patient.getTime() <= er.getRound() - 1) {
                roundPatients.add(patient);
            }
        }

        // Sort the patients alphabetically.
        Collections.sort(roundPatients, nameComparator);

        // Print every patient's status for the current round.
        for (Patient patient : roundPatients) {
            System.out.printf("%s is %s\n", patient.getName(), patient.getStatus().getValue());
        }

        System.out.printf("\n~~~~ Nurses treat patients ~~~~\n");

        int i = 0;
        // For each patient, check if is cured by a nurse and print the nurse activity
        // and the rounds remaining.
        for (Patient patient : roundPatients) {
            for (Nurse nurse : er.getNurseList()) {
                for (Patient curedPatient : nurse.getCuredPatients()) {
                    if (curedPatient.equals(patient)) {
                        System.out.printf("Nurse %d treated %s and patient has %d more ",
                                           i, patient.getName(), patient.getTreatment());

                        if (patient.getTreatment() == 1) {
                            System.out.printf("round\n");
                        } else {
                            System.out.printf("rounds\n");
                        }

                        i++;

                        if (i == er.getNurses()) {
                            i = 0;
                        }
                    }
                }
            }
        }

        System.out.printf("\n~~~~ Doctors check their hospitalized ");
        System.out.printf("patients and give verdicts ~~~~\n");

        // Parse the doctors and print their interaction with their hospitalized patients.
        for (Doctor doctor : er.getInputDoctors()) {
            List<Patient> internedPatients = doctor.getHospitalizedPatients();

            // Sort the patients hospitalized by the doctor alphabetically.
            Collections.sort(internedPatients, nameComparator);

            // Consult every patient and give a verdict.
            for (Iterator<Patient> iter = internedPatients.iterator(); iter.hasNext();) {
                Patient patient = iter.next();
                boolean goHome = doctor.consult(patient);

                // If patient can be sent home with a treatment.
                if (goHome) {
                    System.out.printf("%s sent %s home\n", doctor.getType(), patient.getName());

                    // Update patient status.
                    patient.setStatus(Status.HOME_DONE_TREATMENT);

                    // Remove patient from nurse's patients list.
                    for (Nurse nurse : er.getNurseList()) {
                        if (nurse.getCuredPatients().contains(patient)) {
                            nurse.getCuredPatients().remove(patient);
                        }
                    }

                    // Remove the patient from doctor's list.
                    iter.remove();

                  // If patient must remain in hospital.
                } else {
                    System.out.printf("%s says that %s should remain in hospital\n",
                                               doctor.getType(), patient.getName());
                }
            }
        }

        System.out.printf("\n");
    }
}
