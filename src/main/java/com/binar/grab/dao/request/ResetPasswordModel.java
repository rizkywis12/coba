package com.binar.grab.dao.request;

import lombok.Data;


@Data
public class ResetPasswordModel {
    public String email;

    public String otp;
    public String newPassword;
}
