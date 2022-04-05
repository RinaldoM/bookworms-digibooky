package com.bookworms.digibooky.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bookworms.digibooky.security.Feature.*;

public enum Role {
    ADMIN(new ArrayList<>(Arrays.asList(
            VIEW_MEMBERS,
            REGISTER_LIBRARIAN,
            REGISTER_BOOK,
            UPDATE_BOOK,
            DELETE_BOOK,
            VIEW_LENT_BOOK_BY_MEMBER,
            VIEW_OVERDUE_LENT_BOOK
    ))),
    LIBRARIAN(new ArrayList<>(Arrays.asList(
            REGISTER_BOOK,
            UPDATE_BOOK,
            DELETE_BOOK,
            VIEW_LENT_BOOK_BY_MEMBER,
            VIEW_OVERDUE_LENT_BOOK
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
