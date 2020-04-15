package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginDAO {

    private static User currentUser;

//    private String auth = "cNdY3D8Gv9ni97rwasRl";

    public LoginResponse loginUser(LoginRequest request){
        assert request.getEmail() != null;
        assert request.getPassword() != null;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table userTable = dynamoDB.getTable("users");
        Table authTable = dynamoDB.getTable("authentications");

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", request.getEmail());

        Item outcome = null;
        try{
            outcome = userTable.getItem(spec);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(outcome == null){
            return new LoginResponse("Error: No User");
        }

        if(!BCrypt.checkpw(request.getPassword(), outcome.getString("password"))){
            return new LoginResponse("Error: Invalid Login");
        }
        String auth = UUID.randomUUID().toString();
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("userAlias", request.getEmail())
                .withUpdateExpression("set authToken = :t")
                .withValueMap(new ValueMap().withString(":t", auth))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        UpdateItemOutcome outcome1 = null;
        try{
            System.out.println("updating");
            outcome1 = authTable.updateItem(updateItemSpec);
            System.out.println("Updated " + outcome1.getItem().toJSONPretty());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(outcome1 == null){
            return new LoginResponse("Error: Server Error");
        }

        currentUser = new User(outcome.get("firstName").toString(), outcome.get("lastName").toString(), outcome.get("alias").toString(), outcome.get("imageUrl").toString());

        return new LoginResponse(currentUser, auth);

    }
}
