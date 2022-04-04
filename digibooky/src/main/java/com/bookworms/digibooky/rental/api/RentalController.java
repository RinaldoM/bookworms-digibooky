package com.bookworms.digibooky.rental.api;

import com.bookworms.digibooky.rental.api.dto.CreateRentalDto;
import com.bookworms.digibooky.rental.api.dto.RentalDto;
import com.bookworms.digibooky.rental.domain.Rental;
import com.bookworms.digibooky.rental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rentals")
public class RentalController {
    private RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRentalDto lendABook(@RequestBody CreateRentalDto createRental){
        return rentalService.lendABook(createRental);
    }
    @DeleteMapping(path =("{rentalId}"), produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public RentalDto returnRental(@PathVariable String rentalId){
        return rentalService.returnRental(rentalId);

    }
}
