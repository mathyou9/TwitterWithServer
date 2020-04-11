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
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterDAO {

    private static Map<User, List<User>> followeesByFollower;

    private static List<User> followers;

    private static User currentUser;

    private String auth = "cNdY3D8Gv9ni97rwasRl";

    public RegisterResponse registerUser(RegisterRequest request){
        assert request.getEmail() != null;
        assert request.getFirstName() != null;
        assert request.getLastName() != null;
        assert request.getHandle() != null;
        assert request.getPassword() != null;

//        if(followeesByFollower == null){
//            followeesByFollower = initializeFollowees();
//        }
//        if(followers == null){
//            followers = initializeFollowers();
//        }
//
//        currentUser = new User(request.getFirstName(), request.getLastName(), request.getHandle(), request.getImageUrl());
//
//
//        followeesByFollower.put(currentUser, new ArrayList<User>());

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("users");

        System.out.println("adding an item");
        PutItemOutcome outcome = table.putItem(new
                Item()
                .withPrimaryKey("alias", request.getHandle())
                .withString("firstName", request.getFirstName())
                .withString("lastName", request.getLastName())
                .withString("email", request.getEmail())
                .withString("password", request.getPassword())
                .withString("imageUrl", request.getImageUrl())
        );

        return new RegisterResponse(currentUser, auth);

    }

    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(50,
                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
    }

    private List<User> initializeFollowers(){

        List<User> users = getFollowGenerator().generateFollowers(50);
        return users;

    }

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }
}
