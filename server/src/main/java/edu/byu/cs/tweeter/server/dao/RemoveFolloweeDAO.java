package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;

public class RemoveFolloweeDAO {

    private static Map<User, List<User>> followeesByFollower;

    public RemoveResponse removeFollowee(RemoveRequest request){
        assert request.getUserToRemove() != null;
        assert request.getCurrentUser() != null;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("follows");

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("follower_handle", request.getCurrentUser().getAlias(), "followee_handle", request.getUserToRemove().getAlias()));

        System.out.println(request.getCurrentUser().getAlias());
        System.out.println(request.getUserToRemove().getAlias());

        try{
            table.deleteItem(deleteItemSpec);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return new RemoveResponse(true);
    }
}
