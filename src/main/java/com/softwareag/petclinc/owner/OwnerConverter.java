package com.softwareag.petclinc.owner;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.softwareag.petclinic.support.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@TypeConverter
public class OwnerConverter implements GenericConverter {
    public static final TypeDescriptor MANAGED_OBJECT_REPRESETATION_TYPE = TypeDescriptor.valueOf(ManagedObjectRepresentation.class);
    public static final TypeDescriptor OWNER_TYPE = TypeDescriptor.valueOf(Owner.class);
    private final ConversionService conversionService;

    @Autowired
    public OwnerConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(
                new ConvertiblePair(Owner.class, ManagedObjectRepresentation.class),
                new ConvertiblePair(ManagedObjectRepresentation.class, Owner.class)
        );
    }

    @Override
    public Object convert(Object o, TypeDescriptor source, TypeDescriptor dest) {
        if (isOwner(source) && isManagedObjectRepresentation(dest)) {
            return convert((Owner) o);
        }
        if (isManagedObjectRepresentation(source) && isOwner(dest)) {
            return convert((ManagedObjectRepresentation) o);
        }

        throw new IllegalArgumentException("Can't map from " + source + " to " + dest + " for value " + o);
    }

    private boolean isManagedObjectRepresentation(TypeDescriptor dest) {
        return dest.isAssignableTo(MANAGED_OBJECT_REPRESETATION_TYPE);
    }

    private boolean isOwner(TypeDescriptor source) {
        return source.isAssignableTo(OWNER_TYPE);
    }

    private Owner convert(ManagedObjectRepresentation mo) {
        Owner owner = new Owner();
        owner.setId(GId.asString(mo.getId()));
        owner.setName(mo.getName());
        owner.setPets(StreamSupport.stream(mo.getChildAssets().spliterator(), false)
                .map(pet -> conversionService.convert(pet, PetReference.class))
                .collect(Collectors.toList()));
        return owner;
    }

    private ManagedObjectRepresentation convert(Owner owner) {
        ManagedObjectRepresentation mo = new ManagedObjectRepresentation();
        mo.setId(GId.asGId(owner.getId()));
        mo.setName(owner.getName());
        mo.setType("com_softwareag_petclinc_Owner");
        return mo;
    }

}
