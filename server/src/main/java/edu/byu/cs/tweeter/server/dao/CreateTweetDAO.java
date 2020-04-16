package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;

public class CreateTweetDAO {

    DateTimeFormatter dateFormatter;

    public CreateTweetResponse createTweet (CreateTweetRequest request){
        assert request.getUser() != null;
        dateFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
        System.out.println(request.getMessage());
        System.out.println(request.getUser());

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("tweets");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");

        String uuid = UUID.randomUUID().toString();

        PutItemOutcome outcome = table.putItem(new
                Item()
                .withPrimaryKey("userAlias", request.getUser().getAlias(), "tweetID", uuid)
                .withString("message", request.getMessage())
                .withString("dateCreated", LocalDateTime.now().format(dateTimeFormatter).toString())
        );

        GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey("userAlias", request.getUser().getAlias(), "tweetID", uuid);

        String userAlias = null;
        String message = null;
        try{
            Item getOutcome = table.getItem(getItemSpec);
            System.out.println(getOutcome);
            userAlias = getOutcome.get("userAlias").toString();
            message = getOutcome.get("message").toString();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println(userAlias);
        System.out.println(message);
        if(message == null || userAlias == null){
            return new CreateTweetResponse(null,null);
        }
        Tweet tweet = new Tweet(request.getUser(), request.getMessage(), LocalDateTime.now().format(dateTimeFormatter).toString());
        tweet.setTweetID(uuid);
        String messageBody = (new Gson()).toJson(tweet);
        System.out.println(messageBody);
        String queueUrl = "https://sqs.us-west-2.amazonaws.com/528184139160/PostStatus";
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody)
                .withDelaySeconds(5);
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
        String msgId = send_msg_result.getMessageId();
        System.out.println("Message ID: " + msgId);
        return new CreateTweetResponse(request.getUser(), message);
    }

}
