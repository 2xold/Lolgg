package com.LeagueOfLegendsGG.LOL.GG.exception.checkable;

import com.LeagueOfLegendsGG.LOL.GG.exception.LoLException;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLExceptionCode;

public class Check {
    public static void checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new LoLException(LoLExceptionCode.USERNAME_IS_EMPTY);
        }
    }
    public static String checkPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new LoLException(LoLExceptionCode.PHONE_NUMBER_IS_EMPTY);
        } else {
            String regEx = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
            String kor = "+82";
            String number;
            if (!phoneNumber.matches(regEx)) {
                throw new LoLException(LoLExceptionCode.PHONE_NUMBER_IS_NOT_VALID);
            }else {
                String mobNum = phoneNumber;
                mobNum = mobNum.replaceAll("[.-]", "");
                number = kor + "" + mobNum;
                phoneNumber = number;
            }
        }
        return phoneNumber;
    }
    public static void checkEmail(String email) {
        if (email == null) {
            throw new LoLException(LoLExceptionCode.EMAIL_IS_EMPTY);
        } else {
            String regEmail = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
            if (!email.matches(regEmail)) {
                throw new LoLException(LoLExceptionCode.EMAIL_IS_NOT_VALID);
            }
        }
    }
    public static void checkPassword(String password) {
        if (password == null) {
            throw new LoLException(LoLExceptionCode.PASSWORD_IS_EMPTY);
        } else {
            String regPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$";
            if (password.length() < 8 || password.length() > 20) {
                throw new LoLException(LoLExceptionCode.PASSWORD_IS_NOT_VALID_LENGTH);
            }
            if (!password.matches(regPassword)) {
                throw new LoLException(LoLExceptionCode.PASSWORD_IS_NOT_VALID);
            }
        }
    }
}
