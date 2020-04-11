package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;

public class FollowersDAO {

    private static List<User> followers;

    public FollowersResponse getFollowers(FollowersRequest request){
        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        if(followers == null){
            followers = initializeFollowers();
        }
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0){
            if(followers != null){
                int followersIndex = getFollowersStartingIndex(request.getLastFollower(), followers);
                for(int limitCounter = 0; followersIndex < followers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++){
                    responseFollowers.add(followers.get(followersIndex));
                }
                hasMorePages = followersIndex < followers.size();
            }
        }

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("follows");

//        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle","@TestUser");
//
//        Item outcome = table.getItem(spec);
//        System.out.println("GotItem " + outcome);

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#fhKey", "follower_handle");
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":fhVal", "@TestUser");

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#fhKey = :fhVal")
                .withNameMap(nameMap)
                .withValueMap(valueMap)
                .withScanIndexForward(true);
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        items = table.query(querySpec);

        iterator = items.iterator();
        System.out.println("Printing query:");
        while (iterator.hasNext()) {
            item = iterator.next();
            System.out.println(item.getString("follower_handle"));
            responseFollowers.add(new User("first", "last", item.getString("follower_handle")));
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers){
        int followeesIndex = 0;
        if(lastFollower != null){
            for(int i = 0; i < allFollowers.size(); i++){
                if(lastFollower.equals(allFollowers.get(i))){
                    followeesIndex = i + 1;
                }
            }
        }
        return followeesIndex;
    }

    private List<User> initializeFollowers(){
        List<User> users = getFollowGenerator().generateFollowers(50);
        return users;

    }

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

}
