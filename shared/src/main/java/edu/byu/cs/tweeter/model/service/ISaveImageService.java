package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SaveImageRequest;
import edu.byu.cs.tweeter.model.service.response.SaveImageResponse;

public interface ISaveImageService {
    SaveImageResponse saveImage(SaveImageRequest request) throws IOException;
}
