package org.cosmo.remotetransport.example;
import org.cosmo.remotetransport.inbuilt.InbuiltServices;
import org.cosmo.remotetransport.registry.ClientRegistryManager;
import org.cosmo.remotetransport.registry.RegistryManagerFactory;

public class Client {
    public static void main(String[] args)throws Exception{
        ClientRegistryManager crm= RegistryManagerFactory.getClientRegistryManager();
        crm.register("rtp://192.168.225.136:8058/fileservice!writeFile#data@filename@");
        crm.set("filename","message.msg");
        crm.set("data","Aesop".getBytes());
        System.out.println(crm.query());
        Thread.sleep(2000);
        crm.register("rtp://192.168.225.136:8058/fileservice!deleteFile#filename@");
        crm.set("filename","message.msg");
        System.out.println(crm.query());
        crm.register("rtp://192.168.225.136:8058/"+ InbuiltServices.DEVICE_INFO_PROVIDER_SERVICES +"!osname");
        System.out.println(crm.query());
    }
}
