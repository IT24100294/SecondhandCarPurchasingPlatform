package Backend;

public class Utils {
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
