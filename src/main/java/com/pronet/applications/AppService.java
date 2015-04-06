package com.pronet.applications;

import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AppService")
public class AppService {

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @Autowired
//    public AppService(JdbcTemplate jdbcTemplate){
//        this.jdbcTemplate = jdbcTemplate;
//    }

public void jobAppAt(AppModel model) throws Exception{

    try{

        String sql = "SELECT name FROM user_login WHERE ID =" + model.getUser_id() ;
        String user_name = jdbcTemplate.queryForObject(sql, String.class);

        String sql1 = "SELECT email FROM user_login WHERE ID =" + model.getUser_id() ;
        String user_email = jdbcTemplate.queryForObject(sql1,String.class);

        jdbcTemplate.execute(
                "INSERT INTO job_apps(company_id,user_id,job_id,job_title,company_name,user_name,user_email) values('"
                        + model.getCompany_id() + "','"
                        + model.getUser_id()+ "','"
                        + model.getJob_id() + "','"
                        + model.getJob_title() + "','"
                        + model.getCompany_name() + "','"
                        + user_name + "','"
                        + user_email+"')");
    }
    catch(Exception e){

        throw new BadRequestException(e.getMessage());

    }
}

    public JSONObject getAllJobsAt(String c_id) throws Exception{

        JSONObject jsonObject = new JSONObject();

        try{


            String getApps = "SELECT * FROM job_apps WHERE company_id =" + c_id ;

            List tmp = jdbcTemplate.queryForList(getApps);

            jsonObject.put("jobs",tmp);

            return jsonObject;

        }
        catch(Exception e){

            throw new BadRequestException(e.getMessage());
        }


    }

}
