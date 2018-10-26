package org.idey.algo.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InstanOfUtil {
    public static <T> boolean isInstanceOf(T object, Class<?> clazz){
        if(object == null)
            return false;
        else{
            Class<?> baseClass = object.getClass();
            if(baseClass.isArray() && clazz.isArray()){
                    return isInstanceOf(baseClass.getComponentType(), clazz.getComponentType());
            }else{
                return !(!baseClass.isArray() && clazz.isArray()) &&
                       !(baseClass.isArray() && !clazz.isArray()) &&
                       isInstanceOf(baseClass,clazz);
            }
        }

    }


    public static boolean isInstanceOf(Class<?> baseClass, Class<?> clazz){
        if(baseClass != null && baseClass.equals(clazz)){
            return true;
        }else{
            if(baseClass != null){
                Set<Class<?>> sets = new HashSet<>(Arrays.asList(baseClass.getInterfaces()));
                Class<?> superClass = baseClass.getSuperclass();
                if(superClass != null){
                    sets.add(superClass);
                }
                for(Class<?> vClass :sets){
                    if(isInstanceOf(vClass,clazz)){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isInstanceOf(new Integer[]{1,2,3,4}, Object[].class));
    }
}
