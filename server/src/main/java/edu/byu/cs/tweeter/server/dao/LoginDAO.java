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

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginDAO {
    private static User currentUser;
    private String auth = "cNdY3D8Gv9ni97rwasRl";
    public LoginResponse loginUser(LoginRequest request){
        assert request.getEmail() != null;
        assert request.getPassword() != null;
//        if(currentUser == null){
//////            currentUser = new User("Test", "User", "@TestUser", "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F27/ORIGINAL/NONE/1789097202");
////            currentUser = new User("Test", "User", request.getEmail(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
////            return new LoginResponse(currentUser, auth);
////        }
////        if(request.getEmail() == currentUser.getAlias() && request.getPassword() == "password"){
////            return new LoginResponse(currentUser, auth);
////        }
////        currentUser.setAlias(request.getEmail());


        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table userTable = dynamoDB.getTable("users");
        Table authTable = dynamoDB.getTable("authentications");

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", request.getEmail());


        System.out.println("adding an item: " + request.getEmail());
        Item outcome = null;
        try{
            System.out.println("getting user");
            outcome = userTable.getItem(spec);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println(outcome);
        if(outcome == null){
            //change to throwing an exception
            return new LoginResponse(null, null);
        }

//        PutItemOutcome outcome1 = authTable.putItem(new
//                Item()
//                .withPrimaryKey("userAlias", request.getEmail())
//                .withString("token", auth)
//        );
//        System.out.println(outcome1);
//        if(outcome1 == null){
//
//        }
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("userAlias", request.getEmail())
                .withUpdateExpression("set token = :t")
                .withValueMap(new ValueMap().withString(":t", auth))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try{
            System.out.println("updating");
            UpdateItemOutcome outcome1 = authTable.updateItem(updateItemSpec);
            System.out.println("Updated " + outcome1.getItem().toJSONPretty());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

//        PutItemOutcome outcome = table.putItem(new
//                Item()
//                .withPrimaryKey("alias", request.getHandle())
//                .withString("firstName", request.getFirstName())
//                .withString("lastName", request.getLastName())
//                .withString("email", request.getEmail())
//                .withString("password", request.getPassword())
//                .withString("imageUrl", request.getImageUrl())
//        );

        return new LoginResponse(currentUser, auth);

    }
}
