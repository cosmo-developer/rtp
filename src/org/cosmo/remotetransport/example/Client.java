package org.cosmo.remotetransport.example;
import org.cosmo.remotetransport.registry.ClientRegistryManager;
import org.cosmo.remotetransport.registry.RegistryManagerFactory;

public class Client {
    public static void main(String[] args)throws Exception{
        ClientRegistryManager crm= RegistryManagerFactory.getClientRegistryManager();
        crm.register("cosmo://192.168.225.136:8058/fileservice!writeFile#data@filename@");
        crm.set("filename","message.msg");
        crm.set("data","Aesop".getBytes());
        System.out.println(crm.query());
    }
}
