package com.pronet.search.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserListings implements Serializable{
    private List<UserListing> userListings;

    public UserListings() {
        userListings = new ArrayList<UserListing>();
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public List<UserListing> getUserListings() {
        return userListings;
    }

    public void setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
    }

    private int totalEntries;

    public UserListings(List<UserListing> userListings, int totalEntries) {
        this.userListings = userListings;
        this.totalEntries = totalEntries;
    }


    public void add(List<UserListing> listings) {
        if(listings!=null)
        userListings.addAll(listings);
    }

    public void addToTotalTerms(Long totalTerms) {
        totalEntries+=totalTerms;
    }
}
