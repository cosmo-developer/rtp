package org.cosmo.remotetransport.registry;
/**
 * @author Sonu Aryan
 */
import org.cosmo.remotetransport.exceptions.InvalidProtocolException;
import org.cosmo.remotetransport.registry.rdata.RemoteData;

public interface ClientRegistryManager {
    public void register(String path) throws InvalidProtocolException;
    public Object query(RemoteData rdata,String rmname);
    public Object query();
    public void set(String key,Object value);
}
