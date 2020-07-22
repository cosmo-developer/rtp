package org.cosmo.remotetransport.registry;
import org.cosmo.remotetransport.annotations.RemoteMethod;
import org.cosmo.remotetransport.exceptions.DuplicateKeyEntry;

import java.lang.reflect.Method;
import java.util.ArrayList;
class ClassWithRemoteName {
    final String name;
    final Class clazz;
    final ArrayList<MethodWithName> methods;
    private void sync(String value){
        for (MethodWithName mwn:methods){
            if (mwn.name().equals(value)){
                try {
                    throw new DuplicateKeyEntry("duplicate key:"+value);
                } catch (DuplicateKeyEntry duplicateKeyEntry) {
                    duplicateKeyEntry.printStackTrace();
                }
            }
        }
    }
    public ClassWithRemoteName(String name,Class clazz){
        this.name=name;
        this.clazz=clazz;
        methods=new ArrayList<>();
    }
    public String name(){
        return this.name;
    }
    public Class clazz(){
        return this.clazz;
    }
    public void add(Method method){
        if (method.isAnnotationPresent(RemoteMethod.class)) {
            method.setAccessible(true);
            String s=method.getAnnotation(RemoteMethod.class).name();
            sync(s);
            methods.add(new MethodWithName(s, method));
        }
    }
    public Method method(String name){
        for (MethodWithName mwn:methods){
            if (mwn.name().equals(name)){
                return mwn.method();
            }
        }
        return null;
    }
}