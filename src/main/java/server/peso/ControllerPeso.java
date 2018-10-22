package server.peso;

import server.database.UserInitialDateDB;
import server.database2.PatientDB;
import server.database2.PatientInitialDB;
import server.net_influence.NetSmile;
import server.net_influence.NetSmileRetePeso;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllerPeso {

    static ArrayList<Double> list_min, list_max;
    private static LocalDate start_date;
    private static double start_peso;
    private static boolean gemelli;
    private static BMI bmi;
    private static HashMap<Integer, LocalDate> avvisi;
    private static StrategyInterfacePeso strategyPeso;
    private static NetSmile net;

    static {
        avvisi = new HashMap<>();
        strategyPeso = new StrategyPeso1();
        net = new NetSmileRetePeso();
    }

    static LocalDate getLastAvviso(int homestation_id)
    {
        if(avvisi.get(homestation_id)!=null)
            return avvisi.get(homestation_id);
        return null;
    }

    public static void addLastAvviso(int homestation_id, LocalDate date)
    {
        avvisi.put(homestation_id, date);
    }

    public static void removeID(int homestation_id)
    {
        avvisi.remove(homestation_id);
    }

    public static void startcheck(int homestation_id, LocalDate actual_date, double actual_peso) {

        setUserInitialData(homestation_id);

        list_min = getListSogliaMin(bmi, gemelli);
        list_max = getListSogliaMax(bmi, gemelli);

        Map<String, Object> map = new HashMap<>();
        map.put("homestation_id", homestation_id);
        map.put("start_date", start_date);
        map.put("actual_date", actual_date);
        map.put("start_peso", start_peso);
        map.put("actual_peso", actual_peso);

        Map<String, Object> testEvidence = strategyPeso.getMapOfStrategy(map);

        net.setAllEvidence(testEvidence);
        net.runNet();
        String result = net.getResultUtility();

        System.out.println("result net: "+ result);
    }
    private static void setUserInitialData(int homestation_id)
    {
        Map<String, Object> map = PatientInitialDB.Select(homestation_id);
        assert map != null;
        start_date = LocalDate.parse(String.valueOf(map.get("pregnancy_start_date")));
        start_peso = (Double) map.get("weight");
        gemelli = (Boolean) map.get("twin");
        bmi = getBMI(String.valueOf(map.get("bmi")));
    }

    private static BMI getBMI(String bmi)
    {
        switch (bmi)
        {
            case "sottopeso":
                return BMI.Sottopeso;
            case "normopeso":
                return BMI.Normopeso;
            case "sovrappeso":
                return BMI.Sovrappeso;
            case "obeso":
                return BMI.Obeso;
        }
        return null;
    }

    private static ArrayList<Double> getListSogliaMin(BMI bmi, boolean gemelli)
    {
        if(gemelli)
        {
            switch (bmi) {
                case Sottopeso:
                    return SogliePeso.sott_gem_min;
                case Normopeso:
                    return SogliePeso.norm_gem_min;
                case Sovrappeso:
                    return SogliePeso.sov_gem_min;
                case Obeso:
                    return SogliePeso.ob_gem_min;
            }
        }
        else
        {
            switch (bmi) {
                case Sottopeso:
                    return SogliePeso.sott_min;
                case Normopeso:
                    return SogliePeso.norm_min;
                case Sovrappeso:
                    return SogliePeso.sov_min;
                case Obeso:
                    return SogliePeso.ob_min;
            }
        }
        return null;
    }

    private static ArrayList<Double> getListSogliaMax(BMI bmi, boolean gemelli)
    {
        if(gemelli)
        {
            switch (bmi) {
                case Sottopeso:
                    return SogliePeso.sott_gem_max;
                case Normopeso:
                    return SogliePeso.norm_gem_max;
                case Sovrappeso:
                    return SogliePeso.sov_gem_max;
                case Obeso:
                    return SogliePeso.ob_gem_max;
            }
        }
        else
        {
            switch (bmi) {
                case Sottopeso:
                    return SogliePeso.sott_max;
                case Normopeso:
                    return SogliePeso.norm_max;
                case Sovrappeso:
                    return SogliePeso.sov_max;
                case Obeso:
                    return SogliePeso.ob_max;
            }
        }
        return null;
    }
}
