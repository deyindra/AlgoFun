package org.idey.algo.iterator;

import org.idey.algo.util.AssertJ;
import org.idey.algo.util.DeSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Iterator which will read the content of text file line by line and deserialize line
 * based on {@link DeSerialize#deserialize(String)} specification
 * Content of file "abc.txt" is set of integers
 * 1
 * 2
 * 3
 * 4
 * <pre>
 *     DeSerialize&lt;Integer&gt; deserializer = str -&gt; Integer.parseInt(str);
 *     try(ClosableResourceIterator&lt;Integer&gt; it = new FileContentIterator&lt;&gt;("abc.txt",deserializer)){
 *      while(it.hasNext()){
 *              // print content 1,2,3,4
 *          System.out.println(it.next());
 *      }
 *     }
 * </pre>
 * <em>{@link FileContentResourceIterator} must be initialized by try with resource or
 * one must call {@link FileContentResourceIterator#close()} method explicitly
 * </em>
 */
public class FileContentResourceIterator<T> implements ClosableResourceIterator<T> {
    private static final Logger log = LoggerFactory.getLogger(FileContentResourceIterator.class);
    private BufferedReader reader;
    private DeSerialize<T> deSerialize;
    //Content of each line in the text file
    private String line;

    /**
     * @param fileName File Name of the text file including path
     * @param deSerialize {@link DeSerialize} which will deserialize the {@link String}
     *                                       content into an Objecr
     * @throws FileNotFoundException in case fileName does exists or not a valid file
     * @throws IllegalArgumentException in case of fileName is null or empty or deSerialize is null
     */
    public FileContentResourceIterator(String fileName, DeSerialize<T> deSerialize) throws FileNotFoundException {
        AssertJ.assertTrue(s-> !(s == null || ("").equals(s.trim())),
                fileName, "File name can not be null");
        AssertJ.notNull(deSerialize, "Deserializer can not be null");
        fileName = fileName.trim();
        this.reader = new BufferedReader(new FileReader(fileName));
        this.deSerialize = deSerialize;
    }


    /**
     *
     * @return true if the line is not null else false
     * @throws IllegalStateException in case there is any error while reading
     */
    @Override
    public boolean hasNext() {
        if (line != null) {
            return true;
        } else {
            try {
                line = reader.readLine();
                return (line != null);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /**
     * @return T in afrer {@link DeSerialize#deserialize(String)} method call
     * @throws NoSuchElementException in case of no more line
     * @throws IllegalStateException in case there is any problem in reading line
     */
    @Override
    public T next() {
        if (line != null || hasNext()) {
            String prevLine = line;
            line = null;
            return deSerialize.deserialize(prevLine);
        } else {
            throw new NoSuchElementException();
        }
    }



    @Override
    public void close() throws IOException {
        if(reader!=null){
            log.info("Closing Reader");
            reader.close();
        }
    }
}
