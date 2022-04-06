package com.bookworms.digibooky.rental.api;

import com.bookworms.digibooky.rental.api.dto.CreateRentalDto;
import com.bookworms.digibooky.rental.api.dto.RentalDto;
import com.bookworms.digibooky.rental.domain.Rental;
import com.bookworms.digibooky.rental.service.RentalService;
import com.bookworms.digibooky.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bookworms.digibooky.security.Feature.*;

@RestController
@RequestMapping("rentals")
public class RentalController {
    private final RentalService rentalService;
    private SecurityService securityService;

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

    @GetMapping(path = "/{memberId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getRentalsOfMember(@RequestHeader String authorizationId, @PathVariable String memberId) {
        securityService.validateAuthorization(authorizationId, VIEW_LENT_BOOK_BY_MEMBER);
        return rentalService.getRentalsOfMember(memberId);
    }


    @GetMapping(path = "/overdue", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getOverdueRentals(@RequestHeader String authorizationId) {
        securityService.validateAuthorization(authorizationId, VIEW_OVERDUE_LENT_BOOK);
        return rentalService.getOverdueRentals();
    }
}
