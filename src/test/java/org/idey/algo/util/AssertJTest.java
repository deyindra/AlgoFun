package org.idey.algo.util;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.idey.algo.rule.ExceptionLoggingRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;


@RunWith(JUnitParamsRunner.class)
public class AssertJTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();


    @Test
    @Parameters(method = "successAssertTestArgs")
    public <T> void successAssertTest(Predicate<T> predicate, T value,
                                String message, Object[] args){
        AssertJ.assertTrue(predicate,value,message,args);
    }

    private Object[] successAssertTestArgs() {
        return new Object[]{
                new Object[]{nonZeroPredicate(), 2, "Error", null},
                new Object[]{nonZeroPredicate(), 2, "Error %d", new Object[]{2}},
        };
    }


    @Test
    @Parameters(method = "successNotNullTestArgs")
    public <T> void successNotNullTest(T value,
                                String message){
        AssertJ.notNull(value,message);
    }

    private Object[] successNotNullTestArgs() {
        return new Object[]{
                new Object[]{2, "Error"},
                new Object[]{2, "Error %d"},
        };
    }

    private Predicate<Integer> nonZeroPredicate(){
        return p->p!=0;
    }

    @Test
    @Parameters(method = "failedAssertTestArgs")
    public <T> void failedAssertTest(Predicate<T> predicate, T value,
                                      String message, Object[] args){
        expectedException.expect(IllegalArgumentException.class);
        AssertJ.assertTrue(predicate,value,message,args);
    }

    private Object[] failedAssertTestArgs() {
        return new Object[]{
                new Object[]{nonZeroPredicate(), 0, null, null},
                new Object[]{nonZeroPredicate(), 0, "", null},
                new Object[]{nonZeroPredicate(), 0, "Error", null},
                new Object[]{nonZeroPredicate(), 0, "Error %d", new Object[]{0}},

        };
    }


}