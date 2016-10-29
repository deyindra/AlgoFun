package org.idey.algo.util;

/**
 *
 * @param <E> Deserialize Object E from {@link String}
 * <pre>
 *        DeSerialize&lt;Integer&gt; deserializer = str -&gt; Integer.parseInt(str);
 *        int i = (deserializer.deserialize("1");
 *        //print 3
 *        System.out.println(i+2);
 * </pre>
 */
public interface DeSerialize<E>{
    E deserialize(String str);
}
