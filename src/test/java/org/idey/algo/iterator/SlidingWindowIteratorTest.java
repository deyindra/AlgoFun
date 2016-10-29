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
import java.util.List;
import java.util.NoSuchElementException;

@RunWith(JUnitParamsRunner.class)
public class SlidingWindowIteratorTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Parameters(method="successIteratorTestParam")
    public <T> void successIteratorTest(Iterator<T> iterator, int size, int step,
                                        Iterator<List<T>> expectedIterator){
        Iterator<List<T>> slidingWindowIterator = new SlidingWindowIterator<>(iterator, size, step);
        Assert.assertTrue(slidingWindowIterator.hasNext()==expectedIterator.hasNext());
        while (slidingWindowIterator.hasNext()){
            Assert.assertEquals(slidingWindowIterator.next(), expectedIterator.next());
        }
        Assert.assertTrue(slidingWindowIterator.hasNext()==expectedIterator.hasNext());
    }

    private Object[] successIteratorTestParam() {
        return new Object[]{
                new Object[]{Arrays.asList(1,2,3,4,5,6).iterator(),1,1,
                Collections.unmodifiableList(Arrays.asList(Collections.singletonList(1),
                        Collections.singletonList(2),Collections.singletonList(3),
                        Collections.singletonList(4), Collections.singletonList(5),
                        Collections.singletonList(6))).iterator()},

                new Object[]{Arrays.asList(1,2,3,4,5,6).iterator(),2,1,
                        Collections.unmodifiableList(Arrays.asList(Arrays.asList(1,2),
                                Arrays.asList(2,3),Arrays.asList(3,4),
                                Arrays.asList(4,5), Arrays.asList(5,6))).iterator()},

                new Object[]{Arrays.asList(1,2,3,4,5,6).iterator(),1,2,
                        Collections.unmodifiableList(Arrays.asList(Collections.singletonList(1),
                                Collections.singletonList(3),
                                Collections.singletonList(5))).iterator()},

                new Object[]{Arrays.asList(1,2,3,4,5,6).iterator(),1,6,
                        Collections.unmodifiableList(Collections.singletonList(Collections.singletonList(1))).iterator()},

                new Object[]{Arrays.asList(1,2,3,4,5,6).iterator(),6,6,
                        Collections.unmodifiableList(Collections.singletonList(Arrays.asList(1,2,3,4,5,6))).iterator()},

                new Object[]{Arrays.asList(1,2,3,4,5,6).iterator(),7,1,
                        Collections.unmodifiableList(Collections.singletonList(Arrays.asList(1,2,3,4,5,6))).iterator()},

                new Object[]{Arrays.asList(1,2,3,4,5,6).iterator(),5,1,
                        Collections.unmodifiableList(Arrays.asList(Arrays.asList(1,2,3,4,5),
                                Arrays.asList(2,3,4,5,6))).iterator()},


        };
    }

    @Test
    @Parameters(method="failureIteratorTestParam")
    public <T extends Comparable<T>> void failureIteratorTest(Iterator<T> iterator, int size, int step){
        expectedException.expect(IllegalArgumentException.class);
        new SlidingWindowIterator<>(iterator, size, step);
    }

    private Object[] failureIteratorTestParam() {
        return new Object[]{
          new Object[]{null, 3, 1},
          new Object[]{Arrays.asList(1,2,3).iterator(), 3, -1},
          new Object[]{Arrays.asList(1,2,3).iterator(), 3, 0},
          new Object[]{Arrays.asList(1,2,3).iterator(), -3, 5},
          new Object[]{Arrays.asList(1,2,3).iterator(), 0, 5},

        };
    }


    @Test
    public void testNoSuchElementException(){
        expectedException.expect(NoSuchElementException.class);
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(Arrays.asList(1,2,3,4,5).iterator(),5,1);
        slidingWindowIterator.next();
        slidingWindowIterator.next();
    }

    @Test
    public void testUnsupportedOperationExceptionTest(){
        expectedException.expect(UnsupportedOperationException.class);
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(Arrays.asList(1,2,3,4,5).iterator(),5,1);
        slidingWindowIterator.remove();
    }


}