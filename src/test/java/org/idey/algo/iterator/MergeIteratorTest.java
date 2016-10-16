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

@RunWith(JUnitParamsRunner.class)
public class MergeIteratorTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Parameters(method="successIteratorTestParam")
    public <T extends Comparable<T>> void successIteratorTest(MergeIterator<T> mergeIterator, Iterator<T> expectedIterator){
        while (mergeIterator.hasNext()){
            Assert.assertEquals(mergeIterator.next(), expectedIterator.next());
        }
    }

    private Object[] successIteratorTestParam() {
        return new Object[]{
                new Object[]{new MergeIterator<>(Arrays.asList(1,4,7).iterator(), null,
                        Arrays.asList(2,3).iterator()), Arrays.asList(1,2,3,4,7).iterator()},
                new Object[]{new MergeIterator<>(Arrays.asList(1,4).iterator(),
                        Arrays.asList(2,3).iterator()), Arrays.asList(1,2,3,4,7).iterator()},
                new Object[]{new MergeIterator<>(Collections.singleton((Integer)null).iterator(),
                            Arrays.asList(null,1,4).iterator(),
                            Arrays.asList(2,3).iterator()), Arrays.asList(null,null,1,2,3,4,7).iterator()}
        };
    }

    @Test
    @Parameters(method="failureIteratorTestParam")
    public <T extends Comparable<T>> void failureIteratorTest(MergeIterator<T> mergeIterator){
        expectedException.expect(NoSuchElementException.class);
        mergeIterator.next();
    }

    @SuppressWarnings("unchecked")
    private Object[] failureIteratorTestParam() {
        return new Object[]{
                new Object[]{new MergeIterator<Integer>(getArrayOfIterators(false))},
                new Object[]{new MergeIterator<Integer>(getArrayOfIterators(true))},
                new Object[]{new MergeIterator<Integer>(null,Collections.emptyIterator())}
        };
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> Iterator<T>[] getArrayOfIterators(boolean isEmpty){
        if(isEmpty){
            return new Iterator[]{};
        }else{
            return null;
        }
    }


    @Test
    public void failedRemoveOperation(){
        expectedException.expect(UnsupportedOperationException.class);
        Iterator<Integer> it1 = Arrays.asList(1,4).iterator();
        Iterator<Integer> it2 = Arrays.asList(2,3).iterator();

        Iterator<Integer> mergeIterator = new MergeIterator<>(it1, it2);
        mergeIterator.remove();
    }

}