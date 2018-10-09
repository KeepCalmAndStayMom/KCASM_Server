package server.peso;

import server.database.PesoDB;
import server.database.UserInitialDateDB;

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

    private static ArrayList<Float> list_min, list_max;
    private LocalDate start_date;
    private float start_peso;
    private boolean gemelli;
    private BMI bmi;

    public ControllerPeso(int homestation_id){

        getUserData(UserInitialDateDB.getUserInitialDate(homestation_id));

        list_min = getListSogliaMin(bmi, gemelli);
        list_max = getListSogliaMax(bmi, gemelli);

        Map<String, Float> test_pesi = PesoDB.getPeso(homestation_id);

        Map<String, Object> test_dieta = new HashMap<>();
        Map<String, Object> test_attivita = new HashMap<>();
        List<Map<String, Object>> list_test_dieta = new ArrayList<>();
        List<Map<String, Object>> list_test_attivita = new ArrayList<>();

        test_dieta.put("data", "2018-09-01");
        test_dieta.put("categoria", "Ingrassante");
        list_test_dieta.add(test_dieta);
        test_dieta.clear();
        test_dieta.put("data", "2018-10-01");
        test_dieta.put("categoria", "Dimagrante");
        list_test_dieta.add(test_dieta);

        test_attivita.put("data", "2018-10-01");
        test_attivita.put("categoria", "Nessuna");
        list_test_attivita.add(test_attivita);
        test_attivita.clear();
        test_attivita.put("data", "2018-08-05");
        test_attivita.put("categoria", "Relax");
        list_test_attivita.add(test_attivita);

        System.out.println(DataFilter.dayPesoOut(start_date, start_peso, test_pesi));

        System.out.println(DataFilter.typeDietaOrAttivita(list_test_dieta));
        System.out.println(DataFilter.weekOfDietaOrAttivita(list_test_dieta));

        System.out.println(DataFilter.typeDietaOrAttivita(list_test_attivita));
        System.out.println(DataFilter.weekOfDietaOrAttivita(list_test_attivita));
    }

    public void getUserData(Map<String, Object> map)
    {
        start_date = LocalDate.parse(String.valueOf(map.get("data_inizio_gravidanza")));
        start_peso = Float.valueOf(String.valueOf(map.get("peso")));
        gemelli = Boolean.valueOf(String.valueOf(map.get("gemelli")));
        bmi = getBMI(String.valueOf(map.get("BMI")));
    }

    public static int checkPeso(int week, float start_peso, float actual_peso)
    {
        if(actual_peso < (start_peso + list_min.get(week)))
            return -1;

        if(actual_peso > (start_peso + list_max.get(week)))
            return 1;

        return 0;
    }

    public static int getWeekOfPregnancy(LocalDate start_date, LocalDate date)
    {
        return (int) ChronoUnit.WEEKS.between(start_date, date);
    }

    public BMI getBMI(String bmi)
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

    public ArrayList<Float> getListSogliaMin(BMI bmi, boolean gemelli)
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

    public ArrayList<Float> getListSogliaMax(BMI bmi, boolean gemelli)
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
