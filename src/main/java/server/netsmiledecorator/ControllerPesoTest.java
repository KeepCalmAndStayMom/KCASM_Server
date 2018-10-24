package server.netsmiledecorator;

import server.database2.PatientInitialDB;
import server.net_influence.NetSmile;
import server.net_influence.NetSmileRetePeso;
import server.peso.BMI;
import server.peso.SogliePeso;
import server.peso.StrategyInterfacePeso;
import server.peso.StrategyPeso1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllerPesoTest {

    static ArrayList<Double> list_min, list_max;
    private static LocalDate start_date;
    private static double start_peso;
    private static boolean gemelli;
    private static BMI bmi;
    private static HashMap<Integer, LocalDate> avvisi;
    private static AbstractNetSmile netSmile;

    public ControllerPesoTest() {
        avvisi = new HashMap<>();

        netSmile = new NetSmile2();
        try {
            FileReader file = new FileReader("src\\main\\resources\\testdatirete.txt");
            BufferedReader buffer = new BufferedReader(file);
            String line = buffer.readLine();
            while(line!=null) {
                System.out.println(line);
                String[] s = line.split(" ");
                netSmile = new NetSmileNode(netSmile, s[0], getEvidenceFilter(s[1]), getRetrieveData(s[2]));
                line = buffer.readLine();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private EvidenceFilterInterface getEvidenceFilter(String s)
    {
        switch (s)
        {
            case "EvidenceFilterPeso": return new EvidenceFilterPeso();
            case "EvidenceFilterDieta": return new EvidenceFilterDieta();
            case "EvidenceFilterAttivita": return new EvidenceFilterAttivita();
            case "EvidenceFilterTempo": return new EvidenceFilterTempo();
        }

        return null;
    }

    private RetrieveDataInterface getRetrieveData(String s)
    {
        switch (s)
        {
            case "RetrieveDataPeso": return new RetrieveDataPeso();
            case "RetrieveDataDieta": return new RetrieveDataDieta();
            case "RetrieveDataAttivita": return new RetrieveDataAttivita();
            case "RetrieveDataWeekDieta": return new RetrieveDataWeekDieta();
            case "RetrieveDataWeekAttivita": return new RetrieveDataWeekAttivita();
            case "RetrieveDataWeekAvviso": return new RetrieveDataWeekAvviso();
            case "RetrieveDataWeekPesoOut": return new RetrieveDataWeekPesoOut();
        }

        return null;
    }

    static LocalDate getLastAvviso(int homestation_id)
    {
        if(avvisi.get(homestation_id)!=null)
            return avvisi.get(homestation_id);
        return null;
    }

    public void addLastAvviso(int homestation_id, LocalDate date)
    {
        avvisi.put(homestation_id, date);
    }

    public void removeID(int homestation_id)
    {
        avvisi.remove(homestation_id);
    }

    public void startcheck(int homestation_id, LocalDate actual_date, double actual_peso) {

        setUserInitialData(homestation_id);

        list_min = getListSogliaMin(bmi, gemelli);
        list_max = getListSogliaMax(bmi, gemelli);

        netSmile.setEvidence(homestation_id, actual_date, actual_peso);
        netSmile.runNet();
        System.out.println(netSmile.getResultUtility());
    }
    private void setUserInitialData(int homestation_id)
    {
        Map<String, Object> map = PatientInitialDB.Select(homestation_id);
        assert map != null;
        start_date = LocalDate.parse(String.valueOf(map.get("pregnancy_start_date")));
        start_peso = (Double) map.get("weight");
        gemelli = (Boolean) map.get("twin");
        bmi = getBMI(String.valueOf(map.get("bmi")));
    }

    private BMI getBMI(String bmi)
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

    private ArrayList<Double> getListSogliaMin(BMI bmi, boolean gemelli)
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

    private ArrayList<Double> getListSogliaMax(BMI bmi, boolean gemelli)
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
