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

    private static ArrayList<Float> list_min, list_max;
    private LocalDate start_date;
    private float start_peso;
    private boolean gemelli;
    private BMI bmi;

    public ControllerPeso(int homestation_id, String date_actual, float peso_actual) {

        getUserData(UserInitialDateDB.getUserInitialDate(homestation_id));

        list_min = getListSogliaMin(bmi, gemelli);
        list_max = getListSogliaMax(bmi, gemelli);

        Map<String, Float> test_pesi = PesoDB.getPeso(homestation_id);
        List<Map<String, Object>> list_test_dieta = DietaDB.getDietaPrograms(homestation_id);
        List<Map<String, Object>> list_test_attivita = AttivitaDB.getAttivitaPrograms(homestation_id);

        int week = getWeekOfPregnancy(start_date, LocalDate.parse(date_actual));
        int checkpeso = checkPeso(week, start_peso, peso_actual);
        int dayPesoOut = DataFilter.dayPesoOut(start_date, start_peso, test_pesi);
        String dieta = DataFilter.typeDietaOrAttivita(list_test_dieta);
        int weekDieta = DataFilter.weekOfDietaOrAttivita(list_test_dieta, LocalDate.parse(date_actual));
        String attivita = DataFilter.typeDietaOrAttivita(list_test_attivita);
        int weekAttivita = DataFilter.weekOfDietaOrAttivita(list_test_attivita, LocalDate.parse(date_actual));
        int weekAvviso = DataFilter.weekOfLastAvviso(NetSmile.getLastAvviso(homestation_id), LocalDate.parse(date_actual));

        Map<String, Object> testEvidence = new HashMap<>();
        testEvidence.put("Peso", checkpeso);
        testEvidence.put("Dieta", dieta);
        testEvidence.put("Attivita", attivita);
        testEvidence.put("Tempo_Peso", dayPesoOut);
        testEvidence.put("Tempo_Dieta", weekDieta);
        testEvidence.put("Tempo_Attivita", weekAttivita);
        testEvidence.put("Ultimo_Avviso", weekAvviso);

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
        System.out.println("actual_date: " + date_actual);
        System.out.println("actual_peso: " + peso_actual);
        System.out.println("week: " + week);
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
