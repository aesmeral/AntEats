package com.hackuci.csbois.service.idm.core;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.twilio.rest.api.v2010.account.Message;


public class TwilioMessaging {
    public static void testMessage(String seller)
    {
        ServiceLogger.LOGGER.info("Attempting to send a message to " + seller);
        Message message = Message.creator(new com.twilio.type.PhoneNumber(seller),
                            new com.twilio.type.PhoneNumber("+18102420280"),
                    "Testing this message").create();
        ServiceLogger.LOGGER.info("Sent a text message to" + seller + "from " + "+18102420280");
    }
}
