package com.softwareag.petclinic.pet;

import com.cumulocity.model.pagination.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetRepository pets;


    public PetController(PetRepository pets) {
        this.pets = pets;
    }

    @GetMapping
    public List<Pet> pets(@RequestParam(defaultValue = "1") int currentPage,
                              @RequestParam(defaultValue = "10") int pageSize) {
        return pets.findAll(new PageRequest(pageSize, currentPage));
    }

    @PostMapping
    public Pet create(Pet owner){
        return pets.create(owner);
    }

}
