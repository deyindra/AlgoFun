package org.idey.algo.prime;

import java.util.BitSet;

public class PrimeTable {
    private int upperLimit;
    private BitSet bits;

    public PrimeTable(int upperLimit) {
        this.upperLimit = upperLimit+1;
        bits = new BitSet();
        bits.set(0,false);
        bits.set(1,false);
        bits.set(2,this.upperLimit,true);
        fillSeive();
    }

    private void fillSeive(){
        for(int i=2;i<upperLimit;i++){
            boolean prime = bits.get(i);
            if(prime){
                for(int j=2;i*j<upperLimit;j++){
                    bits.set(i*j,false);
                }
            }
        }
    }

    private boolean isPrime(int i){
        if(i>=upperLimit){
            throw new ArrayIndexOutOfBoundsException();
        }
        return bits.get(i);
    }

    public static void main(String[] args) {
        PrimeTable t = new PrimeTable(5);
        System.out.println(t.isPrime(4));
        System.out.println(t.isPrime(3));
    }
}
