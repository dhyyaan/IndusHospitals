package com.indushospitals.utils;



import com.indushospitals.customview.CustomFontEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by think360 on 02/08/17.
 */

public class Validation {


    public static boolean isValidName(CustomFontEditText editText) {

            Pattern mPattern = Pattern.compile("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Matcher matcher = mPattern.matcher(editText.getText().toString());
            if (!matcher.find()) {
                return true;
            } else {
                return false;
            }
        }
    }

