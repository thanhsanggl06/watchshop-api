package com.api.watchshop.service;

import com.api.watchshop.dto.OrderRequest;
import jakarta.mail.MessagingException;

public interface EmailService {
    String sendHtmlEmail(OrderRequest request) throws MessagingException;
}
