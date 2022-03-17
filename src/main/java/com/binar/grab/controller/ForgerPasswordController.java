package com.binar.grab.controller;

import com.binar.grab.config.Config;
import com.binar.grab.dao.request.ResetPasswordModel;
import com.binar.grab.model.oauth.User;
import com.binar.grab.repository.oauth.UserRepository;
import com.binar.grab.service.UserService;
import com.binar.grab.service.email.EmailSender;
import com.binar.grab.util.EmailTemplate;
import com.binar.grab.util.SimpleStringUtils;
import com.binar.grab.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/forget-password/")
public class ForgerPasswordController {

    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService serviceReq;

    @Value("${expired.token.password.minute:}")//FILE_SHOW_RUL
    private int expiredToken;

    @Autowired
    public TemplateResponse templateCRUD;

    @Autowired
    public EmailTemplate emailTemplate;

    @Autowired
    public EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Step 1 : Send OTP
    @PostMapping("/forgot-password")//send OTP
    public Map sendEmailPassword(@RequestBody ResetPasswordModel user) {
        String message = "Thanks, please check your email";

        if (StringUtils.isEmpty(user.getEmail())) return templateCRUD.templateEror("No email provided");
        User found = userRepository.findOneByUsername(user.getEmail());
        if (found == null) return templateCRUD.notFound("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getResetPassword();
        if (StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtils.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);
            template = template.replaceAll("\\{\\{PASS_TOKEN}}", otp);
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername1() == null ? "" +
                    "@UserName"
                    :
                    "@" + found.getUsername1()));

            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername1() == null ? "" +
                    "@UserName"
                    :
                    "@" + found.getUsername1()));
            template = template.replaceAll("\\{\\{PASS_TOKEN}}", found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Chute - Forget Password", template);


        return templateCRUD.templateSukses("success");

    }

    //Step 2 : CHek TOKEN OTP EMAIL
    @PostMapping("/forgot-password-chek-token")
    public Map cheKTOkenValid(@RequestBody ResetPasswordModel model) {
        if (model.getOtp() == null) return templateCRUD.notFound("Token " + config.isRequired);

        User user = userRepository.findOneByOTP(model.getOtp());
        if (user == null) {
            return templateCRUD.templateEror("Token not valid");
        }

        return templateCRUD.templateSukses("Success");
    }

    // Step 3 : lakukan reset password baru
    @PostMapping("/forgot-password-reset")
    public Map<String, String> resetPassword(@RequestBody ResetPasswordModel model) {
        if (model.getOtp() == null) return templateCRUD.notFound("Token " + config.isRequired);
        if (model.getNewPassword() == null) return templateCRUD.notFound("New Password " + config.isRequired);
        User user = userRepository.findOneByOTP(model.getOtp());
        String success;
        if (user == null) return templateCRUD.notFound("Token not valid");

        user.setPassword(passwordEncoder.encode(model.getNewPassword().replaceAll("\\s+", "")));
        user.setOtpExpiredDate(null);
        user.setOtp(null);

        try {
            userRepository.save(user);
            success = "success";
        } catch (Exception e) {
            return templateCRUD.templateEror("Gagal simpan user");
        }
        return templateCRUD.templateSukses(success);
    }


}
