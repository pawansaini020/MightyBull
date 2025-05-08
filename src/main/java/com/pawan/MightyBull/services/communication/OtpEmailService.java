package com.pawan.MightyBull.services.communication;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dto.BaseDto;
import com.pawan.MightyBull.dto.communication.EmailTemplateDto;
import com.pawan.MightyBull.dto.user.SignupRequest;
import com.pawan.MightyBull.utils.TemplateReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class OtpEmailService extends EmailService {

    @Override
    protected EmailTemplateDto getEmailTemplateDTO(BaseDto baseDto) {
        SignupRequest signupRequest = (SignupRequest) baseDto;
        String template = TemplateReader.readTemplate(AppConstant.Email.OTP_TEMPLATE);
        return EmailTemplateDto.builder()
                .to(Arrays.asList(signupRequest.getEmail()))
                .subject(AppConstant.Email.OTP_SUBJECT)
                .template(template.replace("${name}", signupRequest.getName())
                        .replace("${otp}", signupRequest.getOtp()))
                .build();
    }
}
