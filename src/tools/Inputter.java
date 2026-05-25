package tools;

import java.util.Scanner;

public class Inputter {
    private Scanner ndl;

    public Inputter() {
        this.ndl = new Scanner(System.in);
    }

    public String getString(String mess) {
        System.out.print(mess);
        return ndl.nextLine();
    }

    public int getInt(String mess) {
        int result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID))
            result = Integer.parseInt(temp);
        return result;
    }

    public double getDouble(String mess) {
        double result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID))
            result = Double.parseDouble(temp);
        return result;
    }

    public String inputAndLoop(String mess, String pattern) {
        String result = "";
        boolean more = true;
        do {
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if (more)
                System.out.println("Data is invalid! Re-enter ...");
        } while (more);
        return result.trim();
    }

    /**
     * Nhập có giới hạn 3 lần. Nếu sai quá 3 lần hỏi Y thử lại, N quay home.
     * Trả về null nếu người dùng chọn quay về home.
     */
    public String inputWithRetryLimit(String mess, String pattern) {
        while (true) {
            int attempts = 0;
            String result = "";
            while (attempts < 3) {
                result = getString(mess);
                if (Acceptable.isValid(result, pattern)) {
                    return result.trim();
                }
                attempts++;
                int remaining = 3 - attempts;
                if (remaining > 0) {
                    System.out.println("Data is invalid! You have " + remaining + " attempt(s) left.");
                }
            }
            System.out.println("You have entered invalid data 3 times.");
            String ans = getString("Press Y to try again, or N to return to Home: ").trim();
            if (!ans.equalsIgnoreCase("Y")) {
                return null;
            }
        }
    }
}