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
    final List<JobListing> jobListings = Arrays.asList(new JobListing("jobid", "title", "ebay", "logo", "SF", "Great Job!"));
    when(jobSearchService.fetchJobListings(term,99,5)).thenReturn(new JobListings(jobListings,100l));
    JobSearchResource jobSearchResource = new JobSearchResource(jobSearchService);
    //when

    JobListings jobList = jobSearchResource.fetchJobListings(term,99,5);
    //then
    assertThat(jobList).isNotNull();
    assertThat(jobList.getListings()).hasSize(1);
    assertThat(jobList.getTotalTerms()).isEqualTo(100);
}
}