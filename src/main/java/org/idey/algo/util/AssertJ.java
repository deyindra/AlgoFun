package org.idey.algo.util;

import java.util.function.Predicate;

/**
 * Assertion Utility class which will Assert statement in case statement is false,
 * it will throw IllegalArgument Exception.
 */

public class AssertJ {

    private AssertJ(){
        throw new AssertionError("Invalid Access");
    }
    /**
     *
     * @param object which will satisfy following code
     * <pre>
     *               new Predicate&lt;T&gt;(){
     *                  public boolean test(T t){
     *                      return t!=null;
     *                  }
     *               }
     * </pre>
     * @param message Error message in case Predicate fails
     * @param <T> describe the type of the object
     * @throws IllegalArgumentException in case passed object is null
     */
    public  static <T> void notNull(T object, String message){
        assertTrue(t -> t!=null,object,message);
    }

    /**
     *
     * @param predicate {@link Predicate} to test the condition
     * @param object Object which will satisfy the predicate
     * @param message Error message
     * @param args Argument to format the message
     * @param <T> describe the type of the object
     * @throws IllegalArgumentException throws {@link IllegalArgumentException} in case Asserttion fails
     */
    public  static <T> void assertTrue(Predicate<? super T> predicate, T object, String message, Object... args){
        if(!predicate.test(object)){
            message = getFinalErrorMessage(message, args);
            if(message==null){
                throw new IllegalArgumentException();
            }else{
                throw  new IllegalArgumentException(message);
            }
        }
    }

    /**
     *
     * @param message Error Message in can be null or empty
     * @param args Arguments for format the error message
     * @return Empty Error Message in case this is null or empty or actual error message
     */
    private static String getFinalErrorMessage(String message, Object... args){
        String actualMessage=null;
        if(message!=null && !("").equals(message.trim())){
            message = message.trim();
            if(args!=null && args.length>0) {
                actualMessage = String.format(message, args);
            }else{
                actualMessage = message;
            }
        }
        return actualMessage;
    }
}
