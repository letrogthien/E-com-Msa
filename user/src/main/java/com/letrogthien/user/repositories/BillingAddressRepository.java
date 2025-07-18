package com.letrogthien.user.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.letrogthien.user.entities.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
    List<BillingAddress> findAllByCountryRegion(String countryRegion);

    Optional<BillingAddress> findByPostalCode(String postalCode);
}
