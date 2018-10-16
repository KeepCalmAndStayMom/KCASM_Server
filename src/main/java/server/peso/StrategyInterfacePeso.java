package server.peso;

import java.time.LocalDate;
import java.util.Map;

public interface StrategyInterfacePeso {

    public abstract Map<String, Object> getMapOfStrategy(int homestation_id, LocalDate start_date, LocalDate actual_date, float start_peso, float actual_peso);
}
