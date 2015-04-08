package com.pronet.search.users;

import java.io.Serializable;
import java.util.List;

public class UserListings implements Serializable{
    private final List<UserListing> userListings;

    public int getTotalEntries() {
        return totalEntries;
    }

    public List<UserListing> getUserListings() {
        return userListings;
    }

    private final int totalEntries;

    public UserListings(List<UserListing> userListings, int totalEntries) {
        this.userListings = userListings;
        this.totalEntries = totalEntries;
    }
}
