package org.cosmo.remotetransport.example;

import org.cosmo.remotetransport.annotations.RemoteControlled;
import org.cosmo.remotetransport.annotations.RemoteMethod;
import org.cosmo.remotetransport.annotations.RemoteParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@RemoteControlled(name="fileservice")
public class FileService {
    @RemoteMethod(name = "createFile")
    public Object createMyFile(@RemoteParam(name = "filename")String name){
        try {
            return new File(name).createNewFile();
        }catch (IOException e){
            return e;
        }
    }
    @RemoteMethod(name="deleteFile")
    public boolean deleteMyFile(@RemoteParam(name="filename")String name){
        return new File(name).delete();
    }
    @RemoteMethod(name = "writeFile")
    public Object writeInMyFile(
            @RemoteParam(name = "filename")String name,
            @RemoteParam(name = "data")byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(name)) {
            fos.write(data);
            fos.flush();
        } catch (FileNotFoundException e) {
            return e;
        } catch (IOException exception) {
            return exception;
        }
        return true;
    }

}
