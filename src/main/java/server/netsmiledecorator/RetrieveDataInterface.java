package server.netsmiledecorator;

import java.time.LocalDate;

public interface RetrieveDataInterface {

    Object getData(int patientID, LocalDate actual_date, double actual_peso);
}
