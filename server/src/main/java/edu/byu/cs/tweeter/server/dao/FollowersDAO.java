package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;

public class FollowersDAO {

    public FollowersResponse getFollowers(FollowersRequest request){
        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("follows");
        Index index = table.getIndex("follows_index");

        Table userTable = dynamoDB.getTable("users");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#fhKey", "followee_handle");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":fhVal", request.getFollower().getAlias());

        System.out.println(request.getFollower().getAlias());
        QuerySpec querySpec = new QuerySpec()
                    .withKeyConditionExpression("#fhKey = :fhVal")
                    .withNameMap(nameMap)
                    .withValueMap(valueMap)
                    .withScanIndexForward(true);
        if(request.getLastFollower() != null){
            querySpec = querySpec
                    .withExclusiveStartKey("followee_handle", request.getFollower().getAlias(), "follower_handle", request.getLastFollower().getAlias());
        }
        querySpec = querySpec
                .withMaxResultSize(request.getLimit());




        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        items = index.query(querySpec);

        iterator = items.iterator();
        System.out.println("Printing query:");
        while (iterator.hasNext()) {
            item = iterator.next();
            System.out.println(item.getString("follower_handle"));

            GetItemSpec getItemSpec = new GetItemSpec()
                    .withPrimaryKey("alias", item.getString("follower_handle"));
            Item outcome = null;
            try{
                outcome = userTable.getItem(getItemSpec);
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
            System.out.println(outcome.getString("alias"));

            responseFollowers.add(new User(outcome.getString("firstName"), outcome.getString("lastName"), outcome.getString("alias"), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
        }
        if(items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey() == null){
            hasMorePages = false;
        }
        return new FollowersResponse(responseFollowers, hasMorePages);
    }
}
