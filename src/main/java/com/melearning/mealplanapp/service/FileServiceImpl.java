package com.melearning.mealplanapp.service;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Value("${app.upload.dir}")
    public String uploadDir;

    public void uploadFile(MultipartFile file) {

        try {
        	
//        	 Path copyLocation = Paths
//                 .get(uploadDir + StringUtils.cleanPath(file.getOriginalFilename()));
//             Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
             
        	InputStream inputStream = file.getInputStream();
			BufferedImage image = ImageIO.read(inputStream);
			BufferedImage resized = resize(image, 500, 500);
        	File newImage = new File(uploadDir + StringUtils.cleanPath(file.getOriginalFilename()));
			ImageIO.write(resized, "png", newImage);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static BufferedImage resize(BufferedImage img, int width, int height) {
    	Dimension imageSize = new Dimension(img.getWidth(), img.getHeight());
    	Dimension boundary = new Dimension(width, height);
    	Dimension scaledDimension = getScaledDimension(imageSize, boundary);
        Image tmp = img.getScaledInstance(scaledDimension.width, scaledDimension.height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(scaledDimension.width, scaledDimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    
    private static Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {   	
        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        return new Dimension((int) (imageSize.width  * ratio),
                             (int) (imageSize.height * ratio));
    }

}
