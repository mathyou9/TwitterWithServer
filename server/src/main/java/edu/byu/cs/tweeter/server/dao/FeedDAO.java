package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedDAO {

    public FeedResponse getFeed(FeedRequest request){
        assert request.getLimit() >= 0;
        assert request.getUser() != null;

        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("feed");

        System.out.println(request.getUser().getAlias());
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#key", "userFeedAlias");
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":val", request.getUser().getAlias());

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#key = :val")
                .withNameMap(nameMap)
                .withValueMap(valueMap)
                .withScanIndexForward(true);
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        items = table.query(querySpec);

        iterator = items.iterator();
        System.out.println("Printing query:");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
        while (iterator.hasNext()) {
            item = iterator.next();
            System.out.println(item.getString("message"));
            responseTweets.add(new Tweet(request.getUser(), "hello", LocalDateTime.now().format(dateTimeFormatter).toString()));
        }
        if(items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey() == null){
            hasMorePages = false;
        }

        return new FeedResponse(responseTweets, hasMorePages);
    }
}
