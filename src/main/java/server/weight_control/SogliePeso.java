package server.weight_control;

import java.util.ArrayList;
import java.util.List;

public class SogliePeso {
    private static final double     WEEK_INC1 = 15,
                                    WEEK_INC2 = 30,
                                    SOTT_GEM_MIN = 18,
                                    SOTT_GEM_MAX = 26,
                                    SOTT_MIN = 12,
                                    SOTT_MAX = 18,
                                    NORM_GEM_MIN = 17,
                                    NORM_GEM_MAX = 24,
                                    NORM_MIN = 11,
                                    NORM_MAX = 16,
                                    SOV_GEM_MIN = 14,
                                    SOV_GEM_MAX = 23,
                                    SOV_MIN = 7,
                                    SOV_MAX = 11,
                                    OB_GEM_MIN = 11,
                                    OB_GEM_MAX = 19,
                                    OB_MIN = 4,
                                    OB_MAX = 7;


    private static List<Double> createList(double value) {
        int i;
        double j=0;
        double inc1 = (value/4)/WEEK_INC1, inc2 = ((value/4)*3)/WEEK_INC2;

        ArrayList<Double> list = new ArrayList<>();

        for(i=0; i<WEEK_INC1; i++, j+=inc1)
            list.add(j);

        for(i=0; i<WEEK_INC2; i++, j+=inc2)
            list.add(j);

        return list;
    }

    public static List<Double> getListSogliaMin(String bmi, boolean gemelli)
    {
        if(gemelli)
        {
            switch (bmi) {
                case "sottopeso":
                    return createList(SOTT_GEM_MIN);
                case "normopeso":
                    return createList(NORM_GEM_MIN);
                case "sovrappeso":
                    return createList(SOV_GEM_MIN);
                case "obeso":
                    return createList(OB_GEM_MIN);
            }
        }
        else
        {
            switch (bmi) {
                case "sottopeso":
                    return createList(SOTT_MIN);
                case "normopeso":
                    return createList(NORM_MIN);
                case "sovrappeso":
                    return createList(SOV_MIN);
                case "obeso":
                    return createList(OB_MIN);
            }
        }
        return null;
    }

    public static List<Double> getListSogliaMax(String bmi, boolean gemelli)
    {
        if(gemelli)
        {
            switch (bmi) {
                case "sottopeso":
                    return createList(SOTT_GEM_MAX);
                case "normopeso":
                    return createList(NORM_GEM_MAX);
                case "sovrappeso":
                    return createList(SOV_GEM_MAX);
                case "obeso":
                    return createList(OB_GEM_MAX);
            }
        }
        else
        {
            switch (bmi) {
                case "sottopeso":
                    return createList(SOTT_MAX);
                case "normopeso":
                    return createList(NORM_MAX);
                case "sovrappeso":
                    return createList(SOV_MAX);
                case "obeso":
                    return createList(OB_MAX);
            }
        }
        return null;
    }
}
