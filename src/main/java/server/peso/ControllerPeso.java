package server.peso;

import server.MainServer;
import server.database.UserInitialDateDB;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllerPeso {

    static ArrayList<Float> list_min, list_max;
    private static LocalDate start_date;
    private static float start_peso;
    private static boolean gemelli;
    private static BMI bmi;
    private static HashMap<Integer, LocalDate> avvisi;
    private static StrategyInterfacePeso strategyPeso;

    static {
        avvisi = new HashMap<>();
        strategyPeso = new StrategyPeso1();
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

    public static void startcheck(int homestation_id, LocalDate actual_date, float actual_peso) {

        setUserInitialData(homestation_id);

        list_min = getListSogliaMin(bmi, gemelli);
        list_max = getListSogliaMax(bmi, gemelli);

        Map<String, Object> testEvidence = strategyPeso.getMapOfStrategy(homestation_id, start_date, actual_date, start_peso, actual_peso);

        MainServer.net.setAllEvidence(testEvidence);
        MainServer.net.runNet();
        String result = MainServer.net.getResultUtility();

        System.out.println("result net: "+ result);
    }
    private static void setUserInitialData(int homestation_id)
    {
        Map<String, Object> map = UserInitialDateDB.getUserInitialDate(homestation_id);
        assert map != null;
        start_date = LocalDate.parse(String.valueOf(map.get("data_inizio_gravidanza")));
        start_peso = Float.valueOf(String.valueOf(map.get("peso")));
        gemelli = Boolean.valueOf(String.valueOf(map.get("gemelli")));
        bmi = getBMI(String.valueOf(map.get("BMI")));
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

    private static ArrayList<Float> getListSogliaMin(BMI bmi, boolean gemelli)
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

    private static ArrayList<Float> getListSogliaMax(BMI bmi, boolean gemelli)
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
