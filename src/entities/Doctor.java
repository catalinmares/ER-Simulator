package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.enums.IllnessType;
import entities.enums.InvestigationResult;
import entities.enums.Status;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class Doctor {

    private String type;
    private boolean isSurgeon;
    private int maxForTreatment;
    private List<Patient> hospitalizedPatients = new ArrayList<Patient>();
    private List<IllnessType> treatedIllnesses;
    private float c1;
    private float c2;
    static final int T = 22;

    private static final int MIN_HOSP = 3;

    private static final float CARDIO_C1 = 0.4F;
    private static final float CARDIO_C2 = 0.1F;
    private static final float ER_PHY_C1 = 0.1F;
    private static final float ER_PHY_C2 = 0.3F;
    private static final float GASTRO_C1 = 0.5F;
    private static final float GASTRO_C2 = 0;
    private static final float GEN_SURG_C1 = 0.2F;
    private static final float GEN_SURG_C2 = 0.2F;
    private static final float INT_C1 = 0.01F;
    private static final float INT_C2 = 0;
    private static final float NEURO_C1 = 0.5F;
    private static final float NEURO_C2 = 0.1F;

    public final String getType() {
        return type;
    }

    public final void setType(String type) {
        switch (type) {
            case "CARDIOLOGIST":
                this.type = "Cardiologist";
                setC1(CARDIO_C1);
                setC2(CARDIO_C2);
                setTreatedIllnesses(Arrays.asList(IllnessType.HEART_ATTACK,
                                                  IllnessType.HEART_DISEASE));
                break;

            case "ER_PHYSICIAN":
                this.type = "ERPhysician";
                setC1(ER_PHY_C1);
                setC2(ER_PHY_C2);
                setTreatedIllnesses(Arrays.asList(IllnessType.ALLERGIC_REACTION,
                                                  IllnessType.BROKEN_BONES,
                                                  IllnessType.BURNS,
                                                  IllnessType.CAR_ACCIDENT,
                                                  IllnessType.CUTS,
                                                  IllnessType.HIGH_FEVER,
                                                  IllnessType.SPORT_INJURIES));
                break;

            case "GASTROENTEROLOGIST":
                this.type = "Gastroenterologist";
                setC1(GASTRO_C1);
                setC2(GASTRO_C2);
                setTreatedIllnesses(Arrays.asList(IllnessType.ABDOMINAL_PAIN,
                                                  IllnessType.ALLERGIC_REACTION,
                                                  IllnessType.FOOD_POISONING));
                break;

            case "GENERAL_SURGEON":
                this.type = "General Surgeon";
                setC1(GEN_SURG_C1);
                setC2(GEN_SURG_C2);
                setTreatedIllnesses(Arrays.asList(IllnessType.ABDOMINAL_PAIN,
                                                  IllnessType.BURNS,
                                                  IllnessType.CAR_ACCIDENT,
                                                  IllnessType.CUTS,
                                                  IllnessType.SPORT_INJURIES));
                break;

            case "INTERNIST":
                this.type = "Internist";
                setC1(INT_C1);
                setC2(INT_C2);
                setTreatedIllnesses(Arrays.asList(IllnessType.ABDOMINAL_PAIN,
                                                  IllnessType.ALLERGIC_REACTION,
                                                  IllnessType.FOOD_POISONING,
                                                  IllnessType.HEART_DISEASE,
                                                  IllnessType.HIGH_FEVER,
                                                  IllnessType.PNEUMONIA));
                break;

            case "NEUROLOGIST":
                this.type = "Neurologist";
                setC1(NEURO_C1);
                setC2(NEURO_C2);
                setTreatedIllnesses(Arrays.asList(IllnessType.STROKE));
                break;

            default:
                break;
        }
    }

    public final boolean isSurgeon() {
        return isSurgeon;
    }

    public final void setIsSurgeon(boolean isSurgeon) {
        this.isSurgeon = isSurgeon;

        // Ignoring the wrong input tests and setting General Surgeon as surgeon.
        if (this.type.equals("General Surgeon")) {
            this.isSurgeon = true;
        }
    }

    public final int getMaxForTreatment() {
        return maxForTreatment;
    }

    public final void setMaxForTreatment(int maxForTreatment) {
        this.maxForTreatment = maxForTreatment;
    }

    public final List<Patient> getHospitalizedPatients() {
        return hospitalizedPatients;
    }

    public final void setHospitalizedPatients(ArrayList<Patient> hospitalizedPatients) {
        this.hospitalizedPatients = hospitalizedPatients;
    }

    public final List<IllnessType> getTreatedIllnesses() {
        return treatedIllnesses;
    }

    public final void setTreatedIllnesses(List<IllnessType> list) {
        this.treatedIllnesses = list;
    }

    public final float getC1() {
        return c1;
    }

    public final void setC1(float c1) {
        this.c1 = c1;
    }

    public final float getC2() {
        return c2;
    }

    public final void setC2(float c2) {
        this.c2 = c2;
    }

    /**
     * @param illness of the patient.
     * @return whether doctor can cure the patient or not.
     */
    public final boolean canCure(IllnessType illness) {
        if (treatedIllnesses.contains(illness)) {
            return true;
        }

        return false;
    }

    /**
     * Doctor cures the patient from Examinations Queue.
     * @param patient from the Examinations Queue.
     */
    public final void cure(Patient patient) {

        // If the patient is seen for the first time by a doctor.
        if (patient.getResult() == InvestigationResult.NOT_DIAGNOSED) {

            // If the doctor can treat the patient, he will be sent home with treatment.
            if (patient.getState().getSeverity() <= maxForTreatment) {
                switch (type) {
                    case "Cardiologist": patient.setStatus(Status.HOME_CARDIO); break;
                    case "ERPhysician": patient.setStatus(Status.HOME_ERPHYSICIAN); break;
                    case "Gastroenterologist": patient.setStatus(Status.HOME_GASTRO); break;
                    case "General Surgeon": patient.setStatus(Status.HOME_SURGEON); break;
                    case "Internist": patient.setStatus(Status.HOME_INTERNIST); break;
                    case "Neurologist": patient.setStatus(Status.HOME_NEURO); break;
                    default: break;
                }

              // Otherwise, he will be sent to ER Technician.
            } else {
                patient.setStatus(Status.INVESTIGATIONSQUEUE);
            }

          // If the patient was examined by ER Technician and must be hospitalized.
        } else if (patient.getResult() == InvestigationResult.HOSPITALIZE) {
            switch (type) {
                case "Cardiologist": patient.setStatus(Status.HOSPITALIZED_CARDIO); break;
                case "ERPhysician": patient.setStatus(Status.HOSPITALIZED_ERPHYSICIAN); break;
                case "Gastroenterologist": patient.setStatus(Status.HOSPITALIZED_GASTRO); break;
                case "General Surgeon": patient.setStatus(Status.HOSPITALIZED_SURGEON); break;
                case "Internist": patient.setStatus(Status.HOSPITALIZED_INTERNIST); break;
                case "Neurologist": patient.setStatus(Status.HOSPITALIZED_NEURO); break;
                default: break;
            }

            hospitalize(patient);

          // If the patient was examined by ER Technician and must be sent home with treatment.
        } else {
            switch (type) {
                case "Cardiologist": patient.setStatus(Status.HOME_CARDIO); break;
                case "ERPhysician": patient.setStatus(Status.HOME_ERPHYSICIAN); break;
                case "Gastroenterologist": patient.setStatus(Status.HOME_GASTRO); break;
                case "General Surgeon": patient.setStatus(Status.HOME_SURGEON); break;
                case "Internist": patient.setStatus(Status.HOME_INTERNIST); break;
                case "Neurologist": patient.setStatus(Status.HOME_NEURO); break;
                default: break;
            }
        }
    }

    /**
     * Doctor consults one of his hospitalized patients and gives a verdict.
     * @param patient from doctor's hospitalized patients list.
     * @return whether the patient can be sent home or must remain in hospital.
     */
    public final boolean consult(Patient patient) {
        if (patient.getTreatment() == 0 || patient.getState().getSeverity() <= 0) {
            return true;
        }

        return false;
    }

     /**
     * Doctor operates the patient.
     * @param patient from Examinations Queue.
     */
    public final void operate(Patient patient) {
        switch (type) {
            case "Cardiologist": patient.setStatus(Status.OPERATED_CARDIO); break;
            case "ERPhysician": patient.setStatus(Status.OPERATED_ERPHYSICIAN); break;
            case "General Surgeon": patient.setStatus(Status.OPERATED_SURGEON); break;
            case "Neurologist": patient.setStatus(Status.OPERATED_NEURO); break;
            default: break;
        }

        // Update the severity of the patient after operation.
        int patientSeverity = patient.getState().getSeverity();
        patient.getState().setSeverity(patientSeverity - Math.round(patientSeverity * c2));

        // Hospitalize the patient after the operation.
        hospitalize(patient);
    }

    /**
     * Doctor hospitalizes the patient.
     * @param patient from Examinations Queue.
     */
    public final void hospitalize(Patient patient) {
        // Calculate the number of rounds and add the patient to doctor's hospitalized patients.
        patient.setTreatment(Math.round(Math.max(patient.getState().getSeverity() * c1, MIN_HOSP)));
        hospitalizedPatients.add(patient);
    }
}
