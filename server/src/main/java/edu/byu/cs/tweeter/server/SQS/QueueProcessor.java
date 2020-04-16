package edu.byu.cs.tweeter.server.SQS;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.server.service.FollowerServiceImpl;


public class QueueProcessor implements RequestHandler<SQSEvent, Void> {



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

            Tweet tweet = gson.fromJson(msg.getBody(), Tweet.class);

            System.out.println("alias:");
            System.out.println(tweet.getUserCreated().getAlias());
            System.out.println("message:");
            System.out.println(tweet.getMessage());
            System.out.println("tweetID:");
            System.out.println(tweet.getTweetID());

            FollowerServiceImpl service = new FollowerServiceImpl();
            FollowersResponse response = service.getFollowers(new FollowersRequest(tweet.getUserCreated(), 100, null));
            System.out.println(response.getHasMorePages());
            int i = 0;
            while(i < 98){
                i++;
                UpdateFeedsRequest request = new UpdateFeedsRequest();
                request.setUsers(response.getFollowers());
                request.setTweetID(tweet.getTweetID());

                String listOfFollowers = gson.toJson(request);
                System.out.println(listOfFollowers);
                String queueUrl = "https://sqs.us-west-2.amazonaws.com/528184139160/UpdateFeed";
                SendMessageRequest sendMessageRequest = new SendMessageRequest()
                        .withQueueUrl(queueUrl)
                        .withMessageBody(listOfFollowers)
                        .withDelaySeconds(5);
                AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
                String msgId = sendMessageResult.getMessageId();
                System.out.println("Message ID: " + msgId);
                response = service.getFollowers(new FollowersRequest(tweet.getUserCreated(), 100, response.getFollowers().get(response.getFollowers().size()-1)));
            }


        }

        return null;

    }

}