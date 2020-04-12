package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowingDAO {

    private static Map<User, List<User>> followeesByFollower;

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        assert request.getLimit() > 0;
        assert request.getFollower() != null;

        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("follows");

        Table userTable = dynamoDB.getTable("users");

//        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle","@TestUser");
//
//        Item outcome = table.getItem(spec);
//        System.out.println("GotItem " + outcome);
        System.out.println(request.getFollower().getAlias());
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#fhKey", "follower_handle");
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":fhVal", request.getFollower().getAlias());

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

            GetItemSpec getItemSpec = new GetItemSpec()
                    .withPrimaryKey("alias", item.getString("followee_handle"));
            Item outcome = null;
            try{
                outcome = userTable.getItem(getItemSpec);
            } catch (Exception e){
                System.err.println(e.getMessage());
            }

            responseFollowees.add(new User(outcome.getString("firstName"), outcome.getString("lastName"), outcome.getString("alias"), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Generates the followee data.
     */
    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100,
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

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFollowee the last followee that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }
}