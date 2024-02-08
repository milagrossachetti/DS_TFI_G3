package com.disenio.TFI.repository;

import com.disenio.TFI.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {  }
