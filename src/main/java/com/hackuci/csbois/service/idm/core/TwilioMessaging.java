package com.hackuci.csbois.service.idm.core;
import com.hackuci.csbois.service.idm.IDMService;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.twilio.rest.api.v2010.account.Message;


public class TwilioMessaging {
    public static void testMessage(String seller)
    {
        ServiceLogger.LOGGER.info("Attempting to send a message to " + seller);
        Message message = Message.creator(new com.twilio.type.PhoneNumber(seller),
                            new com.twilio.type.PhoneNumber(IDMService.getPhoneNumber()),
                    "Testing this message").create();
        ServiceLogger.LOGGER.info("Sent a text message to " + seller + " from " + IDMService.getPhoneNumber());
    }
    public static void firstMeeting(String seller, String buyer)
    {
        ServiceLogger.LOGGER.info(" Attemping to send a message to " + buyer);
        Message.creator(new com.twilio.type.PhoneNumber(buyer),
                        new com.twilio.type.PhoneNumber(IDMService.getPhoneNumber()),
                  "Hello, your swipe seller number is: " + seller).create();
         Message.creator(new com.twilio.type.PhoneNumber(seller),
                         new com.twilio.type.PhoneNumber(IDMService.getPhoneNumber()),
                   "Hello, someone is interested in your swipes\n Your swipe buyer's number is: " + buyer).create();
    }
}
