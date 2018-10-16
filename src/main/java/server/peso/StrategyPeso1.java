package server.peso;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StrategyPeso1 implements StrategyInterfacePeso {

    @Override
    public Map<String, Object> getMapOfStrategy(int homestation_id, LocalDate start_date, LocalDate actual_date, float start_peso, float actual_peso) {
        Map<String, Object> result_map = new HashMap<>();

        result_map.put("Peso", DataFilter.checkPeso(start_date, actual_date, start_peso, actual_peso));
        result_map.put("Dieta", DataFilter.typeDieta(homestation_id));
        result_map.put("Attivita", DataFilter.typeAttivita(homestation_id));
        result_map.put("Tempo_Peso", DataFilter.dayPesoOut(homestation_id, start_date, start_peso));
        result_map.put("Tempo_Dieta", DataFilter.weekOfDieta(homestation_id, actual_date));
        result_map.put("Tempo_Attivita", DataFilter.weekOfAttivita(homestation_id, actual_date));
        result_map.put("Ultimo_Avviso", DataFilter.weekOfLastAvviso(ControllerPeso.getLastAvviso(homestation_id), actual_date));

        return result_map;
    }
}
