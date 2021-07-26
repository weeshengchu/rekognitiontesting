package com.amazonaws.samples;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import java.util.List;

public class DetectLabels {

 public static void main(String[] args) throws Exception {

    String photo = "input.jpg";
    String bucket = "alan-demo-rekognition";

    
    
//    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("ap-southeast-1").build();

    AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider (new BasicAWSCredentials("Accesskey", "SecretKey"));
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withCredentials(credentialsProvider).withRegion("ap-southeast-1").build();

    
    DetectLabelsRequest request = new DetectLabelsRequest()
         .withImage(new Image()
         .withS3Object(new S3Object()
         .withName(photo).withBucket(bucket)))
         .withMaxLabels(10)
         .withMinConfidence(75F);

    try {
       DetectLabelsResult result = rekognitionClient.detectLabels(request);
       List <Label> labels = result.getLabels();

       System.out.println("Detected labels for " + photo);
       for (Label label: labels) {
          System.out.println(label.getName() + ": " + label.getConfidence().toString());
       }
    } catch(AmazonRekognitionException e) {
       e.printStackTrace();
    }
 }
}