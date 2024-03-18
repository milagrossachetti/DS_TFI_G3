package com.disenio.TFI.repository;

import com.disenio.TFI.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.date >= :currentDate AND a.date <= :endDate")
    List<Appointment> findAppointmentsWithinDateRange(Date currentDate, Date endDate);

    @Query("SELECT a FROM Appointment a WHERE a.date <= :date ORDER BY a.date DESC LIMIT 1")
    Appointment findMostRecentAppointmentToDate(Date date);
}