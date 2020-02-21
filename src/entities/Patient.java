package entities;

import entities.enums.IllnessType;
import entities.enums.InvestigationResult;
import entities.enums.Status;
import entities.enums.Urgency;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class Patient {
    private int id;
    private String name;
    private int age;
    private int time;
    private State state;
    private Urgency urgency = Urgency.NOT_DETERMINED;
    private InvestigationResult result = InvestigationResult.NOT_DIAGNOSED;
    private Status status;
    private int treatment;

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final int getAge() {
        return age;
    }

    public final void setAge(int age) {
        this.age = age;
    }

    public final int getTime() {
        return time;
    }

    public final void setTime(int time) {
        this.time = time;
    }

    public final State getState() {
        return state;
    }

    public final void setState(State state) {
        this.state = state;
    }

    public final Urgency getUrgency() {
        return urgency;
    }

    public final void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public final InvestigationResult getResult() {
        return result;
    }

    public final void setResult(InvestigationResult result) {
        this.result = result;
    }

    public final Status getStatus() {
        return status;
    }

    public final void setStatus(Status status) {
        this.status = status;
    }

    public final int getTreatment() {
        return treatment;
    }

    public final void setTreatment(int treatment) {
        this.treatment = treatment;
    }

    public class State {
        private IllnessType illnessName;
        private int severity;

        public final IllnessType getIllnessName() {
            return illnessName;
        }

        public final void setIllnessName(IllnessType illnessName) {
            this.illnessName = illnessName;
        }

        public final int getSeverity() {
            return severity;
        }

        public final void setSeverity(int severity) {
            this.severity = severity;
        }
    }
}
