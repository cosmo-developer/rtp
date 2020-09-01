package org.cosmo.remotetransport.inbuilt.hardware;

import org.cosmo.remotetransport.annotations.RemoteControlled;
import org.cosmo.remotetransport.annotations.RemoteMethod;
import org.cosmo.remotetransport.inbuilt.InbuiltServices;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.OperatingSystemMXBean;

@RemoteControlled(name = InbuiltServices.DEVICE_INFO_PROVIDER_SERVICES)
public class DeviceInfoProvider {
    OperatingSystemMXBean os=null;
    MemoryManagerMXBean[] memoryManagers=null;
    public DeviceInfoProvider(){
        os= ManagementFactory.getOperatingSystemMXBean();
        memoryManagers= ManagementFactory.getMemoryManagerMXBeans().toArray(new MemoryManagerMXBean[0]);
    }
    @RemoteMethod(name = "osname")
    public String getOSName(){
        return os.getName();
    }
    @RemoteMethod(name = "arch")
    public String getArchitecture(){
        return os.getArch();
    }
    @RemoteMethod(name = "available_processors")
    public int getAvailableProcessors(){
        return os.getAvailableProcessors();
    }
    @RemoteMethod(name="version")
    public String getVersion(){
        return os.getVersion();
    }
    @RemoteMethod(name = "system_average_load")
    public double systemAverageLoad(){
        return os.getSystemLoadAverage();
    }
    @RemoteMethod(name = "n_memory_managers")
    public int getNMemoryManagers(){
        return memoryManagers.length;
    }


}
