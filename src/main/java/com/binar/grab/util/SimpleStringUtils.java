package com.binar.grab.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Random;

public class SimpleStringUtils {
    public static String randomString(int size) {
        return randomString(size, false);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static String randomString(int size, boolean numberOnly) {
        String saltChars = "1234567890";
        if (!numberOnly) {
            saltChars += "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        }
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < size) {
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }

        return salt.toString();
    }
    public Pageable getShort(String orderby, String ordertype, Integer page, Integer size) {
        Pageable show_data;
        if (orderby != null) {
            if (ordertype != null) {
                if (ordertype.equals("desc")) {
                    show_data = PageRequest.of(page, size, Sort.by(orderby).descending());
                } else {
                    show_data = PageRequest.of(page, size, Sort.by(orderby).ascending());
                }
            } else {
                show_data = PageRequest.of(page, size, Sort.by(orderby).descending());
            }

        } else {
            show_data = PageRequest.of(page, size, Sort.by("id").descending());
        }
        return show_data;
    }

}
