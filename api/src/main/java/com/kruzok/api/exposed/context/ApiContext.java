package com.kruzok.api.exposed.context;


public interface ApiContext {
    
    int getRequestVersion();
    
    String getHeader(String headerName);
}
