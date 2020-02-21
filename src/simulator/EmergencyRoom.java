package simulator;

import java.util.ArrayList;
import java.util.Observable;

import entities.Doctor;
import entities.ERTechnician;
import entities.Nurse;
import entities.Patient;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class EmergencyRoom extends Observable {
    private int simulationLength;
    private int nurses;
    private int investigators;
    private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
    private ArrayList<Doctor> inputDoctors = new ArrayList<Doctor>();
    private ArrayList<Patient> incomingPatients = new ArrayList<Patient>();
    private ArrayList<Nurse> nurseList = new ArrayList<Nurse>();
    private ArrayList<ERTechnician> technicians = new ArrayList<ERTechnician>();

    private ArrayList<Patient> triageQueue = new ArrayList<Patient>();
    private ArrayList<Patient> examinationQueue = new ArrayList<Patient>();
    private ArrayList<Patient> investigationsQueue = new ArrayList<Patient>();
    private int round;

    public int getSimulationLength() {
        return simulationLength;
    }

    public void setSimulationLength(int simulationLength) {
        this.simulationLength = simulationLength;
    }

    public int getNurses() {
        return nurses;
    }

    public void setNurses(int nurses) {
        this.nurses = nurses;

        for (int i = 0; i < nurses; i++) {
            nurseList.add(new Nurse());
        }
    }

    public int getInvestigators() {
        return investigators;
    }

    public void setInvestigators(int investigators) {
        this.investigators = investigators;

        for (int i = 0; i < investigators; i++) {
            technicians.add(new ERTechnician());
        }
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public ArrayList<Patient> getIncomingPatients() {
        return incomingPatients;
    }

    public void setIncomingPatients(ArrayList<Patient> incomingPatients) {
        this.incomingPatients = incomingPatients;
    }

    public ArrayList<Patient> getTriageQueue() {
        return triageQueue;
    }

    public void setTriageQueue(ArrayList<Patient> triageQueue) {
        this.triageQueue = triageQueue;
    }

    public ArrayList<Patient> getExaminationQueue() {
        return examinationQueue;
    }

    public void setExaminationQueue(ArrayList<Patient> examinationQueue) {
        this.examinationQueue = examinationQueue;
    }

    public ArrayList<Patient> getInvestigationsQueue() {
        return investigationsQueue;
    }

    public void setInvestigationsQueue(ArrayList<Patient> investigationsQueue) {
        this.investigationsQueue = investigationsQueue;
    }

    public ArrayList<Nurse> getNurseList() {
        return nurseList;
    }

    public void setNurseList(ArrayList<Nurse> nurseList) {
        this.nurseList = nurseList;
    }

    public ArrayList<ERTechnician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(ArrayList<ERTechnician> technicians) {
        this.technicians = technicians;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ArrayList<Doctor> getInputDoctors() {
        return inputDoctors;
    }

    public void setInputDoctors(ArrayList<Doctor> inputDoctors) {
        this.inputDoctors = inputDoctors;
    }

    public void update() {
        this.setChanged();
        this.notifyObservers();
    }
}
