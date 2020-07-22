package org.cosmo.remotetransport.registry;

import java.lang.reflect.Method;

class MethodWithName {
    final String name;
    final Method method;
    public MethodWithName(String name,Method method){
        this.name=name;
        this.method=method;
    }
    public String name(){
        return this.name;
    }
    public Method method(){
        return this.method;
    }
}
