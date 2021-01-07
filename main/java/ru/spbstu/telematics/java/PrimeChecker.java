package ru.spbstu.telematics.java;

import java.util.List;

public class PrimeChecker extends Thread {

    private int step, last, current;
    private final List<Integer> primes;
    public PrimeChecker(int start, int step, int last, List<Integer> primes) {
        this.step = step;
        this.last = last;
        this.primes = primes;
        current = start;
    }
    public List<Integer> getPrimes() {
        return primes;
    }

    private boolean checkCurrent() {
        for(int prime: primes) {
            if(prime*prime > current)
                break;
            if(current % prime == 0)
                return false;
        }
        return true;
    }

    @Override
    public void run() {
        while(current <= last) {
            final boolean isPrime = checkCurrent();
            if(isPrime)
                primes.add(current);
            current += step;
        }
        System.out.println("Thread " + getName() + " has finished work");
    }
}
