package com.mousavi.the_java_academy_bank.service.impl;

import com.mousavi.the_java_academy_bank.dto.EmailDetails;

public interface EmailService {

    void sendEmailAllert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);
}
