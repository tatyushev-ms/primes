package com.tatyushevms.primes;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class Incrementer implements Answer<Integer> {
    
    int i = 0;
    
    @Override
    public Integer answer(InvocationOnMock invocation) {
        return ++i;
    }
    
    public int getNumber() {
        return i;
    }
    
}
