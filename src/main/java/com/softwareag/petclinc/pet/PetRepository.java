package com.softwareag.petclinc.pet;

import com.cumulocity.model.pagination.PageRequest;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.QueryParam;
import com.cumulocity.sdk.client.inventory.InventoryApi;
import com.cumulocity.sdk.client.inventory.InventoryFilter;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkArgument;

@Repository
public class PetRepository {
    private final InventoryApi inventoryApi;
    private final ConversionService conversionService;

    public PetRepository(InventoryApi inventoryApi, ConversionService conversionService) {
        this.inventoryApi = inventoryApi;
        this.conversionService = conversionService;
    }


    public List<Pet> findAll(PageRequest pageRequest) {
        return StreamSupport.stream(inventoryApi.getManagedObjectsByFilter(new InventoryFilter().byType("com_softwareag_petclinc_Pet"))
                .get(pageRequest.getPageSize(),
                        new QueryParam(() -> "currentPage", String.valueOf(pageRequest.getCurrentPage())),
                        new QueryParam(() -> "withParents", String.valueOf(true))
                )
                .allPages().spliterator(), false)
                .map(owner -> conversionService.convert(owner, Pet.class))
                .collect(Collectors.toList());
    }

    public Pet create(Pet pet) {
        checkArgument(pet.getId() == null, "id needs to be null on create");
        return conversionService.convert(inventoryApi.create(conversionService.convert(pet, ManagedObjectRepresentation.class)), Pet.class);
    }
}
