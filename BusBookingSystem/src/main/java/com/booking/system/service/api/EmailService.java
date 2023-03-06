package com.booking.system.service.api;

public interface EmailService {
    void sendMessageWithAttachment(String to, String ticketId);
}
