package com.example.weather.serviceimpl;


import org.springframework.stereotype.Service;

import com.example.weather.service.UENCheckService;

import java.time.Year;
import java.util.regex.Pattern;


@Service 
public class UENCheckServiceImpl implements UENCheckService{

    
    @Override
    public String identifyUenType(String uen) {

        if (uen == null) {
            return "Invalid";
        }

        uen = uen.trim().toUpperCase();

        if (Pattern.matches("\\d{8}[A-Z]", uen)) {
            return "Business";
        }

        if (Pattern.matches("(19|20)\\d{7}[A-Z]", uen)) {

            int year = Integer.parseInt(uen.substring(0, 4));
            int currentYear = Year.now().getValue();

            if (year > currentYear) {
                return "Invalid";
            }

            return "Local Company";
        }

        if (Pattern.matches("[TSR]\\d{2}[A-Z][A-Z0-9]\\d{4}[A-Z]", uen)) {
            if (uen.substring(0,1).equals("T")){
                int year = 2000 + Integer.parseInt(uen.substring(1, 3));
                int currentYear = Year.now().getValue();

                if (year > currentYear) {
                    return "Invalid";
                }
            }
            
            return "Other Entity";
        }

        return "Invalid";
    }
}
