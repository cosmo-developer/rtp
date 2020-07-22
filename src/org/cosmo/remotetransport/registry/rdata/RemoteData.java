package org.cosmo.remotetransport.registry.rdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteData implements Serializable {
    HashMap<String,Object> datas;
    @Deprecated
    final ArrayList<DataWithKey> data;
    public RemoteData(){
        this.data=new ArrayList<>();
        this.datas=new HashMap<>();
    }
    @Deprecated
    public void data(String key,Object data){
        this.data.add(new DataWithKey(key,data));
    }
    @Deprecated
    public Object data(String key){
        for (DataWithKey da:data){
            if (da.key().equals(key)){
                return da.data();
            }
        }
        return null;
    }
    @Deprecated
    public Object data(int index){
        return data.get(index).data();
    }
    @Deprecated
    public String key(int index){
        return data.get(index).key();
    }
    @Deprecated
    public void set(String key,Object value){
        for (DataWithKey dwk:data){
            if (dwk.key().equals(key)){

            }
        }
    }
    public void addData(String key,Object value){
        datas.put(key,value);
    }
    public Object getData(String key){
        return datas.get(key);
    }
    public void replaceValue(String key,Object newValue)
    {
        datas.replace(key,newValue);
    }
    public boolean containKey(String key){
        return datas.containsKey(key);
    }
    public void reserveSpace(String[] keys){
        for (String key:keys){
            datas.put(key,null);
        }
    }
    public void delete(){
        datas.clear();
    }
}
