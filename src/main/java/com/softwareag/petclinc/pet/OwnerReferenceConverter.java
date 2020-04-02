package com.softwareag.petclinc.pet;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectReferenceRepresentation;
import com.softwareag.petclinic.support.TypeConverter;
import org.springframework.core.convert.converter.Converter;

@TypeConverter
public class OwnerReferenceConverter implements Converter<ManagedObjectReferenceRepresentation, OwnerReference> {
    @Override
    public OwnerReference convert(ManagedObjectReferenceRepresentation ref) {
        final OwnerReference reference = new OwnerReference();
        reference.setId(GId.asString(ref.getManagedObject().getId()));
        return reference;
    }
}
