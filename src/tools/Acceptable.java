package tools;

public interface Acceptable {
    String STU_ID_VALID = "^[CcDdHhSsQq][Ee]\\d{6}$";
    String NAME_VALID = "^.{2,20}$";
    String DOUBLE_VALID = "^\\d+(\\.\\d+)?$";
    String INTEGER_VALID = "\\d+";
    String PHONE_VALID = "^(03[2-9]|07[06-9]|08[1-9]|09[0-9])\\d{7}$";
    String VIETTEL_VALID = "^(032|033|034|035|036|037|038|039|086|096|097|098)\\d{7}$";
    String VNPT_VALID = "^(081|082|083|084|085|088|091|094)\\d{7}$";
    String EMAIL_VALID = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    String EMAIL_FPT_VALID = "^[\\w.-]+@fpt\\.edu\\.vn$";
    String CAMPUS_VALID = "^(CE|DE|HE|SE|QE)$";

    static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}