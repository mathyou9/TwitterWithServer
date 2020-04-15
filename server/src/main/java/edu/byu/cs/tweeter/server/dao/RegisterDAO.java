package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterDAO {

    private static User currentUser;

//    private String auth = "cNdY3D8Gv9ni97rwasRl";

    public RegisterResponse registerUser(RegisterRequest request){
        assert request.getEmail() != null;
        assert request.getFirstName() != null;
        assert request.getLastName() != null;
        assert request.getHandle() != null;
        assert request.getPassword() != null;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("users");
        Table authTable = dynamoDB.getTable("authentications");

        System.out.println("adding an item");
        String password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        System.out.println(password);
        PutItemOutcome outcome = table.putItem(new
                Item()
                .withPrimaryKey("alias", request.getHandle())
                .withString("firstName", request.getFirstName())
                .withString("lastName", request.getLastName())
                .withString("email", request.getEmail())
                .withString("password", password)
                .withString("imageUrl", request.getImageUrl())
        );

        GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey("alias", request.getHandle());
        try{
            Item getOutcome = table.getItem(getItemSpec);
            currentUser = new User(
                    getOutcome.get("firstName").toString(),
                    getOutcome.get("lastName").toString(),
                    getOutcome.get("alias").toString(),
                    getOutcome.get("imageUrl").toString()
            );
        } catch (Exception e){
            System.err.println(e.getMessage());
        }


        String auth = UUID.randomUUID().toString();
        PutItemOutcome outcome1 = authTable.putItem(new
                Item()
                .withPrimaryKey("userAlias", request.getHandle())
                .withString("authToken", auth)
        );

        return new RegisterResponse(currentUser, auth);

    }
}
