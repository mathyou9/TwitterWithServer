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
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;

public class UserProfileDAO {

    private static Map<User, List<User>> followeesByFollower;

    private static List<User> followers;

    private static User selectedUser;

    public ProfileResponse getUserProfile(ProfileRequest request){
        assert request.getAlias() != null;
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table userTable = dynamoDB.getTable("users");

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", request.getAlias());
        System.out.println(request.getAlias());
        Item outcome = null;
        try{
            outcome = userTable.getItem(spec);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        User user = null;
        if(outcome != null){
            user = new User(outcome.get("firstName").toString(), outcome.get("lastName").toString(), outcome.get("alias").toString(), outcome.get("imageUrl").toString());
        }
        return new ProfileResponse(user);
    }
}
