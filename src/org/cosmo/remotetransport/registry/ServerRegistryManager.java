package org.cosmo.remotetransport.registry;

import org.cosmo.remotetransport.exceptions.InvalidRemoteException;
/**
 * @author Sonu Aryan
 */
import java.lang.reflect.Method;

public interface ServerRegistryManager {
    public void register(Class remote) throws InvalidRemoteException;
    public boolean unregister(Class remote);
    public boolean unregister(String name);
    public void port(int port);
    public int port();
    public void run();
    public Method get(String rcname,String rmname);
    public Class get(String rcname);
}
