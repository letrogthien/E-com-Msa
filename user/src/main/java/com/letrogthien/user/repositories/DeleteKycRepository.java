package com.letrogthien.user.repositories;


import com.letrogthien.user.entities.DeleteKyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeleteKycRepository extends JpaRepository<DeleteKyc, UUID> {

}
