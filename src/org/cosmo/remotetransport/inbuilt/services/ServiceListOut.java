package org.cosmo.remotetransport.inbuilt.services;

import org.cosmo.remotetransport.annotations.RemoteControlled;
import org.cosmo.remotetransport.annotations.RemoteMethod;
import org.cosmo.remotetransport.annotations.RemoteParam;
import org.cosmo.remotetransport.inbuilt.InbuiltServices;
import org.cosmo.remotetransport.registry.ClassWithRemoteName;
import org.cosmo.remotetransport.registry.RegistryManagerFactory;

import java.util.ArrayList;

@RemoteControlled(name = InbuiltServices.SERVICE_INFO_PROVIDER_SERVICES)
public class ServiceListOut {
    ArrayList<ClassWithRemoteName> remotes;
    public ServiceListOut(){
        remotes= RegistryManagerFactory.getList();
    }
    @RemoteMethod(name = "nos")
    public int getNumberOfServices(){
        return remotes.size();
    }
    @RemoteMethod(name="name")
    public String getServiceName(@RemoteParam(name = "si") int n){
        return remotes.get(n).name();
    }
    @RemoteMethod(name="mos")
    public String methodOfService(
            @RemoteParam(name="name")String service_name,
            @RemoteParam(name="mi")int method_index
    ){
        for (ClassWithRemoteName cwrn:remotes){
            if (cwrn.name().equals(service_name)){
                return cwrn.getMethodName(method_index);
            }
        }
        return null;
    }
}
