package com.letrogthien.user.repositories;

import com.letrogthien.user.entities.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, UUID> {
}
