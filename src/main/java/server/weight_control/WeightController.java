package server.weight_control;

import server.database2.LoginDB;
import server.database2.MedicDB;
import server.database2.MedicHasPatientDB;
import server.database2.MessageMedicPatientDB;
import server.evidence_filter.EvidenceFilterInterface;
import server.retrieve_data.RetrieveDataInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightController {

    private final String PATH_CLASS_EF = "server.evidence_filter.";
    private final String PATH_CLASS_RD = "server.retrieve_data.";

    private static HashMap<Integer, LocalDate> avvisi;
    private static AbstractNetSmile netSmile;

    public WeightController() {
        avvisi = new HashMap<>();
        netSmile = new NetSmile2();

        try {
            FileReader file = new FileReader("src\\main\\resources\\testdatirete.txt");
            BufferedReader buffer = new BufferedReader(file);
            String line = buffer.readLine();
            while(line!=null) {
                String[] s = line.split(" ");
                netSmile = new NetSmileNode(netSmile, s[0], (EvidenceFilterInterface) Class.forName(PATH_CLASS_EF +s[1]).newInstance(), (RetrieveDataInterface) Class.forName(PATH_CLASS_RD +s[2]).newInstance());
                line = buffer.readLine();
            }

        } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LocalDate getLastAvviso(int patientId)
    {
        if(avvisi.get(patientId)!=null)
            return avvisi.get(patientId);
        return null;
    }

    public void addLastAvviso(int patientId, LocalDate date)
    {
        avvisi.put(patientId, date);
    }

    public void removeID(int patientId)
    {
        avvisi.remove(patientId);
    }

    public void startcheck(int patientId, LocalDate actualDate, double actualPeso) {

        netSmile.clearNet();
        netSmile.setEvidence(patientId, actualDate, actualPeso);
        netSmile.runNet();

        String result = netSmile.getResultUtility();

        //if(result!=null)
            //sendNotification(patientId, result);

    }

    private void sendNotification(int patientId, String msg)
    {
        System.out.println("Invio notifiche");

        msg = "Paziente " + patientId + ": " + msg;

        List<Integer> medics = MedicHasPatientDB.selectMedics(patientId);

        if(medics!=null && !medics.isEmpty()) {
            for(Integer medicId: medics) {

                Map<String, Object> map = new HashMap<>();
                map.put("Medic_id", medicId);
                map.put("Patient_id", 0);
                map.put("timedate", LocalDateTime.now());
                map.put("medic_sender", false);
                map.put("message", msg);
                MessageMedicPatientDB.insert(map);



                if((Boolean) map.get("email_notify")) {
                    map = LoginDB.selectMedic(medicId);
                    EmailNotificator.sendEmail(msg, String.valueOf(map.get("email")));
                }
                if((Boolean) map.get("sms_notify")) {
                    map = MedicDB.select(medicId);
                    SMSNotificator.sendSMS(msg, String.valueOf(map.get("phone")));
                }
            }
        }
    }

}
