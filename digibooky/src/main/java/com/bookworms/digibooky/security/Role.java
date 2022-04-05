package com.bookworms.digibooky.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN(new ArrayList<>(Arrays.asList(
            Feature.REGISTER_LIBRARIAN
    ))),
    LIBRARIAN(new ArrayList<>(Arrays.asList(

    ))),
    MEMBER(new ArrayList<>(Arrays.asList(

    )));

    private List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
