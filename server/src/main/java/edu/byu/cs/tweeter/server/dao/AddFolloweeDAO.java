package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;

public class AddFolloweeDAO {

    private static Map<User, List<User>> followeesByFollower;
    public AddFollowResponse AddFollowee (AddFollowRequest request) throws Exception {
        assert request.getCurrentUser() != null;
        assert request.getUserToAdd() != null;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("follows");

        User TestUser = new User("TestFirst", "TestLast", "@TestAlias", "");
        User TestFollowee = new User("TestFirst2", "TestLast2", "@TestFollowee", "");

        System.out.println("adding an item");
        PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey("follower_handle", request.getCurrentUser().getAlias(), "followee_handle", request.getUserToAdd().getAlias()));

        return new AddFollowResponse(true);
    }
}
