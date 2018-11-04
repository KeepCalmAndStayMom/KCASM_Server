package server.api.v2;

import com.google.common.collect.ImmutableSet;

import java.util.*;

public class Checker {
    private final static Set PATIENT_KEYSET = ImmutableSet.of("name", "surname", "age", "phone", "address_home", "address_hospital");
    private final static Set MEDIC_KEYSET = ImmutableSet.of("name", "surname", "age", "phone", "specialization");
    private final static Set PATIENT_LOGIN_KEYSET = ImmutableSet.of("email", "password", "patient_id", "email_notify", "sms_notify");
    private final static Set MEDIC_LOGIN_KEYSET = ImmutableSet.of("email", "password", "medic_id", "email_notify", "sms_notify");
    private final static Set WEIGHT_KEYSET = ImmutableSet.of("date", "weight");
    private final static Set TASK_KEYSET = ImmutableSet.of("patient_id", "medic_id", "date", "category", "description", "starting_program", "executed");
    private final static Set INITIAL_DATA_KEYSET = ImmutableSet.of("patient_id", "pregnancy_start", "weight", "height", "bmi", "twin");
    private final static Set MESSAGE_KEYSET = ImmutableSet.of("medic_id", "patient_id", "timedate", "subject", "message", "medic_sender");

    public static boolean patientMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PATIENT_KEYSET) || !((String)map.get("phone")).matches(Regex.PHONE_FORMAT))
            return false;

        return true;
    }

    public static boolean medicMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(MEDIC_KEYSET) || !((String) map.get("phone")).matches(Regex.PHONE_FORMAT))
            return false;

        return true;
    }

    public static boolean patientLoginDataMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PATIENT_LOGIN_KEYSET) || keys.contains("medic_id") || !((String) map.get("email")).matches(Regex.EMAIL_REGEX))
            return false;

        return true;
    }

    public static boolean medicLoginDataMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(MEDIC_LOGIN_KEYSET) || keys.contains("patient_id") || !((String) map.get("email")).matches(Regex.EMAIL_REGEX))
            return false;

        return true;
    }

    public static boolean weightMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(WEIGHT_KEYSET) || !((String) map.get("date")).matches(Regex.DATE_REGEX))
            return false;

        return true;
    }

    public static boolean taskMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(TASK_KEYSET) || !((String) map.get("date")).matches(Regex.DATE_REGEX))
            return false;

        return true;
    }

    public static boolean patientInitialDataMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(INITIAL_DATA_KEYSET) || !((String) map.get("pregnancy_start")).matches(Regex.DATE_REGEX))
            return false;

        return true;
    }

    public static boolean messageMapValidation(Map<String, ?> map) {
        Set<String> keys = map.keySet();

        if(!keys.containsAll(MESSAGE_KEYSET) || !((String) map.get("timedate")).matches(Regex.TIMEDATE_REGEX))
            return false;

        return true;
    }
}
