package main.java.com.upload;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws IOException {
        Regions clientRegion =Regions.CA_CENTRAL_1;
        String bucketName = "systemupload";

//        Console console=null;
//        console=System.console();
//
//        if (console== null) {
//
//        }
        Scanner in= new Scanner(System.in);
        System.out.println("*** String object key name ***: ");
        String stringObjKeyName = in.nextLine();
        System.out.println("*** File object key name ***: ");
        String fileObjKeyName =in.nextLine();
        System.out.println("file path : ");
        String fileName =in.nextLine();

        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
            System.out.println("upload successful");
        } catch (AmazonServiceException e) {

            e.printStackTrace();
        } catch (SdkClientException e) {

            e.printStackTrace();
        }
    }
}
