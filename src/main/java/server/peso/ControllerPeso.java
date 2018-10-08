package server.peso;

import server.database.UserInitialDateDB;

import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Map;

public class ControllerPeso {

    private ArrayList<Float> list_min, list_max;

    public ControllerPeso(int homestation_id){

        Map<String, Object> map = UserInitialDateDB.getUserInitialDate(homestation_id);

        boolean gemelli = Boolean.valueOf(String.valueOf(map.get("gemelli")));
        BMI bmi = getBMI(String.valueOf(map.get("BMI")));
        System.out.println(bmi);

        list_min = getListSogliaMin(bmi, gemelli);
        list_max = getListSogliaMax(bmi, gemelli);

        System.out.println(list_min);
        System.out.println(list_max);
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
