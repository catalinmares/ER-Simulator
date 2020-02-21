package entities.enums;
/**
 * IMMEDIATE > URGENT > LESS_URGENT > NON_URGENT.
 * NON_URGENT means it will not enter the emergency flows
 *
 * [Part of the homework's skeleton]
 */

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public enum Urgency {
    IMMEDIATE(5),
    URGENT(4),
    LESS_URGENT(3),
    NON_URGENT(2),
    NOT_DETERMINED(1);

    private int value;

    Urgency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
