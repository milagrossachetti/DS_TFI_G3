package com.disenio.TFI.repository;

import com.disenio.TFI.model.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
    Optional<Secretary> findByUserId(Long userId);
}