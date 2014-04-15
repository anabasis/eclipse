package com.tg.slug.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tg.slug.exception.SysException;
import com.tg.slug.logging.Logging;

public class FileUtil {
	public FileUtil(){}
	/**
	 * 
	 * @param filepath
	 * @param filename
	 * @param is
	 * @throws SysException
	 * @throws IOException
	 */
	public void writeFileFromInputStream(String filepath, String filename, InputStream is) throws SysException, IOException{

		String delemeter   = OSUtil.getOsDelemeter();
		FileOutputStream fos = null;

		File myfile = new File(filepath+delemeter+"attachefiles"+delemeter+filename);
        
        if(myfile.exists()){
        	myfile.delete();
        	Logging.info.println("< FileUtil > getMailAttatchedFile() at "+filename+" is already exixt in ["+filepath+delemeter+"]");
        }
        
        createDirectoryIfNeeded(filepath);
        
        try{
        	
	        fos = new FileOutputStream(myfile);
	        
	        byte[] buffer = new byte[1024];
	        while (0 < is.read(buffer)) {
	            fos.write(buffer);
	        }
		} catch (FileNotFoundException e) {
            throw new SysException("< FileUtil > getMailAttatchedFile() " +e.getMessage());
		} catch (IOException e) {
            throw new SysException("< FileUtil > getMailAttatchedFile() " +e.getMessage());
        }finally{
	        fos.close();
        }
	}
	/***
	 * 
	 * @param directoryName
	 */
	public void createDirectoryIfNeeded(String directoryName)
	{
  
		File theDir = new File(directoryName);
		Logging.info.println("< FileUtil >< createDirectoryIfNeeded > Check Directory Exists: "+directoryName);
		// if the directory does not exist, create it
		if (!theDir.exists()){
			Logging.info.println("< FileUtil >< createDirectoryIfNeeded > Make Directory: " + directoryName);
			theDir.mkdir();
		}
	}
	
	/***
	 * 
	 * @param filepath
	 * @param filename
	 * @return
	 */
	public File checkedDirFile(String filepath, String filename){
		
		String delemeter   = OSUtil.getOsDelemeter();
        
		createDirectoryIfNeeded(filepath);

		File cfile = new File(filepath+delemeter+"attachefiles"+delemeter+filename);
		
		return cfile;

	}
}
