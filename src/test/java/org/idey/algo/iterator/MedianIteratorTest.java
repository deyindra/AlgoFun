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
import java.util.Iterator;
import java.util.NoSuchElementException;

@RunWith(JUnitParamsRunner.class)
public class MedianIteratorTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Parameters(method = "successIteratorTestParam")
    public <T extends Comparable<T>> void successMedianTest(T[] array, int size, Iterator<T> expecteeIterator) {
        MedianIterator<T> medianIterator = new MedianIterator<>(array, size);
        Assert.assertTrue(medianIterator.hasNext() == expecteeIterator.hasNext());
        while (medianIterator.hasNext()){
            Assert.assertEquals(medianIterator.next(), expecteeIterator.next());
        }
        Assert.assertTrue(medianIterator.hasNext() == expecteeIterator.hasNext());
    }

    private Object[] successIteratorTestParam() {
        return new Object[]{
                new Object[]{new Integer[]{4,1,3,2,6}, 2, Arrays.asList(1,1,2,2).iterator()},
                new Object[]{new Integer[]{4,1,3,2,6}, 3, Arrays.asList(3,2,3).iterator()},
                new Object[]{new Integer[]{4,1,3,2,6}, 1, Arrays.asList(4,1,3,2,6).iterator()},
        };
    }

    @Test
    @Parameters(method = "failedIteratorTestParam")
    public <T extends Comparable<T>> void failedMedianTest(T[] array, int size){
        expectedException.expect(IllegalArgumentException.class);
        new MedianIterator<>(array, size);
    }


    private Object[] failedIteratorTestParam() {
        return new Object[]{
                new Object[]{null, 2},
                new Object[]{new Integer[]{}, 3},
                new Object[]{new Integer[]{4,1,3,2,6}, 0},
                new Object[]{new Integer[]{4,1,3,2,6}, -1},
                new Object[]{new Integer[]{4,1,3,2,6}, 6},
        };
    }

    @Test
    public void endOfIteratorTest(){
        expectedException.expect(NoSuchElementException.class);
        Iterator<Integer> medianIterator = new MedianIterator<>(new Integer[]{1,4,3},3);
        medianIterator.next();
        medianIterator.next();
    }

    @Test
    public void removalIteratorTest(){
        expectedException.expect(UnsupportedOperationException.class);
        Iterator<Integer> medianIterator = new MedianIterator<>(new Integer[]{1,4,3},3);
        medianIterator.remove();
    }
}