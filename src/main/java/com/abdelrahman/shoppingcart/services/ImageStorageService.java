package com.abdelrahman.shoppingcart.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

public interface ImageStorageService {

	String storeFile(InputStream inputStream , String originalName)throws IOException;
	
	Resource getFileResource(String storedPath)throws IOException;
}
