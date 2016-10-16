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
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RunWith(JUnitParamsRunner.class)
public class PowersetIteratorTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Test
    @Parameters(method="successIteratorTestParam")
    public <T> void successIteratorTest(Set<T> sets,Optional<MinMax> minMaxOptional,
                                        Iterator<Set<T>> finalIterator){
        Iterator<Set<T>> iterator;
        if(minMaxOptional.isPresent()) {
            MinMax minMax = minMaxOptional.get();
            iterator = new PowersetIterator<>(sets, minMax.min, minMax.max);
        }else{
            iterator = new PowersetIterator<>(sets);
        }

        while (iterator.hasNext()){
            Assert.assertEquals(iterator.next(),finalIterator.next());
        }
    }

    private Object[] successIteratorTestParam() {
        return new Object[]{
                new Object[]{new HashSet<>(Arrays.asList(1,2,3,4)),Optional.of(new MinMax(3,4)),
                    Arrays.asList(new HashSet<>(Arrays.asList(1,2,3)),
                            new HashSet<>(Arrays.asList(1,2,4)),
                            new HashSet<>(Arrays.asList(1,3,4)),
                            new HashSet<>(Arrays.asList(2,3,4)),
                            new HashSet<>(Arrays.asList(1,2,3,4))
                            ).iterator()
                },
                new Object[]{new HashSet<>(Arrays.asList(1,2)),Optional.empty(),
                        Arrays.asList(new HashSet<>(Collections.emptyList()),
                                new HashSet<>(Collections.singleton(1)),
                                new HashSet<>(Collections.singleton(2)),
                                new HashSet<>(Arrays.asList(1,2))
                        ).iterator()
                },
                new Object[]{new HashSet<>(Collections.emptyList()),Optional.empty(),
                        Collections.singleton(new HashSet<>(Collections.emptyList())).iterator()
                }

        };
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Test
    @Parameters(method="failureIteratorTestParam")
    public <T> void failedNullSetTest(Optional<MinMax> minMaxOptional){
        expectedException.expect(IllegalArgumentException.class);
        if(minMaxOptional.isPresent()){
            MinMax minMax = minMaxOptional.get();
            new PowersetIterator<T>(null,minMax.min,minMax.max);
        }else{
            new PowersetIterator<T>(null);
        }
    }


    private Object[] failureIteratorTestParam(){
        return new Object[]{
            new Object[]{
                   Optional.of(new MinMax(3,4))
            },
            new Object[]{
                    Optional.empty()
            }

        };
    }

    @Test
    public void testNoSuchElementException(){
        expectedException.expect(NoSuchElementException.class);
        Iterator<Set<Integer>> it = new PowersetIterator<>(Collections.emptySet());
        it.next();
        it.next();
    }

    @Test
    public void testRemove(){
        expectedException.expect(UnsupportedOperationException.class);
        Iterator<Set<Integer>> it = new PowersetIterator<>(Collections.emptySet());
        it.remove();
    }






    private static class MinMax{
        private int min;
        private int max;

        private MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}