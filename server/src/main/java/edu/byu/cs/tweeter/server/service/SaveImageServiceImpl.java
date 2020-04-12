package edu.byu.cs.tweeter.server.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import edu.byu.cs.tweeter.model.service.ISaveImageService;
import edu.byu.cs.tweeter.model.service.request.SaveImageRequest;
import edu.byu.cs.tweeter.model.service.response.SaveImageResponse;

public class SaveImageServiceImpl implements ISaveImageService {
    @Override
    public SaveImageResponse saveImage(SaveImageRequest request) throws IOException {
        byte[] imageBytes = Base64.getMimeDecoder().decode(request.getBase64Image());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        File file = new File("/tmp/imageFile.jpeg");
        ImageIO.write(bufferedImage, "jpeg", file);
        String url = "profilePics/" + request.getAlias() + "profilePic";
        s3.putObject("classthreeforty", url,file);
        url = "https://classthreeforty.s3-us-west-2.amazonaws.com/" + url;
        return new SaveImageResponse(url);
    }
}
