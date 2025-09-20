package com.lifespring.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstant {
    public static  final String PATIENT  = "patient";
    public static final String DOCTOR = "doctor";
    public static final String ADMIN = "admin";
    public static final boolean enabled = true ;
    public static final boolean disabled = false ;
    public static final String emailSubject = "Regarding OTP varification ";


}


