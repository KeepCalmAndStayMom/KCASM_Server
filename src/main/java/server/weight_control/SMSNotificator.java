package server.weight_control;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.messages.TextMessage;

import java.io.IOException;

class SMSNotificator {

    private final static String NEXMO_PHONE_NUMBER = "+393442298522";
    private final static String NEXMO_API_KEY = "635924f3";
    private final static String NEXMO_API_SECRET = "577sIM8w7pdEYRyS";

    private static AuthMethod auth = new TokenAuthMethod(NEXMO_API_KEY, NEXMO_API_SECRET);
    private static NexmoClient client = new NexmoClient(auth);

    static void sendSMS(String msg, String patientPhone) {
        try {
            client.getSmsClient().submitMessage(new TextMessage(NEXMO_PHONE_NUMBER, patientPhone, msg));
            System.out.println("SMS inviato correttamente");
        } catch (NexmoClientException | IOException e) {
            e.printStackTrace();
            System.out.println("Errore nell'invio dell'SMS");
        }
    }
}
