package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;

public class FindFollowerDAO {

    private static Map<User, List<User>> followeesByFollower;

    public FindFollowerResponse FindFollower(FindFollowerRequest request){
        assert request.getCurrentUser() != null;
        assert request.getUserBeingFollowed() != null;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table followsTable = dynamoDB.getTable("follows");

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", request.getUserBeingFollowed().getAlias(), "followee_handle", request.getCurrentUser().getAlias());
        Item outcome = null;
        System.out.println(request.getCurrentUser().getAlias());
        System.out.println(request.getUserBeingFollowed().getAlias());
        try {
            outcome = followsTable.getItem(spec);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }


        if(outcome == null){
            return new FindFollowerResponse(false);
        }
        return new FindFollowerResponse(true);
    }
}
