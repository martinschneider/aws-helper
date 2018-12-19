package io.github.martinschneider.aws;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/** Service class to interact with AWS S3, basically a wrapper around {@link AmazonS3}. */
public class AWSS3Service {

  private static final Logger LOG = LoggerFactory.getLogger(AWSS3Service.class);

  private AmazonS3 awsS3;

  public AWSS3Service(String awsAccessKey, String awsSecretKey, String awsRegion) {
    awsS3 = buildS3Client(awsAccessKey, awsSecretKey, awsRegion);
  }

  private AmazonS3 buildS3Client(String awsAccessKey, String awsSecretKey, String awsRegion) {
    LOG.debug("Building AWS S3 client");
    LOG.debug("awsAccessKey={}", awsAccessKey);
    LOG.debug("awsSecretKey={}", awsSecretKey);
    LOG.debug("awsRegion={}", awsRegion);
    return AmazonS3ClientBuilder.standard()
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
        .withRegion(awsRegion)
        .build();
  }

  public void putObject(String bucketName, String keyName, String filePath) {
    try {
      LOG.info("Upload file to S3 bucket {}, keyName: {}", bucketName, keyName);
      awsS3.putObject(bucketName, keyName, new File(filePath));
    } catch (SdkClientException e) {
      LOG.error("Uploading file to S3 failed", e);
    }
  }
}
