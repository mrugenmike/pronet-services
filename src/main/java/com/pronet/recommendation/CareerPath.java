package com.pronet.recommendation;

import com.mongodb.DBObject;

import java.util.List;

/**
 * Created by mrugen on 5/2/15.
 */
public class CareerPath {
    public List<String> getPaths() {
        return paths;
    }

    public long getFrequency() {
        return frequency;
    }

    List<String> paths;
    long frequency;

    public CareerPath(List<String> paths, long frequency) {
        this.paths = paths;
        this.frequency = frequency;
    }


    public static CareerPath instance(DBObject reco) {
        final List<String> paths = (List<String>) reco.get("paths");
        final long frequency = Long.valueOf(reco.get("frequency").toString());
        return new CareerPath(paths, frequency);
    }
}
