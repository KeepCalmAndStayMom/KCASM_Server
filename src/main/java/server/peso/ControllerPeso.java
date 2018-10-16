package server.peso;

import server.database.AttivitaDB;
import server.database.DietaDB;
import server.database.PesoDB;
import server.database.UserInitialDateDB;
import server.net_influence.NetSmile;

import java.io.ObjectStreamClass;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerPeso {

    public static ArrayList<Float> list_min, list_max;
    public static LocalDate start_date;
    public static float start_peso;
    public static boolean gemelli;
    public static BMI bmi;
    public static HashMap<Integer, LocalDate> avvisi;
    public static StrategyInterfacePeso strategyPeso;

    static {
        avvisi = new HashMap<>();
        strategyPeso = new StrategyPeso1();
    }

    public static LocalDate getLastAvviso(int homestation_id)
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

        int checkpeso = DataFilter.checkPeso(start_date, actual_date, start_peso, actual_peso);
        int dayPesoOut = DataFilter.dayPesoOut(homestation_id, start_date, start_peso);
        String dieta = DataFilter.typeDieta(homestation_id);
        int weekDieta = DataFilter.weekOfDieta(homestation_id, actual_date);
        String attivita = DataFilter.typeAttivita(homestation_id);
        int weekAttivita = DataFilter.weekOfAttivita(homestation_id, actual_date);
        int weekAvviso = DataFilter.weekOfLastAvviso(getLastAvviso(homestation_id), actual_date);

        Map<String, Object> testEvidence = strategyPeso.getMapOfStrategy(homestation_id, start_date, actual_date, start_peso, actual_peso);

        NetSmile.clearNet();
        NetSmile.setAllEvidence(testEvidence);
        NetSmile.runNet();

        String result = NetSmile.getResultUtility();

        System.out.println("homestation_id: " + homestation_id);
        System.out.println("lista_min: " + list_min);
        System.out.println("lista_max: " + list_max);
        System.out.println("start_date: " + start_date);
        System.out.println("start_peso: " + start_peso);
        System.out.println("BMI: " + bmi);
        System.out.println("actual_date: " + actual_date);
        System.out.println("actual_peso: " + actual_peso);
        System.out.println("checkpeso: " + checkpeso);
        System.out.println("daypeso: " + dayPesoOut);
        System.out.println("dieta: " + dieta);
        System.out.println("weekDieta: " +weekDieta);
        System.out.println("attivita: " + attivita);
        System.out.println("weekattivita: " + weekAttivita);
        System.out.println("weekAvviso: " + weekAvviso);
        System.out.println("result net: " + result);
        System.out.println("\n\n");

    }
    public static void setUserInitialData(int homestation_id)
    {
        Map<String, Object> map = UserInitialDateDB.getUserInitialDate(homestation_id);
        start_date = LocalDate.parse(String.valueOf(map.get("data_inizio_gravidanza")));
        start_peso = Float.valueOf(String.valueOf(map.get("peso")));
        gemelli = Boolean.valueOf(String.valueOf(map.get("gemelli")));
        bmi = getBMI(String.valueOf(map.get("BMI")));
    }

    public static BMI getBMI(String bmi)
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

    public static ArrayList<Float> getListSogliaMin(BMI bmi, boolean gemelli)
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

    public static ArrayList<Float> getListSogliaMax(BMI bmi, boolean gemelli)
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
