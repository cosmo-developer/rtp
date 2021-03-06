package org.cosmo.remotetransport.example;

import org.cosmo.remotetransport.exceptions.InvalidRemoteException;
import org.cosmo.remotetransport.registry.ServerRegistryManager;
import org.cosmo.remotetransport.registry.RegistryManagerFactory;
public class Main {
    /**
     *
     * @param args
     * @throws InvalidRemoteException
     */
    public static void main(String[] args) throws InvalidRemoteException {
        ServerRegistryManager rm= RegistryManagerFactory.getServerRegistryManager();
        rm.port(8058);
        rm.register(ExampleRemote.class);
        rm.register(FileService.class);
        rm.run();
    }
}
