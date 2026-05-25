package dispatcher.handler;

import business.Mountains;
import business.Students;
import model.Student;
import tools.Acceptable;
import tools.Inputter;

public class AddRegistration {
    private static final double BASE_FEE = 6000000;
    private static final double DISCOUNT_RATE = 0.35;

    public static void execute(Inputter ndl, Students rl, Mountains mountains) {
        System.out.println("\n--- New Registration ---");

        // Student ID
        String id;
        while (true) {
            id = ndl.inputWithRetryLimit("Student ID (e.g. SE123456): ", Acceptable.STU_ID_VALID);
            if (id == null)
                return;
            id = id.toUpperCase();
            if (rl.searchById(id) != null) {
                System.out.println("Student ID already exists. Please enter a different ID.");
            } else {
                break;
            }
        }

        // Name
        String name = ndl.inputWithRetryLimit("Name [2-20 chars]: ", Acceptable.NAME_VALID);
        if (name == null)
            return;

        // Phone
        String phone = ndl.inputWithRetryLimit("Phone number [10 digits]: ", Acceptable.PHONE_VALID);
        if (phone == null)
            return;

        // Email — bắt buộc @fpt.edu.vn
        String email = ndl.inputWithRetryLimit("Email address (@fpt.edu.vn): ", Acceptable.EMAIL_FPT_VALID);
        if (email == null)
            return;

        // Mountain code
        mountains.showAll();
        String mountainCode;
        while (true) {
            mountainCode = ndl.getString("Mountain Code: ").trim();
            if (mountains.isValidMountainCode(mountainCode))
                break;
            System.out.println("Invalid mountain code. Please enter a code from the list.");
        }

        // Tính phí
        double fee = BASE_FEE;
        if (Acceptable.isValid(phone, Acceptable.VIETTEL_VALID) || Acceptable.isValid(phone, Acceptable.VNPT_VALID)) {
            fee = BASE_FEE * (1 - DISCOUNT_RATE);
            System.out.printf("Viettel/VNPT discount applied (35%%): %,.0f VND%n", fee);
        } else {
            System.out.printf("Tuition fee: %,.0f VND%n", fee);
        }

        rl.add(new Student(id, name, phone, email, mountainCode, fee));
        System.out.println("Registration added successfully.");
    }
}