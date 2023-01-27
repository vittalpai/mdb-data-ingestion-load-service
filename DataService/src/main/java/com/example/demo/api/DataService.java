package com.example.demo.api;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@RestController
public class DataService {

    static MongoClient mongoClient;
    static MongoDatabase db;

    static MongoCollection<Document> transactionCollection, accountCollection;

    static {
        // Replace with the MongoDB Connection String
        ConnectionString connectionString = new ConnectionString("mongodb+srv://xxxx:xxxx@demo1.3ketd.mongodb.net/?w=1");
        mongoClient = MongoClients.create(connectionString);
        db = mongoClient.getDatabase("AbcBank");
        transactionCollection = db.getCollection("transaction");
        accountCollection = db.getCollection("account");
    }

    @PostMapping("/data/{id}")
    String insertData(@PathVariable(value = "id") Long accountID) {
        try (ClientSession clientSession = mongoClient.startSession()) {
            clientSession.startTransaction();
            accountCollection.insertMany(clientSession, getBalance(accountID));
            transactionCollection.insertMany(clientSession, getTransactions(accountID));
            clientSession.commitTransaction();
            return "Success";
        } catch (Exception e) {
            System.out.println("aborted ");
            e.printStackTrace();
            return "Failed";
        }
    }

    @GetMapping("/data/{id}")
    String fetchData(@PathVariable(value = "id") int id) {
        try {
            String accountID = String.format("VP0010001-%d", id);
            ArrayList<Document> doc = new ArrayList<>();
            transactionCollection.find(eq("accountId", accountID)).into(doc);
            if (doc.size() != 0) {
                return "Success";
            }
        } catch (Exception e) {
            System.out.println("aborted ");
            e.printStackTrace();
        }
        return "Failure";
    }


    private List<Document> getBalance(Long customerID) {
        List<Document> payload = new ArrayList<Document>();
        long balanceDate = System.currentTimeMillis() / 1000L;
        String key = "VP0010001-" + customerID + "-" + balanceDate;
        Document doc = new Document();
        doc.append("_id", key);
        doc.append("accountId", "VP0010001-" + customerID);
        doc.append("availableBalance", new BigDecimal("13277943.94"));
        doc.append("balanceDate", balanceDate);
        doc.append("currencyId", "USD");
        doc.append("customerId", customerID.toString());
        doc.append("disbursedAmount", new BigDecimal("0"));
        doc.append("extensionData", new Document());
        doc.append("externalIndicator", false);
        doc.append("objectId", key);
        doc.append("onlineActualBalance", new BigDecimal("13278000.94"));
        doc.append("openingDate", new Date());
        doc.append("outstandingBalance", new BigDecimal("0"));
        doc.append("processingTime", new Date());
        doc.append("productName", "AC");
        doc.append("sanctionedAmount", new BigDecimal("0"));
        doc.append("workingBalance", new BigDecimal("13277943.94"));
        payload.add(doc);
        return payload;
    }

    private List<Document> getTransactions(Long customerID) {
        List<Document> payload = new ArrayList<Document>();
        Long key = Long.parseLong("15554592000000");
        for (int i = 0; i < 5; i++) {
            String ID = "VP0010001-" + customerID + "-" + key;
            Document doc = new Document();
            doc.append("_id", ID);
            doc.append("sortKey", key);
            doc.append("accountId", "VP0010001-" + customerID);
            doc.append("accountOfficerId", 1);
            doc.append("amountInAccountCurrency", new BigDecimal("111"));
            doc.append("amountInEventCurrency", new BigDecimal("-129"));
            doc.append("bookingDate", LocalDate.now().plusDays(i));
            doc.append("categorisactionId", 130);
            doc.append("chequeNumber", "");
            doc.append("currency", "USD");
            doc.append("customerId", customerID.toString());
            doc.append("customerReference", "FT21105YDXJT");
            doc.append("extensionData", new Document());
            doc.append("externalIndicator", false);
            doc.append("externalReference", "");
            doc.append("narrative", "");
            doc.append("objectId", ID);
            doc.append("processingDate", LocalDate.now().plusDays(i));
            doc.append("recordId", "197453644225786.030004");
            doc.append("runningBalance", new BigDecimal("0"));
            doc.append("transactionAmount", new BigDecimal("111"));
            doc.append("transactionReference", "FT21105YDXJT");
            doc.append("valueDate", LocalDate.now().plusDays(i));
            payload.add(doc);
            key = key + 1000000000;
        }
        return payload;
    }

}