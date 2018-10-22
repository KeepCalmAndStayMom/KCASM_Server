package server.peso;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StrategyPeso1 implements StrategyInterfacePeso {

    @Override
    public Map<String, Object> getMapOfStrategy(Map<String, Object> map) {

        Map<String, Object> result_map = null;

        if(map.containsKey("homestation_id")
                && map.containsKey("start_date")
                && map.containsKey("actual_date")
                && map.containsKey("start_peso")
                && map.containsKey("actual_peso"))
        {

            result_map = new HashMap<>();
            int homestation_id = (Integer) map.get("homestation_id");
            LocalDate start_date = (LocalDate) map.get("start_date");
            LocalDate actual_date = (LocalDate) map.get("actual_date");
            double start_peso = (Double) map.get("start_peso");
            double actual_peso = (Double) map.get("actual_peso");

            result_map.put("Peso", DataFilter.checkPeso(start_date, actual_date, start_peso, actual_peso));
            result_map.put("Dieta", DataFilter.typeDieta(homestation_id));
            result_map.put("Attivita", DataFilter.typeAttivita(homestation_id));
            result_map.put("Tempo_Peso", DataFilter.dayPesoOut(homestation_id, start_date, start_peso));
            result_map.put("Tempo_Dieta", DataFilter.weekOfDieta(homestation_id, actual_date));
            result_map.put("Tempo_Attivita", DataFilter.weekOfAttivita(homestation_id, actual_date));
            result_map.put("Ultimo_Avviso", DataFilter.weekOfLastAvviso(ControllerPeso.getLastAvviso(homestation_id), actual_date));
        }
        return result_map;

    }
}
