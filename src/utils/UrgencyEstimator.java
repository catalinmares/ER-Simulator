package utils;

import entities.enums.IllnessType;
import entities.enums.Urgency;

import java.util.HashMap;
import java.util.Map;


/**
 * Estimates the urgency based on the patient's illness and how severe the illness is manifested.
 */

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class UrgencyEstimator {

    private static UrgencyEstimator instance;
    private Map<Urgency, HashMap<IllnessType, Integer>> algorithm;

    private static final int IMM_ABD_PAIN = 60;
    private static final int IMM_ALL_REACT = 50;
    private static final int IMM_BROKEN_BONES = 80;
    private static final int IMM_BURNS = 40;
    private static final int IMM_CAR_ACC = 30;
    private static final int IMM_CUTS = 50;
    private static final int IMM_FOOD_POISON = 50;
    private static final int IMM_HEART_ATTACK = 0;
    private static final int IMM_HEART_DISEASE = 40;
    private static final int IMM_HIGH_FEVER = 70;
    private static final int IMM_PNEUMONIA = 80;
    private static final int IMM_SPORT_INJ = 70;
    private static final int IMM_STROKE = 0;

    private static final int URG_ABD_PAIN = 40;
    private static final int URG_ALL_REACT = 30;
    private static final int URG_BROKEN_BONES = 50;
    private static final int URG_BURNS = 20;
    private static final int URG_CAR_ACC = 20;
    private static final int URG_CUTS = 30;
    private static final int URG_HEART_ATTACK = 0;
    private static final int URG_FOOD_POISON = 20;
    private static final int URG_HEART_DISEASE = 20;
    private static final int URG_HIGH_FEVER = 40;
    private static final int URG_PNEUMONIA = 50;
    private static final int URG_SPORT_INJ = 50;
    private static final int URG_STROKE = 0;

    private static final int LU_ABD_PAIN = 10;
    private static final int LU_ALL_REACT = 10;
    private static final int LU_BROKEN_BONES = 20;
    private static final int LU_BURNS = 10;
    private static final int LU_CAR_ACC = 10;
    private static final int LU_CUTS = 10;
    private static final int LU_FOOD_POISON = 0;
    private static final int LU_HEART_ATTACK = 0;
    private static final int LU_HEART_DISEASE = 10;
    private static final int LU_HIGH_FEVER = 0;
    private static final int LU_PNEUMONIA = 10;
    private static final int LU_SPORT_INJ = 20;
    private static final int LU_STROKE = 0;

    private UrgencyEstimator() {
        algorithm = new HashMap<Urgency, HashMap<IllnessType, Integer>>() {
            {
                put(Urgency.IMMEDIATE,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, IMM_ABD_PAIN);
                                put(IllnessType.ALLERGIC_REACTION, IMM_ALL_REACT);
                                put(IllnessType.BROKEN_BONES, IMM_BROKEN_BONES);
                                put(IllnessType.BURNS, IMM_BURNS);
                                put(IllnessType.CAR_ACCIDENT, IMM_CAR_ACC);
                                put(IllnessType.CUTS, IMM_CUTS);
                                put(IllnessType.FOOD_POISONING, IMM_FOOD_POISON);
                                put(IllnessType.HEART_ATTACK, IMM_HEART_ATTACK);
                                put(IllnessType.HEART_DISEASE, IMM_HEART_DISEASE);
                                put(IllnessType.HIGH_FEVER, IMM_HIGH_FEVER);
                                put(IllnessType.PNEUMONIA, IMM_PNEUMONIA);
                                put(IllnessType.SPORT_INJURIES, IMM_SPORT_INJ);
                                put(IllnessType.STROKE, IMM_STROKE);

                            }
                        });

                put(Urgency.URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, URG_ABD_PAIN);
                                put(IllnessType.ALLERGIC_REACTION, URG_ALL_REACT);
                                put(IllnessType.BROKEN_BONES, URG_BROKEN_BONES);
                                put(IllnessType.BURNS, URG_BURNS);
                                put(IllnessType.CAR_ACCIDENT, URG_CAR_ACC);
                                put(IllnessType.CUTS, URG_CUTS);
                                put(IllnessType.HEART_ATTACK, URG_HEART_ATTACK);
                                put(IllnessType.FOOD_POISONING, URG_FOOD_POISON);
                                put(IllnessType.HEART_DISEASE, URG_HEART_DISEASE);
                                put(IllnessType.HIGH_FEVER, URG_HIGH_FEVER);
                                put(IllnessType.PNEUMONIA, URG_PNEUMONIA);
                                put(IllnessType.SPORT_INJURIES, URG_SPORT_INJ);
                                put(IllnessType.STROKE, URG_STROKE);
                            }
                        });

                put(Urgency.LESS_URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, LU_ABD_PAIN);
                                put(IllnessType.ALLERGIC_REACTION, LU_ALL_REACT);
                                put(IllnessType.BROKEN_BONES, LU_BROKEN_BONES);
                                put(IllnessType.BURNS, LU_BURNS);
                                put(IllnessType.CAR_ACCIDENT, LU_CAR_ACC);
                                put(IllnessType.CUTS, LU_CUTS);
                                put(IllnessType.FOOD_POISONING, LU_FOOD_POISON);
                                put(IllnessType.HEART_ATTACK, LU_HEART_ATTACK);
                                put(IllnessType.HEART_DISEASE, LU_HEART_DISEASE);
                                put(IllnessType.HIGH_FEVER, LU_HIGH_FEVER);
                                put(IllnessType.PNEUMONIA, LU_PNEUMONIA);
                                put(IllnessType.SPORT_INJURIES, LU_SPORT_INJ);
                                put(IllnessType.STROKE, LU_STROKE);
                            }
                        });

            }
        };
    }

    public static UrgencyEstimator getInstance() {
        if (instance == null) {
            instance = new UrgencyEstimator();
        }

        return instance;
    }

    //called by doctors and nurses
    public Urgency estimateUrgency(IllnessType illnessType, int severity) {

        if (severity >= algorithm.get(Urgency.IMMEDIATE).get(illnessType)) {
            return Urgency.IMMEDIATE;
        }

        if (severity >= algorithm.get(Urgency.URGENT).get(illnessType)) {
            return Urgency.URGENT;
        }

        if (severity >= algorithm.get(Urgency.LESS_URGENT).get(illnessType)) {
            return Urgency.LESS_URGENT;
        }

        return Urgency.NON_URGENT;
    }
}
