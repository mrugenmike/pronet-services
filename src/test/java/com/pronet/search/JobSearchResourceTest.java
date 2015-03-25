package com.pronet.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class JobSearchResourceTest {
@Test
public void itShouldSendJobsBySearchTerm(){
    //given
    String term = "Software Engineer";
    JobSearchService jobSearchService = mock(JobSearchService.class);
    when(jobSearchService.fetchJobListings(term)).thenReturn(Arrays.asList(new JobListing("jobid","title","ebay","logo","SF")));
    JobSearchResource jobSearchResource = new JobSearchResource(jobSearchService);
    //when

    List<JobListing> jobList = jobSearchResource.fetchJobs(term);
    //then
    assertThat(jobList).isNotNull();
    assertThat(jobList).hasSize(1);
}
}