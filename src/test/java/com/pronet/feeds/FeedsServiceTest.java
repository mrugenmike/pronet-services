package com.pronet.feeds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class FeedsServiceTest {
    @Test
    public void getCompanyFeeds() throws Exception {

        FeedsService feedsService = mock(FeedsService.class);
        final List<FeedsModel> jobListings = Arrays.asList(new FeedsModel("5", "new feed", "new feed at 7", "C", "google", "Logo.jpg"));
        ArrayList test = new ArrayList();
        test.add(jobListings);
        when(feedsService.getCompanyFeedAt("5")).thenReturn(test);
        FeedsController getFeed = new FeedsController(feedsService);
        //when
        List feedList = getFeed.getCompanyFeed("5");
        //then
        assertThat(feedList).isNotNull();
        //assertThat(getFeed.getCompanyFeed("5")).hasSize(1);
    }
}
