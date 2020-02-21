import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Doctor;
import entities.ERTechnician;
import entities.Nurse;
import entities.Patient;
import entities.enums.IllnessType;
import entities.enums.InvestigationResult;
import entities.enums.Status;
import entities.enums.Urgency;
import observers.ERObserver;
import simulator.EmergencyRoom;
import utils.SeverityComparator;
import utils.PatientComparator;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class Main {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static PatientComparator patientComparator = PatientComparator.getInstance();
    private static SeverityComparator severityComparator = SeverityComparator.getInstance();
    private static ERObserver erObserver = ERObserver.getInstance();

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        // Reading simulation arguments from JSON file.
        File inFile = new File(args[0]);
        EmergencyRoom er = objectMapper.readValue(inFile, EmergencyRoom.class);
        er.addObserver(erObserver);
        // InputDoctor is the list of doctors in the order they appear in input.
        for (Doctor doctor : er.getDoctors()) {
            er.getInputDoctors().add(doctor);
        }

        for (int i = 0; i < er.getSimulationLength(); i++) {
            er.setRound(i + 1);

            // Adding patients of current round in Triage Queue.
            ArrayList<Patient> triageQueue = er.getTriageQueue();
            for (Patient patient : er.getIncomingPatients()) {
                if (patient.getTime() == i) {
                    triageQueue.add(patient);
                    patient.setStatus(Status.TRIAGEQUEUE);
                }
            }
            Collections.sort(triageQueue, severityComparator);

            // Distributing available nurses to determine Urgency.
            for (Patient patient : triageQueue) {
                for (Nurse nurse : er.getNurseList()) {
                    if (nurse.isAvailable()) {
                        nurse.setAvailable(false);
                        nurse.calculateUrgency(patient);
                        break;
                    }
                }
            }
            // After parsing the Triage Queue, nurses become available for consulting again.
            for (Nurse nurse : er.getNurseList()) {
                nurse.setAvailable(true);
            }

            // Moving patients consulted by nurses from Triage to Examination Queue.
            ArrayList<Patient> exQueue = er.getExaminationQueue();
            for (Iterator<Patient> it = triageQueue.iterator(); it.hasNext();) {
                Patient patient = it.next();

                // Update patient status to "in examinations queue".
                if (patient.getUrgency() != Urgency.NOT_DETERMINED) {
                    exQueue.add(patient);
                    patient.setStatus(Status.EXAMINATIONSQUEUE);
                    it.remove();
                }
            }
            Collections.sort(exQueue, patientComparator);

            // Doctors examine patients in Examination Queue and give verdicts.
            ArrayList<Patient> invQueue = er.getInvestigationsQueue();
            for (Iterator<Patient> iter1 = exQueue.iterator(); iter1.hasNext();) {
                Patient patient = iter1.next();
                Doctor toAdd = null;

                // Parsing doctor list to find the first doctor that can cure the patient.
                for (Iterator<Doctor> iter2 = er.getDoctors().iterator(); iter2.hasNext();) {
                    Doctor doctor = iter2.next();
                    IllnessType patientIllness = patient.getState().getIllnessName();

                    // Patient must be operated.
                    if (patient.getResult() == InvestigationResult.OPERATE) {
                        // Check if doctor is surgeon and can cure the illness of the patient.
                        if (doctor.isSurgeon() && doctor.canCure(patientIllness)) {
                            doctor.operate(patient);

                            // Remove the doctor from the list.
                            toAdd = doctor;
                            iter2.remove();
                            break;
                        }
                      // Patient must be consulted by doctor.
                    } else if (doctor.canCure(patientIllness)) {
                        doctor.cure(patient);

                        // Remove the doctor from the list.
                        toAdd = doctor;
                        iter2.remove();
                        break;
                    }
                }
                // If the patient must be operated but there is no surgeon that can operate him.
                if (patient.getResult() == InvestigationResult.OPERATE && toAdd == null) {
                    // Send the patient to another hospital.
                    patient.setStatus(Status.OTHERHOSPITAL);
                  // If the patient was operated or must be hospitalized.
                } else if (patient.getResult() == InvestigationResult.HOSPITALIZE
                           || patient.getResult() == InvestigationResult.OPERATE) {

                    // Find a nurse to provide the patient its treatment.
                    boolean nurseFound = false;
                    for (Nurse nurse : er.getNurseList()) {
                        if (!nurse.isCuringPatients()) {
                            nurse.getCuredPatients().add(patient);
                            nurseFound = true;
                            break;
                        }
                    }
                    // At the end of the round, all patients must be cured, so if all the nurses
                    // are curing patients, we set the first nurse of the list to cure the patient.
                    if (!nurseFound) {
                        er.getNurseList().get(0).getCuredPatients().add(patient);
                    }
                }
                // If patient was consulted by the doctor and sent to ER Technician.
                if (patient.getStatus() == Status.INVESTIGATIONSQUEUE) {
                    invQueue.add(patient);
                }
                // Remove the patient from Examinations Queue.
                iter1.remove();
                // Add the doctor that consulted the patient to the end of the doctors list.
                if (toAdd != null) {
                    er.getDoctors().add(toAdd);
                }
            }
            Collections.sort(invQueue, patientComparator);

            // ER Technicians examine patients in Investigations Queue and give results.
            for (Iterator<Patient> iter = invQueue.iterator(); iter.hasNext();) {
                Patient patient = iter.next();

                // Search for available technicians to examine the patient.
                for (ERTechnician technician : er.getTechnicians()) {
                    if (technician.isAvailable()) {
                        technician.setAvailable(false);
                        technician.examinate(patient);
                        exQueue.add(patient);
                        iter.remove();
                        break;
                    }
                }
            }
            // At the end of the round, all technicians become available for next round.
            for (ERTechnician technician : er.getTechnicians()) {
                technician.setAvailable(true);
            }
            // All the nurses give the treatment to their patients.
            for (Nurse nurse : er.getNurseList()) {
                if (nurse.isCuringPatients()) {
                    nurse.treatPatients();
                }
            }
            // Inform the observers of the changes in the Emergency Room.
            er.update();
        }
    }
}
