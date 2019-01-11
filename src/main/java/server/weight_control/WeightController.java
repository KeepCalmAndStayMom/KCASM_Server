package server.weight_control;

import server.database.v2.LoginDB;
import server.database.v2.MedicDB;
import server.database.v2.MedicHasPatientDB;
import server.database.v2.MessageMedicPatientDB;
import server.evidence_filter.EvidenceFilterInterface;
import server.retrieve_data.RetrieveDataInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightController {

    private static HashMap<Integer, LocalDate> avvisi;
    private static AbstractNetSmile netSmile;

    public WeightController() {

        avvisi = new HashMap<>();

        netSmile = createNet("src\\main\\resources\\RetePeso.txt");
    }

    private AbstractNetSmile createNet(String fileName)
    {
        final String PATH_CLASS_NET = "server.weight_control.";
        final String PATH_CLASS_EF = "server.evidence_filter.";
        final String PATH_CLASS_RD = "server.retrieve_data.";
        AbstractNetSmile net;

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            String line = buffer.readLine();
            net = (AbstractNetSmile) Class.forName(PATH_CLASS_NET + line).newInstance();
            line = buffer.readLine();
            while(line!=null) {
                String[] s = line.split(" ");
                net = new NetSmileNode(net, s[0], (EvidenceFilterInterface) Class.forName(PATH_CLASS_EF +s[1]).newInstance(), (RetrieveDataInterface) Class.forName(PATH_CLASS_RD +s[2]).newInstance());
                line = buffer.readLine();
            }

        } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            net = null;
        }
        return net;
    }

    public LocalDate getLastAvviso(int patientId) { return avvisi.get(patientId); }

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

        String result = netSmile.getResultMessage();

        if(result!=null) {
            addLastAvviso(patientId, actualDate);
            sendNotification(patientId, result);
        }
    }

    private void sendNotification(int patientId, String msg)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        msg = "Paziente " + patientId + ": " + msg;

        List<Integer> medics = MedicHasPatientDB.selectMedics(patientId);

        if(medics!=null && !medics.isEmpty()) {
            for(Integer medicId: medics) {
                if(medicId!=0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("medic_id", medicId);
                    map.put("patient_id", 0);
                    map.put("timedate", dtf.format(LocalDateTime.now()));
                    map.put("subject", "Controllo Peso");
                    map.put("message", msg);
                    map.put("medic_sender", false);
                    MessageMedicPatientDB.insert(map);


                    map = MedicDB.select(medicId);
                    if((Boolean) map.get("email_notify")) {
                        Map<String, Object> map2 = LoginDB.selectMedic(medicId);
                        EmailNotificator.sendEmail(msg, /*String.valueOf(map2.get("email"))*/"20016516@studenti.uniupo.it");
                    }
                    /*
                    if((Boolean) map.get("sms_notify")) {
                        SMSNotificator.sendSMS(msg, String.valueOf(map.get("phone")));
                    }*/
                }
            }
        }
    }

}
