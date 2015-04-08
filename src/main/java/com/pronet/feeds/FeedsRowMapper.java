package com.pronet.feeds;

import com.pronet.feeds.FeedsModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class FeedsRowMapper implements RowMapper
{
        public Object mapRow (ResultSet rs,int rowNum)throws SQLException {
            FeedsModel feeds = new FeedsModel();
            feeds.setFeedID(rs.getString(1));
            feeds.setUserID(rs.getString(2));
            feeds.setFeed_title(rs.getString(3));
            feeds.setFeed_description(rs.getString(4));
            feeds.setFeed_role(rs.getString(5));
            feeds.setFeed_username(rs.getString(6));
            feeds.setFeed_userimage(rs.getString(7));

            System.out.println(feeds.getFeedID() +","+ feeds.getUserID() +","+ feeds.getFeed_title() +","+ feeds.getFeed_description() +","+
                    feeds.getFeed_role() +","+ feeds.getFeed_userimage() +","+ feeds.getFeed_username());
            return feeds;
    }
}
