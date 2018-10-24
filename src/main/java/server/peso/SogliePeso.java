package server.peso;

import java.util.ArrayList;

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

    public static final ArrayList<Double>      sott_gem_min = createList(SOTT_GEM_MIN),
                                        sott_gem_max = createList(SOTT_GEM_MAX),
                                        sott_min = createList(SOTT_MIN),
                                        sott_max = createList(SOTT_MAX),
                                        norm_gem_min = createList(NORM_GEM_MIN),
                                        norm_gem_max = createList(NORM_GEM_MAX),
                                        norm_min = createList(NORM_MIN),
                                        norm_max = createList(NORM_MAX),
                                        sov_gem_min = createList(SOV_GEM_MIN),
                                        sov_gem_max = createList(SOV_GEM_MAX),
                                        sov_min = createList(SOV_MIN),
                                        sov_max = createList(SOV_MAX),
                                        ob_gem_min = createList(OB_GEM_MIN),
                                        ob_gem_max = createList(OB_GEM_MAX),
                                        ob_min = createList(OB_MIN),
                                        ob_max = createList(OB_MAX);


    private static ArrayList<Double> createList(double value) {
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
}
