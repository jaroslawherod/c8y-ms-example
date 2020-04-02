package com.softwareag.petclinic.pet;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectReferenceRepresentation;
import com.softwareag.petclinic.support.TypeConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@TypeConverter
public class OwnerReferenceConverter implements Converter<ManagedObjectReferenceRepresentation, OwnerReference> {
    @Override
    public OwnerReference convert(ManagedObjectReferenceRepresentation ref) {
        final OwnerReference reference = new OwnerReference();
        reference.setId(GId.asString(ref.getManagedObject().getId()));
        return reference;
    }
}
