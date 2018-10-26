package org.idey.algo.iterator;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.idey.algo.rule.ExceptionLoggingRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

@RunWith(JUnitParamsRunner.class)
public class TransformationIteratorTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Parameters(method="successIteratorTestParam")
    public <I,R> void successIteratorTest(Iterator<I> iterator, Function<I,R> function,
                                          Iterator<R> expectedOutput){
        Iterator<R> transformIterator = new TransformationIterator<>(iterator,function);
        Assert.assertTrue(transformIterator.hasNext()==expectedOutput.hasNext());
        while (transformIterator.hasNext()){
            Assert.assertEquals(transformIterator.next(),expectedOutput.next());
        }
        Assert.assertTrue(transformIterator.hasNext()==expectedOutput.hasNext());
    }

    private Object[] successIteratorTestParam() {
        return new Object[]{
                new Object[]{Arrays.asList(1,2,3).iterator(),
                        getDoubleInt(),Arrays.asList(2,4,6).iterator()},
                new Object[]{Collections.singleton(1).iterator(),
                        getDoubleInt(),Collections.singleton(2).iterator()},
                new Object[]{Collections.emptyList().iterator(),
                        getDoubleInt(),Collections.emptyList().iterator()}
        };
    }

    @Test
    @Parameters(method="failureWithNullArgsIteratorTestParam")
    public <I,R> void failureWithNullArgsIteratorTest(Iterator<I> iterator, Function<I,R> function){
        expectedException.expect(IllegalArgumentException.class);
        Iterator<R> transformIterator = new TransformationIterator<>(iterator,function);
    }

    private Object[] failureWithNullArgsIteratorTestParam() {
        return new Object[]{
                new Object[]{null,
                        getDoubleInt()},
                new Object[]{Collections.singleton(1).iterator(),
                        null},
                new Object[]{null,null}
        };
    }


    @Test
    public void failureNoSuchElementExceptionTest(){
        expectedException.expect(NoSuchElementException.class);
        Iterator<Integer> iterator = new TransformationIterator<>(Collections.singleton(1).iterator(), getDoubleInt());
        iterator.next();
        iterator.next();
    }

    @Test
    public void failureUnknownOperationExceptionTest(){
        expectedException.expect(UnsupportedOperationException.class);
        Iterator<Integer> iterator = new TransformationIterator<>(Collections.singleton(1).iterator(), getDoubleInt());
        iterator.remove();
    }

    public Function<Integer,Integer> getDoubleInt(){
        return i->i*2;
    }
}