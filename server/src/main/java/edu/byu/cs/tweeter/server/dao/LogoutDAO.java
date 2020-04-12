package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutDAO {

    public LogoutResponse logoutUser(LogoutRequest request){
        assert request.getUser() != null;
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table authTable = dynamoDB.getTable("authentications");

        System.out.println(request.getUser().getAlias());
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("userAlias", request.getUser().getAlias())
                .withUpdateExpression("set authToken = :t")
                .withValueMap(new ValueMap().withString(":t", ""))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        try{
            System.out.println("updating");
            UpdateItemOutcome outcome1 = authTable.updateItem(updateItemSpec);
            System.out.println("Updated " + outcome1.getItem().toJSONPretty());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new LogoutResponse(true);
    }
}
