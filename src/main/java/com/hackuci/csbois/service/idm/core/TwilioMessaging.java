package com.hackuci.csbois.service.idm.core;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


public class TwilioMessaging {
    public static String ACCOUNT_SID = "AC9627426339c4b2f26404ccdc9d90102c";       // thinking putting it in IDM Service
    public static String AUTH_TOKEN = "44b88eba3f1895cbd834079e0ece2d0f";        // thinking putting it in IDM Service
    public static void testMessage(String seller)
    {
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        Message message = Message.creator(new com.twilio.type.PhoneNumber(seller),
                            new com.twilio.type.PhoneNumber("+8102420280"),
                    "This is a test message").create();

    }
}
