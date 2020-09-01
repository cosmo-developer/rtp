package org.cosmo.remotetransport.session;

import org.cosmo.remotetransport.exceptions.InvalidProtocolException;
import org.cosmo.remotetransport.exceptions.RTPSessionMultiInstanceException;
import org.cosmo.remotetransport.registry.ClientRegistryManager;
import org.cosmo.remotetransport.registry.RegistryManagerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sonu Aryan
 */
public class RTPSession {
    static RTPSession session=null;
    ArrayList<String> services;
    private RTPSession() throws RTPSessionMultiInstanceException {
        if (session!=null)throw new RTPSessionMultiInstanceException("Not allow multi-instance");
        services=new ArrayList();
    }
    public static RTPSession getInstance(){
        try {
            session=new RTPSession();
        } catch (RTPSessionMultiInstanceException e) {
            e.printStackTrace();
        }
        return session;
    }
    public void addService(String ipAddress, int port,String remoteName, String serviceName, List<String> paramsName){
        String params="";
        if (paramsName.size()>0)params="#";
        for (String n:paramsName){
            params+=n+"@";
        }
        services.add("rtp://"+ipAddress+":"+port+"/"+remoteName+"!"+serviceName+params);
    }
    public void addService(String ipAddress,int port,String remoteName,String serviceName,String... paramsName){
        addService(ipAddress,port,remoteName,serviceName, Arrays.asList(paramsName));
    }
    public ArrayList<String> getServiceParams(int serviceIndex){
        ArrayList<String> params=new ArrayList<>();
        String service=services.get(serviceIndex);
        String[] have=service.split("#");
        if (have.length>1){
            String ppath=have[1];
            have=ppath.split("@");
            for (String s:have){
                if (s.trim().isEmpty())break;
                params.add(s);
            }
        }
        return params;
    }
    public Object query(int serviceIndex,Object... paramValues) throws InvalidProtocolException {
        ClientRegistryManager crm= RegistryManagerFactory.getClientRegistryManager();
        crm.register(services.get(serviceIndex));
        int c=0;
        ArrayList<String> argList=getServiceParams(serviceIndex);
        for (String key:argList){
            crm.set(key,paramValues[c]);
            c++;
        }
        return crm.query();
    }
}
