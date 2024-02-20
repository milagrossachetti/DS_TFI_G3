package com.disenio.TFI.repository;

import com.disenio.TFI.model.Odontologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OdontologistRepository extends JpaRepository<Odontologist, Long> {
    Optional<Odontologist> findByUserId(Long userId);
}