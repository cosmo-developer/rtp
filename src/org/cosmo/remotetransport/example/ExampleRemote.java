package org.cosmo.remotetransport.example;

import org.cosmo.remotetransport.annotations.RemoteControlled;
import org.cosmo.remotetransport.annotations.RemoteMethod;
import org.cosmo.remotetransport.annotations.RemoteParam;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RemoteControlled(name = "example")
public class ExampleRemote {
    /**
     *
     * @param message
     * @return
     * @throws UnknownHostException
     */
    @RemoteMethod(name = "print")
    public InetAddress show(@RemoteParam(name = "msg")String message) throws UnknownHostException {
        System.out.println(message);
        return InetAddress.getLocalHost();//return in response
    }
}
