package org.cosmo.remotetransport.registry;

import org.cosmo.remotetransport.annotations.RemoteControlled;
import org.cosmo.remotetransport.exceptions.DuplicateKeyEntry;
import org.cosmo.remotetransport.exceptions.InvalidProtocolException;
import org.cosmo.remotetransport.exceptions.InvalidRemoteException;
import org.cosmo.remotetransport.registry.rdata.RemoteData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public abstract class RegistryManagerFactory {
    private static ArrayList<ClassWithRemoteName> remotes;
    private static int PORT=80;//default port
    private static void sync(String value){
        for (ClassWithRemoteName cwrn:remotes){
            if (cwrn.name().equals(value)){
                try {
                    throw new DuplicateKeyEntry("duplicate key:"+value);
                } catch (DuplicateKeyEntry duplicateKeyEntry) {
                    duplicateKeyEntry.printStackTrace();
                }
            }
        }
    }
    public static ServerRegistryManager getServerRegistryManager(){
        remotes=new ArrayList<>();
        return new ServerRegistryManager() {
            @Override
            public void register(Class remote) throws InvalidRemoteException {
                if (remote.isAnnotationPresent(RemoteControlled.class)){
                    RemoteControlled rc= (RemoteControlled) remote.getAnnotation(RemoteControlled.class);
                    sync(rc.name());
                    ClassWithRemoteName cwrm=new ClassWithRemoteName(rc.name(),remote);
                    Method[] ms=remote.getMethods();
                    for (Method m:ms){
                        cwrm.add(m);
                    }
                    remotes.add(cwrm);
                }else{
                    throw new InvalidRemoteException("Invalid remote:"+remote);
                }
            }
            @Override
            public boolean unregister(Class remote) {
                for (ClassWithRemoteName cwrn:remotes){
                    if (cwrn.clazz().equals(remote)){
                        return remotes.remove(cwrn);
                    }
                }
                return false;
            }
            @Override
            public boolean unregister(String name) {
                for (ClassWithRemoteName cwrn:remotes){
                    if (cwrn.name().equals(name)){
                        return remotes.remove(cwrn);
                    }
                }
                return false;
            }

            @Override
            public void port(int port) {
                PORT=port;
            }

            @Override
            public int port() {
                return PORT;
            }
            @Override
            public void run(){
                try {
                    ServerSocket ss=new ServerSocket(PORT);
                    while(true){
                        Socket s=ss.accept();
                        Thread t =new Thread(new MultiProcessHandler(s,this));
                        t.start();
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public Method get(String rcname, String rmname) {
                for (ClassWithRemoteName cwrm:remotes){
                    if (cwrm.clazz().equals(get(rcname))){
                        return cwrm.method(rmname);
                    }
                }
                return null;
            }

            @Override
            public Class get(String rcname) {
                for (ClassWithRemoteName cwrm:remotes){
                    if (cwrm.name().equals(rcname)){
                        return cwrm.clazz();
                    }
                }
                return null;
            }
        };
    }
    public static ClientRegistryManager getClientRegistryManager(){
        return new ClientRegistryManager() {
            RemoteData rd=new RemoteData();
            String HOST="localhost";
            int PORT=80;//default port
            String registry="/";
            String methodname="";
            @Override
            public void register(String path) throws InvalidProtocolException {
                if (path.startsWith("rtp://")){
                    if (path.split(":").length==3){
                        HOST=path.split(":")[1].replace("//","");
                        PORT=Integer.parseInt(path.split(":")[2].split("/")[0]);
                        registry=path.split(":")[2].split("/")[1].split("!")[0];
                        methodname=path.split(":")[2].split("/")[1].split("!")[1].split("#")[0];
                        if (path.split(":")[2].split("/")[1].split("!")[1].split("#").length==2){
                            String keys[]=path.split(":")[2].split("/")[1].split("!")[1].split("#")[1].split("@");
                            rd.reserveSpace(keys);
                        }
                    }
                }else{
                    throw new InvalidProtocolException("invalid protocol:"+path.split("//")[0]+"//");
                }
            }

            @Override
            public Object query(RemoteData rdata, String rmname) {
                try {
                    Socket socket = new Socket(HOST, PORT);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(rdata);
                    oos.writeObject(registry);
                    oos.writeObject(rmname);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Object returnable=ois.readObject();
                    ois.close();
                    oos.flush();
                    socket.close();
                    return returnable;
                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Object query() {
                return this.query(rd,methodname);
            }
            @Override
            public void set(String key, Object value) {
                if (rd.containKey(key)){
                    rd.replaceValue(key,value);
                }
            }
        };
    }
}
