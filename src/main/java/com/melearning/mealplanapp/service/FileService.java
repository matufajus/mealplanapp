package com.melearning.mealplanapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	public void uploadFile(MultipartFile file, String prefix);

}
