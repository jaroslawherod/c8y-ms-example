package com.softwareag.petclinic.owner;

import com.cumulocity.model.idtype.GId;
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
public class OwnerRepository {
    private final InventoryApi inventoryApi;
    private final ConversionService conversionService;

    public OwnerRepository(InventoryApi inventoryApi, ConversionService conversionService) {
        this.inventoryApi = inventoryApi;
        this.conversionService = conversionService;
    }


    public List<Owner> findAll(PageRequest pageRequest) {
        return StreamSupport.stream(inventoryApi
                .getManagedObjectsByFilter(new InventoryFilter().byType("com_softwareag_petclinc_Owner"))
                .get(pageRequest.getPageSize(), new QueryParam(() -> "currentPage", String.valueOf(pageRequest.getCurrentPage())))
                .allPages().spliterator(), false)
                .map(owner -> conversionService.convert(owner, Owner.class))
                .collect(Collectors.toList());
    }

    public Owner create(Owner owner) {
        checkArgument(owner.getId() == null, "id needs to be null on create");
        return conversionService.convert(inventoryApi.create(conversionService.convert(owner, ManagedObjectRepresentation.class)), Owner.class);
    }

    public void delete(String id) {
        inventoryApi.delete(GId.asGId(id));
    }
}
