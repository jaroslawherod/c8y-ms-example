package com.softwareag.petclinic.owner;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectReferenceRepresentation;
import com.softwareag.petclinic.support.TypeConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@TypeConverter
public class PetReferenceConverter implements Converter<ManagedObjectReferenceRepresentation, PetReference> {
    @Override
    public PetReference convert(ManagedObjectReferenceRepresentation ref) {
        final PetReference reference = new PetReference();
        reference.setId(GId.asString(ref.getManagedObject().getId()));
        return reference;
    }
}
