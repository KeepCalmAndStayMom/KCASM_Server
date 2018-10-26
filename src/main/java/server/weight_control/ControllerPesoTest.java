package server.weight_control;

import server.evidence_filter.EvidenceFilterInterface;
import server.retrieve_data.RetrieveDataInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class ControllerPesoTest {

    private static HashMap<Integer, LocalDate> avvisi;
    private static AbstractNetSmile netSmile;

    public ControllerPesoTest() {
        avvisi = new HashMap<>();

        String ef_class = "server.evidence_filter.";
        String rd_class = "server.retrieve_data.";

        netSmile = new NetSmile2();
        try {
            FileReader file = new FileReader("src\\main\\resources\\testdatirete.txt");
            BufferedReader buffer = new BufferedReader(file);
            String line = buffer.readLine();
            while(line!=null) {
                String[] s = line.split(" ");
                netSmile = new NetSmileNode(netSmile, s[0], (EvidenceFilterInterface) Class.forName(ef_class+s[1]).newInstance(), (RetrieveDataInterface) Class.forName(rd_class+s[2]).newInstance());
                line = buffer.readLine();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    public static LocalDate getLastAvviso(int homestation_id)
    {
        if(avvisi.get(homestation_id)!=null)
            return avvisi.get(homestation_id);
        return null;
    }

    public void addLastAvviso(int homestation_id, LocalDate date)
    {
        avvisi.put(homestation_id, date);
    }

    public void removeID(int homestation_id)
    {
        avvisi.remove(homestation_id);
    }

    public void startcheck(int homestation_id, LocalDate actual_date, double actual_peso) {

        netSmile.setEvidence(homestation_id, actual_date, actual_peso);
        netSmile.runNet();
        System.out.println(netSmile.getResultUtility());
    }

}
