package org.idey.algo.constructor;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.idey.algo.rule.ExceptionLoggingRule;
import org.idey.algo.util.AssertJ;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@RunWith(JUnitParamsRunner.class)
public class ConstructorUtilTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();


    @Test
    @Parameters(method = "failureConstructorTest")
    public <T> void testPrivateConstructor(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        expectedException.expect(InvocationTargetException.class);
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    private Object[] failureConstructorTest() {
        return new Object[]{
                new Object[]{AssertJ.class},
        };
    }
}
