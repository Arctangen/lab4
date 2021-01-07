package ru.spbstu.telematics.java;



import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

    static List<Integer> count(int limit){
        Instant start1 = Instant.now();
        final List<Integer> primes = new ArrayList<Integer>();
        primes.add(2);
        for(int i=3; i<limit; i+=2) {
            boolean isPrime = true;
            for(int prime: primes) {
                if(prime * prime > i)
                    break;
                if(i % prime == 0) {
                    isPrime = false;
                    break;
                }
            }
            if(isPrime)
                primes.add(i);
        }
        System.out.println("Total primes found: " + primes.size() + "in " + Duration.between(start1, Instant.now()).toMillis());
        //System.out.println(Duration.between(start1, Instant.now()).toMillis());
        return primes;
    }

    static List<Integer> countThreads(int limit){
        final List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        int threadNumber = 8;
        int last = (int) Math.sqrt(limit) + 1;
        final PrimeChecker firstChecker = new PrimeChecker(3, 2, last, list);
        firstChecker.setName("Base checker");
        Instant start = Instant.now();
        firstChecker.run();
        if (last % 2 == 0)
            last++;
        final PrimeChecker[] checkers = new PrimeChecker[threadNumber];
        final List[] copies = new ArrayList[threadNumber];
        for (int i = 0; i < threadNumber; i++) {
            final List<Integer> listCopy = new ArrayList<Integer>(list);
            checkers[i] = new PrimeChecker(last + 2 * i, 2 * threadNumber, limit, listCopy);
            checkers[i].setName("Checker #" + i);
            copies[i] = listCopy;
        }
        for (int i = 1; i < threadNumber; i++)
            checkers[i].start();
        checkers[0].start();
        try {
            for (int i = 1; i < threadNumber; i++)
                checkers[i].join();
        } catch (InterruptedException ex) {}
        int total = list.size();
        for (int i = 0; i < threadNumber; i++)
            total += (copies[i].size() - list.size());
        System.out.println("Total threadprimes found: " + total + "in " + Duration.between(start, Instant.now()).toMillis());

        final List<Integer> listFinal = new ArrayList<Integer>();
        listFinal.addAll(list);
        for (int i = 0; i < threadNumber; i++) {
            copies[i].removeAll(list);
            listFinal.addAll(copies[i]);
        }

        Collections.sort(listFinal);

        return listFinal;
    }

    public static void main(String[] args) {
        List<Integer> primet = new ArrayList<Integer>();
        List<Integer> primes = new ArrayList<Integer>();

        primet = count(200);
        primes = countThreads(200);
        System.out.println(primes);
        System.out.println(primet);
    }
}

