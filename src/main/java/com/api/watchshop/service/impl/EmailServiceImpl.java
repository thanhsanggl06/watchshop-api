package com.api.watchshop.service.impl;

import com.api.watchshop.constants.MyConstants;
import com.api.watchshop.dto.OrderDetailsRequest;
import com.api.watchshop.dto.OrderRequest;
import com.api.watchshop.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    public JavaMailSender emailSender;
    @Override
    public String sendHtmlEmail(OrderRequest request) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        // Thông tin về khách hàng
        String customerName = request.getCustomerRequest().getName();
        String phoneNumber = request.getCustomerRequest().getPhoneNumber();
        String address = request.getShippingAddress();

        // Tạo phần HTML cho email
        StringBuilder htmlMsg = new StringBuilder("<h3>Thông tin khách hàng:</h3>");
        htmlMsg.append("<p><strong>Tên khách hàng:</strong> ").append(customerName).append("</p>");
        htmlMsg.append("<p><strong>Số điện thoại:</strong> ").append(phoneNumber).append("</p>");
        htmlMsg.append("<p><strong>Địa chỉ giao hàng:</strong> ").append(address).append("</p>");

        htmlMsg.append("<h3>Danh sách sản phẩm đã đặt hàng:</h3><ul>");

        for (OrderDetailsRequest request1 : request.getItems()) {
            htmlMsg.append("<li><strong> Id sản phẩm :").append(request1.getId()).append("</strong>  ")
                    .append("Số lượng : " +request1.getQuantity()).append(" x ")
                    .append(request1.getPrice()).append("</li>");
        }

        htmlMsg.append("</ul>");

        message.setContent(htmlMsg.toString(), "text/html;charset=UTF-8");

        helper.setTo(MyConstants.FRIEND_EMAIL);
        helper.setSubject("Bạn có đơn hàng mới  ");

        this.emailSender.send(message);

        return "Email Sent!";
    }
}
