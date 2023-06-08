package com.sahay.airtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirtimeRepository extends JpaRepository<Airtime , Long> {

    Optional<Airtime> findAirtimeByReference(String reference);
}
