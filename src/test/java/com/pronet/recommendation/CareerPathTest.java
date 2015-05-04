package com.pronet.recommendation;

import com.mongodb.BasicDBObject;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by mrugen on 5/4/15.
 */
public class CareerPathTest {

    @Test
    public void itShould(){
        final BasicDBObject reco = new BasicDBObject("paths", Arrays.asList("a", "b", "c")).append("frequency",1);
        final CareerPath instance = CareerPath.instance(reco);
        assert instance!=null;
    }
    @Test
    public void itShouldLong(){
        final BasicDBObject reco = new BasicDBObject("paths", Arrays.asList("a", "b", "c")).append("frequency",1l);
        final CareerPath instance = CareerPath.instance(reco);
        assert instance!=null;
    }

}