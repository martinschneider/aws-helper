package io.github.martinschneider.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

/**
 * Service class to interact with AWS Dynamo DB, basically a wrapper around {@link AmazonDynamoDB}.
 */
public class AWSDynamoService {

  private static final Logger LOG = LoggerFactory.getLogger(AWSDynamoService.class);

  private AmazonDynamoDB awsDynamoDB;

  /**
   * Constructor
   *
   * @param awsAccessKey the AWS access key
   * @param awsSecretKey the AWS secret key
   * @param awsRegion the AWS region
   */
  public AWSDynamoService(String awsAccessKey, String awsSecretKey, String awsRegion) {
    awsDynamoDB = buildDynamoClient(awsAccessKey, awsSecretKey, awsRegion);
  }

  /** @return {@link AmazonDynamoDB} */
  public AmazonDynamoDB getAwsDynamo() {
    return awsDynamoDB;
  }

  private AmazonDynamoDB buildDynamoClient(
      String awsAccessKey, String awsSecretKey, String awsRegion) {
    LOG.debug("Building AWS Device Farm client");
    LOG.debug("awsAccessKey={}", awsAccessKey);
    LOG.debug("awsSecretKey={}", awsSecretKey);
    LOG.debug("awsRegion={}", awsRegion);
    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
        .withRegion(awsRegion)
        .build();
  }
}
