package com.softwareag.petclinic.owner;

import com.cumulocity.model.pagination.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerRepository owners;


    public OwnerController(OwnerRepository owners) {
        this.owners = owners;
    }

    @GetMapping
    public List<Owner> owners(@RequestParam(defaultValue = "1") int currentPage,
                              @RequestParam(defaultValue = "10") int pageSize) {
        return owners.findAll(new PageRequest(pageSize, currentPage));
    }

    @PostMapping
    public Owner create(Owner owner){
        return owners.create(owner);
    }

    @DeleteMapping("{id}")
    public void delete (@PathVariable("id") String id){
        owners.delete(id);
    }

}
