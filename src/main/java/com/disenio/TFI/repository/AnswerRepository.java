package com.disenio.TFI.repository;

import com.disenio.TFI.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.patient.id = :patientId")
    List<Answer> findByPatientId(@Param("patientId") Long patientId);
}
