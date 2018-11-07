package server.api.v2;

import com.google.common.collect.ImmutableSet;
import java.util.*;

public class Checker {
    private final static Set PATIENT_KEYSET = ImmutableSet.of("name", "surname", "age", "phone", "address_home", "address_hospital");
    private final static Set MEDIC_KEYSET = ImmutableSet.of("name", "surname", "age", "phone", "address", "specialization");
    private final static Set LOGIN_KEYSET = ImmutableSet.of("email", "password", "email_notify", "sms_notify");
    private final static Set POST_WEIGHT_KEYSET = ImmutableSet.of("date", "weight");
    private final static Set PUT_WEIGHT_KEYSET = ImmutableSet.of("weight");
    private final static Set TASK_KEYSET = ImmutableSet.of("patient_id", "date", "category", "description", "starting_program");
    private final static Set PUT_MEDIC_TASK_KEYSET = ImmutableSet.of("date", "category", "description", "starting_program");
    private final static Set PUT_PATIENT_TASK_KEYSET = ImmutableSet.of("executed");
    private final static Set POST_INITIAL_DATA_KEYSET = ImmutableSet.of("pregnancy_start_date", "weight", "height", "bmi", "twin");
    private final static Set PUT_INITIAL_DATA_KEYSET = ImmutableSet.of("twin");
    private final static Set PATIENT_MESSAGE_KEYSET = ImmutableSet.of("medic_id", "timedate", "subject", "message");
    private final static Set MEDIC_MESSAGE_KEYSET = ImmutableSet.of("patient_id", "timedate", "subject", "message");
    private final static Set PATIENT_ADD_MEDIC = ImmutableSet.of("medic_id");
    private final static Set MEDIC_ADD_PATIENT = ImmutableSet.of("patient_id");
    private final static Set SET_MESSAGE_AS_READ = ImmutableSet.of("read");

    public static boolean patientMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(PATIENT_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PATIENT_KEYSET) || !((String)map.get("phone")).matches(Regex.PHONE_FORMAT))
            return false;

        return true;
    }

    public static boolean medicMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(MEDIC_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(MEDIC_KEYSET) || !((String) map.get("phone")).matches(Regex.PHONE_FORMAT))
            return false;

        return true;
    }

    public static boolean loginDataMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(LOGIN_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(LOGIN_KEYSET) || !((String) map.get("email")).matches(Regex.EMAIL_REGEX))
            return false;

        return true;
    }

    public static boolean postWeightMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(POST_WEIGHT_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(POST_WEIGHT_KEYSET) || !((String) map.get("date")).matches(Regex.DATE_REGEX))
            return false;

        return true;
    }

    public static boolean putWeightMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(PUT_WEIGHT_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PUT_WEIGHT_KEYSET))
            return false;

        return true;
    }

    public static boolean taskMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(TASK_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(TASK_KEYSET) || !((String) map.get("date")).matches(Regex.DATE_REGEX))
            return false;

        return true;
    }

    public static boolean putPatientTaskMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(PUT_PATIENT_TASK_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PUT_PATIENT_TASK_KEYSET))
            return false;

        return true;
    }

    public static boolean putMedicTaskMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(PUT_MEDIC_TASK_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PUT_MEDIC_TASK_KEYSET) || !((String) map.get("date")).matches(Regex.DATE_REGEX))
            return false;

        return true;
    }

    public static boolean postPatientInitialDataMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(POST_INITIAL_DATA_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(POST_INITIAL_DATA_KEYSET) || !((String) map.get("pregnancy_start_date")).matches(Regex.DATE_REGEX))
            return false;

        return true;
    }

    public static boolean putPatientInitialDataMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(PUT_INITIAL_DATA_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PUT_INITIAL_DATA_KEYSET))
            return false;

        return true;
    }

    public static boolean patientMessageMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(PATIENT_MESSAGE_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PATIENT_MESSAGE_KEYSET) || !((String) map.get("timedate")).matches(Regex.TIMEDATE_REGEX))
            return false;

        return true;
    }

    public static boolean medicMessageMapValidation(Map<String, ?> map) {
        map.keySet().retainAll(MEDIC_MESSAGE_KEYSET);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(MEDIC_MESSAGE_KEYSET) || !((String) map.get("timedate")).matches(Regex.TIMEDATE_REGEX))
            return false;

        return true;
    }

    public static boolean patientAddMedic(Map<String, ?> map) {
        map.keySet().retainAll(PATIENT_ADD_MEDIC);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(PATIENT_ADD_MEDIC))
            return false;

        return true;
    }

    public static boolean medicAddPatient(Map<String, ?> map) {
        map.keySet().retainAll(MEDIC_ADD_PATIENT);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(MEDIC_ADD_PATIENT))
            return false;

        return true;
    }

    public static boolean setMessageAsRead(Map<String, ?> map) {
        map.keySet().retainAll(SET_MESSAGE_AS_READ);
        Set<String> keys = map.keySet();

        if(!keys.containsAll(SET_MESSAGE_AS_READ) || !((Boolean) map.get("read")))
            return false;

        return true;
    }
}
