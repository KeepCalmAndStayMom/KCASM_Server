package server.retrieve_data;

import java.time.LocalDate;

public interface RetrieveDataInterface {

    Object getData(int patientID, LocalDate actualDate, double actualPeso);
}
