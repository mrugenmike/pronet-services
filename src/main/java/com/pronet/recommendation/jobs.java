package com.pronet.recommendation;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by varuna on 5/2/15.
 */


@Document(collection = "jobs")
public class jobs {

    String name;
    int jobid;

    public jobs() {
    }


    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
