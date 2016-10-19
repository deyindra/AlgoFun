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
import java.util.function.Predicate;

@RunWith(JUnitParamsRunner.class)
public class FilterIteratorTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();


    @Test
    @Parameters(method="successIteratorTestParam")
    public <T> void successIteratorTest(Iterator<T> inputIterator, Predicate<T> predicate,
                                        Iterator<T> finalIterator){
        Iterator<T> filterIterator = new FilterIterator<>(predicate, inputIterator);
        Assert.assertTrue(filterIterator.hasNext()==finalIterator.hasNext());
        while (filterIterator.hasNext()){
            Assert.assertEquals(filterIterator.next(),finalIterator.next());
        }
        Assert.assertTrue(filterIterator.hasNext()==finalIterator.hasNext());
    }

    private Object[] successIteratorTestParam() {
        return new Object[]{
                new Object[]{Arrays.asList(1,2,3,4).iterator(),evenPredicate(), Arrays.asList(2,4).iterator()},
                new Object[]{Arrays.asList(2,2).iterator(),evenPredicate(), Arrays.asList(2,2).iterator()},
                new Object[]{Collections.singletonList(2).iterator(),evenPredicate(), Collections.singletonList(2).iterator()},
                new Object[]{Collections.emptyList().iterator(),evenPredicate(), Collections.emptyList().iterator()}
        };
    }

    private Predicate<Integer> evenPredicate(){
        return p->p%2==0;
    }


    @Test
    @Parameters(method="failIteratorTestParam")
    public <T> void failIteratorTest(Iterator<T> inputIterator, Predicate<T> predicate){
        expectedException.expect(Exception.class);
        Iterator<T> filterIterator = new FilterIterator<>(predicate, inputIterator);
        filterIterator.next();
    }

    private Object[] failIteratorTestParam() {
        return new Object[]{
                new Object[]{Arrays.asList(1,2,3,4).iterator(),null},
                new Object[]{Arrays.asList(1,3).iterator(),evenPredicate()},
                new Object[]{null,evenPredicate()},
                new Object[]{Collections.emptyList().iterator(),evenPredicate()},
        };
    }


    @Test
    public void failedRemoveOperation(){
        expectedException.expect(UnsupportedOperationException.class);
        Iterator<Integer> it = Arrays.asList(1,2,3,4).iterator();
        Iterator<Integer> fiIntegerIterator = new FilterIterator<>(evenPredicate(),it);
        fiIntegerIterator.remove();
    }

}