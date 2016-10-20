package org.idey.algo.iterator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.idey.algo.util.DeSerialize;
import org.idey.algo.rule.ExceptionLoggingRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


@RunWith(JUnitParamsRunner.class)
public class FileContentIteratorTest {
    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Parameters(method = "sucessParameTest")
    public <T> void successIteratorTest(String fileName, DeSerialize<T> deSerialize, Iterator<T> expectedIterator) throws Exception {
            try(FileContentResourceIterator<T> iterator = new FileContentResourceIterator<>(fileName, deSerialize)) {
                Assert.assertTrue(iterator.hasNext() == expectedIterator.hasNext());
                while (iterator.hasNext()) {
                    T object = iterator.next();
                    if (object == null) {
                        Assert.assertNull(expectedIterator.next());
                    } else {
                        Assert.assertEquals(object, expectedIterator.next());
                    }
                }
                Assert.assertTrue(iterator.hasNext() == expectedIterator.hasNext());
            }
    }

    @Test
    @Parameters(method = "sucessParameWithAutoCloseTest")
    public <T> void successIteratorAutoCloseTest(String fileName, DeSerialize<T> deSerialize) throws Exception {
        try(FileContentResourceIterator<T> iterator = new FileContentResourceIterator<>(fileName, deSerialize)) {
            iterator.next();
        }
    }

    private Object[] sucessParameWithAutoCloseTest() {
        return new Object[]{
                new Object[]{FileContentIteratorTest.class
                        .getResource("/FileContentIteratorInput-Integer.txt").getPath(),
                        (DeSerialize<Integer>) Integer::parseInt
                },
                new Object[]{FileContentIteratorTest.class
                        .getResource("/FileContentIteratorInput-String.txt").getPath(),
                        (DeSerialize<String>) str->str
                }
        };
    }


    private Object[] sucessParameTest() {
        return new Object[]{
                new Object[]{FileContentIteratorTest.class
                        .getResource("/FileContentIteratorInput-Integer.txt").getPath(),
                        (DeSerialize<Integer>) Integer::parseInt,
                        Arrays.asList(1,2,3,4,5).iterator()
                },
                new Object[]{FileContentIteratorTest.class
                        .getResource("/FileContentIteratorInput-String.txt").getPath(),
                        (DeSerialize<String>) str->str,
                        Arrays.asList("A", "B", "C", "", "D").iterator()
                }
        };
    }

    @Test
    @Parameters(method="failureIteratorParamTest")
    public <T> void failureIteratorTest(String fileName,DeSerialize<T> deSerialize,
                                    Class<? extends Exception> clazz) throws Exception {
        expectedException.expect(clazz);
        try (FileContentResourceIterator<T> fileContentIterator = new FileContentResourceIterator<>(fileName, deSerialize)) {
            if (clazz == NoSuchElementException.class) {
                while (fileContentIterator.hasNext()) {
                    fileContentIterator.next();
                }
                fileContentIterator.next();
            } else if (clazz == UnsupportedOperationException.class) {
                fileContentIterator.remove();
            }
        }
    }

    private Object[] failureIteratorParamTest(){
        return new Object[]{
                new Object[]{null, (DeSerialize<String>) str->str,IllegalArgumentException.class},
                new Object[]{"", (DeSerialize<String>) str->str,IllegalArgumentException.class},
                new Object[]{" ", (DeSerialize<String>) str->str,IllegalArgumentException.class},
                new Object[]{FileContentIteratorTest.class
                        .getResource("/FileContentIteratorInput-String.txt").getPath(), null,IllegalArgumentException.class},
                new Object[]{"test.txt", (DeSerialize<String>) str->str,FileNotFoundException.class},
                new Object[]{System.getProperty("java.io.tmpdir"), (DeSerialize<String>) str->str,FileNotFoundException.class},
                new Object[]{FileContentIteratorTest.class
                        .getResource("/FileContentIteratorInput-String.txt").getPath(), (DeSerialize<String>) str->str,NoSuchElementException.class},
                new Object[]{FileContentIteratorTest.class
                        .getResource("/FileContentIteratorInput-String.txt").getPath(), (DeSerialize<String>) str->str,UnsupportedOperationException.class}


        };

    }




}