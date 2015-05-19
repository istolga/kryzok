package com.kruzok.api.exposed.adapter;


public interface Adapter<From, To> {
    
    To adapt(From from);
    
    Class<From> getFromClass();
    
    Class<To> getToClass();
    
    boolean canSupport(int version);
    
}
