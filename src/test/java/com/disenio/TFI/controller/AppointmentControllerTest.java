package com.disenio.TFI.controller;

import com.disenio.TFI.service.AppointmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppointmentService appointmentService;
    Calendar calendar;
    Date currentDate, endDate;
    List<Date> availableDates = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // Defino una lista de fechas disponibles entre el 11/03/2024 y el 12/03/2024
        calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 11, 9, 0, 0);
        currentDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        endDate = calendar.getTime();

        while(currentDate.compareTo(endDate) <= 0) {
            calendar.setTime(currentDate);
            // Obtengo día de la semana, hora y minutos
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            // Si es lunes, martes, miércoles, jueves o viernes agrega la fecha a la lista de fechas disponibles
            if(dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                // La fecha tiene que estar entre las 09:00 y las 13:00 o entre las 14:00 y las 18:00
                if((hour >= 9 && hour <= 12) || (hour >= 14 && hour <= 17) || (hour == 13 && minute == 0) || (hour == 18 && minute == 0)) {
                    availableDates.add(calendar.getTime());
                }
            }
            calendar.add(Calendar.MINUTE, 15);
            currentDate = calendar.getTime();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAvailableDates() throws Exception {
        // Simulo el comportamiento de getAvailableDates
        when(appointmentService.getAvailableDates(15, 1)).thenReturn(availableDates);

        this.mockMvc.perform(get("/appointments/available-dates?duration=15&days=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    /*
    @BeforeEach
    void setUp() {
        // Defino una lista de fechas disponibles entre el 11/03/2024 y el 25/03/2024
        calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 11, 9, 0, 0);
        firstDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        lastDate = calendar.getTime();
        while(firstDate.compareTo(lastDate) <= 0) {
            calendar.setTime(firstDate);
            // Obtengo día de la semana
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            // Obtengo la hora
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            // Si es lunes, martes, miércoles, jueves o viernes agrega la fecha a la lista de fechas disponibles
            if(dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                // Si la hora es entre las 9 y las 12 o las 14 y las 17, agrega la fecha a la lista de fechas disponibles
                if((hour >= 9 && hour <= 12) || (hour >= 14 && hour <= 17)) {
                    availableDates.add(firstDate);
                }
            }
            calendar.add(Calendar.HOUR, 1);
            firstDate = calendar.getTime();
        }
        // Creo los datos de un turno
        calendar.set(2024, Calendar.MARCH, 11, 9, 0, 0);
        date = calendar.getTime();
        appointmentData = new AppointmentData(date, "Consulta", 1L, );
        // Creo un paciente
        Patient patient = new Patient();
        patient.setId(1L);
        // Creo un turno
        appointment = new Appointment(1L, date, "Consulta", AppointmentStatus.PENDING, patient);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAvailableDates() throws Exception {
        // Simulo el comportamiento de getAvailableDates
        when(appointmentService.getAvailableDates()).thenReturn(availableDates);
        this.mockMvc.perform(get("/appointment/getAvailableDates"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void submitAppointmentDetails() throws Exception {
        // Convierto objeto turno a JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(appointment);
        // Simulo el comportamiento de submitAppointmentDetails
        when(appointmentService.submitAppointmentDetails(appointmentData)).thenReturn(appointment);
        this.mockMvc.perform(post("/appointment/postAppointment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andDo(print())
            .andExpect(status().isOk());
    }

     */
}