package ru.spbstu.telematics.java;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CountTest {

    List<Integer> prime = new ArrayList<Integer>();
    List<Integer> primes = new ArrayList<Integer>();

    @Before
    public void setUp() throws Exception {
        prime.clear();
        primes.clear();
    }

    @Test
    public void sizeTest() {
        prime = App.count(2000000);
        primes = App.countThreads(2000000);
        Assert.assertTrue(prime.size()==primes.size());
    }

    @Test
    public void isEmptyTest(){
        prime = App.count(1);
        primes = App.countThreads(1);
        Assert.assertTrue(prime.size()==primes.size());
    }

    @Test
    public void rndmnum(){
        prime = App.count(2000000);
        primes = App.countThreads(2000000);
        int randomnum = 0+(int) (Math.random()*2000);
        //System.out.println(randomnum);
        Assert.assertTrue(prime.indexOf(randomnum)==primes.indexOf(randomnum));
    }
}
