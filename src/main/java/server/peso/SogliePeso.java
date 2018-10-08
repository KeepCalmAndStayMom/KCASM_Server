package server.peso;

import java.util.ArrayList;

public class SogliePeso {
    private static final int    DAY_INC1 = 93,
                                DAY_INC2 = 186,
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

    public static final ArrayList<Float>    sott_gem_min = createList(SOTT_GEM_MIN),
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


    public static ArrayList<Float> createList(int value) {
        int i;
        float j;
        float inc1 = (value/4)/DAY_INC1, inc2 = ((value/4)*3)/DAY_INC2;

        /*
        Da sistemare i valori incrementali in quanto troppo piccoli per essere utilizzati dai float

         */
        System.out.println(value);
        System.out.println(value/4);
        System.out.println(value/4/DAY_INC1);

        ArrayList<Float> list = new ArrayList<>();

        for(i=0, j=0; i<DAY_INC1; i++, j+=inc1)
            list.add(j);

        for(i=0, j=0; i<DAY_INC2; i++, j+=inc2)
            list.add(j);

        return list;
    }
}
