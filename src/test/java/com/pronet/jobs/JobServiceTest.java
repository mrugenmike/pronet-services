package com.pronet.jobs;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class JobServiceTest {
    @Test
    public void thisGetsJob(){
        JobsService jobsService = mock(JobsService.class);
        final JSONObject jobDetails = new JSONObject();
        jobDetails.put("jid", "111");
        jobDetails.put("id", "7");//company ID
        jobDetails.put("jtitle", "Software Engineer");
        jobDetails.put("description", "New Opening");
        jobDetails.put("user_name", "Google");
        jobDetails.put("logo", "test.jpg");
        jobDetails.put("skills", "JAVA");
        jobDetails.put("job_region", "CA");
        jobDetails.put("job_status", "ACTIVE");
        //when(jobsService.getJobDetailsAt("111",'1')).thenReturn(jobDetails);

        //JSONObject jsonObject = jobsService.getJobDetailsAt("111","1");
        //assertThat(jsonObject).isNotNull();

    }

}
