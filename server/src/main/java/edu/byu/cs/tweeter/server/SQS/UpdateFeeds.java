package edu.byu.cs.tweeter.server.SQS;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.request.UpdateFeedsRequest;


public class UpdateFeeds implements RequestHandler<SQSEvent, Void> {



    @Override

    public Void handleRequest(SQSEvent event, Context context) {

        for (SQSEvent.SQSMessage msg : event.getRecords()) {

            // TODO:
            System.out.println("msg: ");
            System.out.println(msg);
            // Add code to print message body to the log
            Gson gson = new Gson();
            System.out.println("TOSTRINGED: ");
            System.out.println(msg.getBody());

            UpdateFeedsRequest request = gson.fromJson(msg.getBody(),UpdateFeedsRequest.class);
            List<User> users = request.getUsers();
            String tweetID = request.getTweetID();

            System.out.println("alias:");
            System.out.println(users.get(0).getAlias());

            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

            DynamoDB dynamoDB = new DynamoDB(client);

            Table table = dynamoDB.getTable("feeds");

            for(User user: users){
                System.out.println(user.getAlias());
                PutItemOutcome outcome = table.putItem(new
                        Item()
                        .withPrimaryKey("usersFeedAlias", user.getAlias())
                        .withString("tweetID", tweetID)
                );
            }
        }

        return null;

    }

}