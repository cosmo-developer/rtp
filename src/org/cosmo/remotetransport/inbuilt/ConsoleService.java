package org.cosmo.remotetransport.inbuilt;

import org.cosmo.remotetransport.annotations.RemoteControlled;
import org.cosmo.remotetransport.annotations.RemoteMethod;
/**
 * @author Sonu Aryan
 */
@RemoteControlled(name = InbuiltServices.CONSOLE_SERVICES)
public class ConsoleService {
    @RemoteMethod(name = "console")
    public Object serve(){

        return null;
    }
}
