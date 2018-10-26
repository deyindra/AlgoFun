package org.idey.algo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by i.dey on 2/10/17.
 */
public class Main {
    public static void main(String[] args) {
        Set<Marker> set = new HashSet<>();
        set.addAll(Arrays.asList(Test1.TEST1, Test2.TEST3));
        System.out.println(set.contains(Test1.TEST1) && set.contains(Test2.TEST3));
        System.out.println(String.format("%03d",1));
    }


    private interface Marker{

    }

    private enum Test1 implements Marker{
        TEST1, TEST2
    }

    private enum Test2 implements Marker{
        TEST3, TEST4
    }

    private enum Test3 implements Marker{
        TEST5, TEST6
    }

}
