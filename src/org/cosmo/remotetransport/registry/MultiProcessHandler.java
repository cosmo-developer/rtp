package org.cosmo.remotetransport.registry;
import org.cosmo.remotetransport.annotations.RemoteParam;
import org.cosmo.remotetransport.registry.rdata.RemoteData;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.util.ArrayList;

class MultiProcessHandler implements Runnable{
    final Socket connectedSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    RemoteData data;
    final ServerRegistryManager srm;
    public MultiProcessHandler(Socket socket,ServerRegistryManager srm){
        this.connectedSocket=socket;
        this.srm=srm;
    }
    @Override
    public void run(){
        try {
            ArrayList<Object> args=new ArrayList<>();
            ois = new ObjectInputStream(connectedSocket.getInputStream());
            data=(RemoteData)ois.readObject();
            String registry=(String)ois.readObject();
            Class ss=srm.get(registry);
            String rm=(String)ois.readObject();
            Method method=srm.get(registry,rm);
            Object instance=ss.newInstance();
            for (Parameter param:method.getParameters()){
                if (param.isAnnotationPresent(RemoteParam.class)){
                    args.add(data.getData(param.getAnnotation(RemoteParam.class).name()));
                }else{
                    args.add(null);
                }
            }
            Object returnable=method.invoke(instance,args.toArray());
            oos=new ObjectOutputStream(connectedSocket.getOutputStream());
            oos.writeObject(returnable);
            oos.flush();
            ois.close();
            connectedSocket.close();
        }catch (IOException | ClassNotFoundException ie){
            ie.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
