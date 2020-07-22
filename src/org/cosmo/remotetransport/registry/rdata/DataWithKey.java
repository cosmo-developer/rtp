package org.cosmo.remotetransport.registry.rdata;

import java.io.Serializable;
@Deprecated
class DataWithKey implements Serializable {
    String key;
    Object data;
    public DataWithKey(String key,Object data){
        this.key=key;
        this.data=data;
    }
    public String key(){
        return this.key;
    }
    public Object data(){
        return this.data;
    }
    public void renameKey(String key){}
}
