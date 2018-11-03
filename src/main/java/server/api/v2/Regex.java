package server.api.v2;

class Regex {
    final static String PHONE_FORMAT = "\\d{9,10}";
    final static String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    final static String TIMEDATE_REGEX = "^(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01]) ([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
}
