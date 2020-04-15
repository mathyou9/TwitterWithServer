package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryDAO {

    public StoryResponse getStoryTweets(StoryRequest request){
        assert request.getLimit() >= 0;
        assert request.getUser() != null;

        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());
        boolean hasMorePages = true;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);


        Table tweetTable = dynamoDB.getTable("tweets");

        System.out.println(request.getUser().getAlias());
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#key", "userAlias");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":val", request.getUser().getAlias());

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#key = :val")
                .withNameMap(nameMap)
                .withValueMap(valueMap)
                .withScanIndexForward(true)
                .withMaxResultSize(request.getLimit());
        if(request.getLastTweet() != null){
            querySpec = querySpec
                    .withExclusiveStartKey("userAlias", request.getUser().getAlias(), "tweetID", request.getLastTweet().getTweetID());
            //probably need sort key
        }
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        items = tweetTable.query(querySpec);

        iterator = items.iterator();
        System.out.println("Printing query:");
        while (iterator.hasNext()) {
            item = iterator.next();
            System.out.println("item " + item);
            Tweet tweet = new Tweet(
                    request.getUser(),
                    item.getString("message"),
                    item.getString("dateCreated")
            );
            tweet.setTweetID(item.getString("tweetID"));
            responseTweets.add(tweet);
        }
        if(items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey() == null){
            hasMorePages = false;
        }

        return new StoryResponse(responseTweets, hasMorePages);
    }
}
