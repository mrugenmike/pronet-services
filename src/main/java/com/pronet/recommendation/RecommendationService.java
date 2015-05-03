package com.pronet.recommendation;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.json.simple.JSONObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.pronet.userdetails.UserDetails;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
/**
 * Created by varuna on 4/28/15.
 */
@Component("RecommendationService")
public class RecommendationService {

    @Autowired
    MysqlDataSource mySQL;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    public RecommendationService() {

    }

    public List<JSONObject> getTopThreeFriends(long id) throws UnknownHostException, TasteException {
        System.out.println("Recommended connections");
        List<JSONObject> recommendedUsers = new ArrayList<JSONObject>();
        JDBCDataModel connectionModel = new MySQLJDBCDataModel(mySQL, "connections", "user_id", "item_id", "preference", null);
        int numberOfRecommendation = 3;
        //System.out.println(mongoURL + " " + mongoPort + " " + mongoDatabase);
        //MongoDBDataModel model = new MongoDBDataModel(mongoURL,mongoPort,mongoDatabase,"ratings",false,false,null);
        UserSimilarity similarity = new LogLikelihoodSimilarity(connectionModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(20, similarity, connectionModel);
        Recommender recommender = new GenericUserBasedRecommender(connectionModel, neighborhood, similarity);
        List<RecommendedItem> recommendations = recommender.recommend(id, numberOfRecommendation);
        for (RecommendedItem recommendation : recommendations) {
            if (id != recommendation.getItemID()) {
                Query searchUserQuery = new Query(Criteria.where("_id").is(Long.toString(recommendation.getItemID())));
                UserDetails saveduser = mongoTemplate.findOne(searchUserQuery, UserDetails.class, "UserDetails");
                JSONObject json = new JSONObject();
                json.put("followeeID", saveduser.getId());
                json.put("followeeName", saveduser.getUser_name());
                json.put("followeeImgURL", saveduser.getImg());
                json.put("followeeRole", "R");
                System.out.println(json);
                recommendedUsers.add(json);
            }
        }
        return recommendedUsers;
    }

    public List<JSONObject> getTopThreeSkills(long id) throws UnknownHostException, TasteException {
        System.out.println("Recommended Three Skills");
        JDBCDataModel skillModel = new MySQLJDBCDataModel(mySQL, "skills", "user_id", "item_id", "preference", null);

        //get all skills
        HashMap<Integer, String> skillMapping = new HashMap<Integer, String>();
        String sql1 = "SELECT skillID, skillName FROM skillsMapping ";
        List<Map<String, Object>> skillsList = jdbcTemplate.queryForList(sql1);

        for (int i = 0; i < skillsList.size(); i++) {
            Map<String, Object> skill = skillsList.get(i);
            skillMapping.put((Integer) skill.get("skillID"), (String) skill.get("skillName"));
        }

        List<JSONObject> recommendedSkills = new ArrayList<JSONObject>();
        int numberOfRecommendation = 5;
        UserSimilarity similarity = new LogLikelihoodSimilarity(skillModel);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, skillModel);
        Recommender recommender = new GenericBooleanPrefUserBasedRecommender(skillModel, neighborhood, similarity);
        List<RecommendedItem> recommendations = recommender.recommend(id, numberOfRecommendation);
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation.getItemID());
            Integer reco = (int) recommendation.getItemID();

            JSONObject json = new JSONObject();
            json.put("skillName", skillMapping.get(reco));
            json.put("value", recommendation.getValue());
            System.out.println(json);
            recommendedSkills.add(json);
        }
        return recommendedSkills;
    }

    public List Pearson(long id) throws UnknownHostException, TasteException {
        JDBCDataModel skillPrefModel = new MySQLJDBCDataModel(mySQL, "skillsPref", "user_id", "item_id", "preference", null);
        System.out.println("Recommended Top Three Skills - Pearson");
        int numberOfRecommendation = 3;
        UserSimilarity similarity = new PearsonCorrelationSimilarity(skillPrefModel);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, skillPrefModel);
        Recommender recommender = new GenericBooleanPrefUserBasedRecommender(skillPrefModel, neighborhood, similarity);
        List<RecommendedItem> recommendations = recommender.recommend(id, numberOfRecommendation);
        return recommendations;
    }

    public List Euclidean(long id) throws UnknownHostException, TasteException {
        JDBCDataModel skillPrefModel = new MySQLJDBCDataModel(mySQL, "skillsPref", "user_id", "item_id", "preference", null);
        System.out.println("Recommended Top Three Skills -  Euclidian");
        int numberOfRecommendation = 3;
        UserSimilarity similarity = new EuclideanDistanceSimilarity(skillPrefModel);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, skillPrefModel);
        Recommender recommender = new GenericBooleanPrefUserBasedRecommender(skillPrefModel, neighborhood, similarity);
        List<RecommendedItem> recommendations = recommender.recommend(id, numberOfRecommendation);
        return recommendations;
    }

    public List Tanimoto(long id) throws UnknownHostException, TasteException {
        JDBCDataModel skillPrefModel = new MySQLJDBCDataModel(mySQL, "skillsPref", "user_id", "item_id", "preference", null);
        System.out.println("Recommended Top Three Skills - Tanimato");
        int numberOfRecommendation = 3;
        UserSimilarity similarity = new TanimotoCoefficientSimilarity(skillPrefModel);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, skillPrefModel);
        Recommender recommender = new GenericBooleanPrefUserBasedRecommender(skillPrefModel, neighborhood, similarity);
        List<RecommendedItem> recommendations = recommender.recommend(id, numberOfRecommendation);
        return recommendations;
    }

    public List careerPath(String currentRole, String destinationRole) throws Exception {

        //Query searchUserQuery = new Query(Criteria.where("name").is("Board memeber"));
        List<jobs> jobsMongo = mongoTemplate.findAll(jobs.class ,"jobs");

        HashMap<Integer, String> idtojob = new HashMap<Integer, String>();
        HashMap<String, Integer> jobtoid = new HashMap<String, Integer>();

        for(jobs job : jobsMongo)
        {
            idtojob.put(job.getJobid(),job.getName());
            jobtoid.put(job.getName(),job.getJobid());
        }

        System.out.println(currentRole+" " + destinationRole);
        List<String> recommendedpath = new ArrayList<String>();

        BufferedReader br = null;
        String start = jobtoid.get(currentRole).toString();
        String destination = jobtoid.get(destinationRole).toString();
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader("final.txt"));

            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = sCurrentLine.trim();
                String[] words = sCurrentLine.split("[\\s\\t]+");
                String csvItemIds = words[0];
                String[] itemIds = csvItemIds.split(",");
                if (Arrays.asList(itemIds).contains(start) && Arrays.asList(itemIds).contains(destination)) {
                    recommendedpath.add(sCurrentLine);
                }
            }

            for (String line : recommendedpath)
            {
                String[] words = sCurrentLine.split("[\\s\\t]+");
                String csvItemIds = words[0];
                String[] itemIds = csvItemIds.split(",");
                for(String itemID : itemIds)
                {
                    System.out.print(idtojob.get(itemID) + ",");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return recommendedpath;
    }
}


