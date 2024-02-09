package com.disenio.TFI.repository;

import com.disenio.TFI.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Question, Long> {
}
