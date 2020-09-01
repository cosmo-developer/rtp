package org.cosmo.remotetransport.example;
/**
 * @author Sonu Aryan
 */

import org.cosmo.remotetransport.exceptions.InvalidProtocolException;
import org.cosmo.remotetransport.inbuilt.InbuiltServices;
import org.cosmo.remotetransport.session.RTPSession;


public class RTPSessionExample {
    public static void main(String[] args) throws InvalidProtocolException {
        String host="localhost";
        int port=8058;
        RTPSession rtpSession=RTPSession.getInstance();
        rtpSession.addService(host,port, InbuiltServices.DEVICE_INFO_PROVIDER_SERVICES,"osname");
        rtpSession.addService(host,port,"fileservice","writeFile","filename","data");
        rtpSession.addService(host,port,"fileservice","deleteFile","filename");
        System.out.println(rtpSession.getServiceParams(0));
        System.out.println(rtpSession.query(0));
//        System.out.println(rtpSession.query(1,"Hello.txt","Hello World".getBytes()));
        System.out.println(rtpSession.query(2,"Hello.txt"));
    }
}
