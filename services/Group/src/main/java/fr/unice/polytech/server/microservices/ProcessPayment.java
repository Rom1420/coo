package fr.unice.polytech.server.microservices;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class ProcessPayment {

    private static final Logger logger = Logger.getLogger("ProcessPayment");

    public static boolean makePayment(int orderId) {
        try {
            // URL du service REST de paiement avec orderId
            URL paymentUrl = new URL("http://localhost:8004/api/group/payment?orderId=" + orderId);
            HttpURLConnection connection = (HttpURLConnection) paymentUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Envoyer une requête POST vide
            try (OutputStream os = connection.getOutputStream()) {
                os.write(new byte[0]);
            }

            // Vérifier le statut de la réponse
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                logger.info("Payment validated for order " + orderId);
                return true;
            } else {
                logger.info("Payment failed for order " + orderId);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
