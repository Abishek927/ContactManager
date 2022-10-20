package com.contact.manager.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileUploadHandler {
	public final String uploadingPath=new ClassPathResource("static/image/").getFile().getAbsolutePath();
	
	public FileUploadHandler() throws  IOException {
		
	}
	public boolean uploadImage(MultipartFile file) {
		boolean f=false;
		try {
		InputStream is=file.getInputStream();
		byte[] b=new byte[is.available()];
		is.read(b);
		FileOutputStream fileOutputStream=new FileOutputStream(uploadingPath+File.separator+file.getOriginalFilename());
		fileOutputStream.write(b);
		fileOutputStream.close();
		is.close();
		f=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}
	

}
