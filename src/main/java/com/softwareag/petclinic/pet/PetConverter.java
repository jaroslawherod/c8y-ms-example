package com.softwareag.petclinic.pet;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.softwareag.petclinic.support.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Set;
import java.util.stream.StreamSupport;

@TypeConverter
public class PetConverter implements GenericConverter {
    public static final TypeDescriptor MANAGED_OBJECT_REPRESETATION_TYPE = TypeDescriptor.valueOf(ManagedObjectRepresentation.class);
    public static final TypeDescriptor PET_TYPE = TypeDescriptor.valueOf(Pet.class);
    private final ConversionService conversionService;

    @Autowired
    public PetConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(
                new ConvertiblePair(Pet.class, ManagedObjectRepresentation.class),
                new ConvertiblePair(ManagedObjectRepresentation.class, Pet.class)
        );
    }

    @Override
    public Object convert(Object o, TypeDescriptor source, TypeDescriptor dest) {
        if (isOwner(source) && isManagedObjectRepresentation(dest)) {
            return convert((Pet) o);
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
        return source.isAssignableTo(PET_TYPE);
    }

    private Pet convert(ManagedObjectRepresentation mo) {
        Pet owner = new Pet();
        owner.setId(GId.asString(mo.getId()));
        owner.setName(mo.getName());
        owner.setOwner(StreamSupport.stream(mo.getAssetParents().spliterator(), false)
                .map(pet -> conversionService.convert(pet, OwnerReference.class))
                .findFirst().orElse(null));
        return owner;
    }

    private ManagedObjectRepresentation convert(Pet pet) {
        ManagedObjectRepresentation mo = new ManagedObjectRepresentation();
        mo.setId(GId.asGId(pet.getId()));
        mo.setName(pet.getName());
        mo.setType("com_softwareag_petclinc_Pet");
        return mo;
    }

}
