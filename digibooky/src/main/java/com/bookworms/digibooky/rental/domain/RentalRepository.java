package com.bookworms.digibooky.rental.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RentalRepository {

    private final Map<String, Rental> rentalsById;

    public RentalRepository() {
        this.rentalsById = new HashMap<>();
    }

    public void addRental(Rental rental) {
        rentalsById.put(rental.getRentalId(), rental);
    }
}
