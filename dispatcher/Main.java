package dispatcher;

import business.Mountains;
import business.Students;
import model.Student;
import tools.Acceptable;
import tools.Inputter;
import java.util.List;

public class Main {
    private static final double BASE_FEE = 6000000;
    private static final double DISCOUNT_RATE = 0.35;

    public static void main(String[] args) {
        int choice = 0;
        Inputter ndl = new Inputter();
        Students rl = new Students();
        Mountains mountains = new Mountains();

        do {
            printMenu();
            String input = ndl.getString("Enter your choice: ");
            if (!Acceptable.isValid(input, Acceptable.INTEGER_VALID)) {
                System.out.println("This function is not available.");
                continue;
            }
            choice = Integer.parseInt(input.trim());

            switch (choice) {
                case 1:
                    addRegistration(ndl, rl, mountains);
                    break;
                case 2:
                    updateRegistration(ndl, rl, mountains);
                    break;
                case 3:
                    System.out.println("\n--- Registered Students ---");
                    rl.showAll();
                    break;
                case 4:
                    deleteRegistration(ndl, rl);
                    break;
                case 5:
                    searchByName(ndl, rl);
                    break;
                case 6:
                    filterByCampus(ndl, rl);
                    break;
                case 7:
                    System.out.println("\n--- Statistics of Registration by Mountain Peak ---");
                    rl.statisticalizeByMountainPeak();
                    break;
                case 8:
                    rl.saveToFile();
                    break;
                case 9:
                    exitProgram(ndl, rl);
                    break;
                default:
                    System.out.println("This function is not available.");
            }
        } while (choice != 9);
    }

    private static void printMenu() {
        System.out.println("\n========= Mountain Hiking Challenge Registration =========");
        System.out.println("1. New Registration");
        System.out.println("2. Update Registration Information");
        System.out.println("3. Display Registered List");
        System.out.println("4. Delete Registration Information");
        System.out.println("5. Search Participants by Name");
        System.out.println("6. Filter Data by Campus");
        System.out.println("7. Statistics of Registration Numbers by Location");
        System.out.println("8. Save Data to File");
        System.out.println("9. Exit");
        System.out.println("==========================================================");
    }

    private static void addRegistration(Inputter ndl, Students rl, Mountains mountains) {
        System.out.println("\n--- New Registration ---");

        String id;
        while (true) {
            id = ndl.inputAndLoop("Student ID (e.g. SE123456): ", Acceptable.STU_ID_VALID).toUpperCase();
            if (rl.searchById(id) != null) {
                System.out.println("Student ID already exists. Please enter a different ID.");
            } else {
                break;
            }
        }

        String name = ndl.inputAndLoop("Name [2-20 chars]: ", Acceptable.NAME_VALID);
        String phone = ndl.inputAndLoop("Phone number [10 digits]: ", Acceptable.PHONE_VALID);
        String email = ndl.inputAndLoop("Email address: ", Acceptable.EMAIL_VALID);

        mountains.showAll();
        String mountainCode;
        while (true) {
            mountainCode = ndl.getString("Mountain Code: ").trim();
            if (mountains.isValidMountainCode(mountainCode))
                break;
            System.out.println("Invalid mountain code. Please enter a code from the list.");
        }

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

    private static void updateRegistration(Inputter ndl, Students rl, Mountains mountains) {
        System.out.println("\n--- Update Registration ---");
        String id = ndl.inputAndLoop("Enter Student ID: ", Acceptable.STU_ID_VALID).toUpperCase();
        Student s = rl.searchById(id);
        if (s == null) {
            System.out.println("This student has not registered yet.");
            return;
        }

        System.out.println("Current: " + s.toString());
        System.out.println("(Press Enter to keep current value)");

        String name = ndl.getString("New Name [" + s.getName() + "]: ").trim();
        if (!name.isEmpty()) {
            if (Acceptable.isValid(name, Acceptable.NAME_VALID))
                s.setName(name);
            else
                System.out.println("Invalid name. Keeping old value.");
        }

        String phone = ndl.getString("New Phone [" + s.getPhone() + "]: ").trim();
        if (!phone.isEmpty()) {
            if (Acceptable.isValid(phone, Acceptable.PHONE_VALID)) {
                s.setPhone(phone);
                double fee = BASE_FEE;
                if (Acceptable.isValid(phone, Acceptable.VIETTEL_VALID)
                        || Acceptable.isValid(phone, Acceptable.VNPT_VALID)) {
                    fee = BASE_FEE * (1 - DISCOUNT_RATE);
                }
                s.setTuitionFee(fee);
            } else
                System.out.println("Invalid phone. Keeping old value.");
        }

        String email = ndl.getString("New Email [" + s.getEmail() + "]: ").trim();
        if (!email.isEmpty()) {
            if (Acceptable.isValid(email, Acceptable.EMAIL_VALID))
                s.setEmail(email);
            else
                System.out.println("Invalid email. Keeping old value.");
        }

        String mountainCode = ndl.getString("New Mountain Code [" + s.getMountainCode() + "]: ").trim();
        if (!mountainCode.isEmpty()) {
            if (mountains.isValidMountainCode(mountainCode))
                s.setMountainCode(mountainCode);
            else
                System.out.println("Invalid mountain code. Keeping old value.");
        }

        rl.update(s);
        System.out.println("Registration updated successfully.");
    }

    private static void deleteRegistration(Inputter ndl, Students rl) {
        System.out.println("\n--- Delete Registration ---");
        String id = ndl.inputAndLoop("Enter Student ID: ", Acceptable.STU_ID_VALID).toUpperCase();
        Student s = rl.searchById(id);
        if (s == null) {
            System.out.println("This student has not registered yet.");
            return;
        }
        System.out.println("Student Details:");
        System.out.println("Student ID : " + s.getId());
        System.out.println("Name       : " + s.getName());
        System.out.println("Phone      : " + s.getPhone());
        System.out.println("Mountain   : " + s.getMountainCode());
        System.out.printf("Fee        : %,.0f%n", s.getTuitionFee());

        String confirm = ndl.getString("Are you sure you want to delete this registration? (Y/N): ");
        if (confirm.trim().equalsIgnoreCase("Y")) {
            rl.delete(id);
            System.out.println("The registration has been successfully deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void searchByName(Inputter ndl, Students rl) {
        System.out.println("\n--- Search by Name ---");
        String name = ndl.getString("Enter name or partial name: ");
        rl.searchByName(name);
    }

    private static void filterByCampus(Inputter ndl, Students rl) {
        System.out.println("\n--- Filter by Campus ---");
        System.out
                .println("Campus codes: CE (Can Tho) | DE (Da Nang) | HE (Ha Noi) | SE (Ho Chi Minh) | QE (Quy Nhon)");
        String campusCode = ndl.getString("Enter campus code: ").trim().toUpperCase();

        boolean validCampus = false;
        for (String code : new String[] { "CE", "DE", "HE", "SE", "QE" }) {
            if (code.equals(campusCode)) {
                validCampus = true;
                break;
            }
        }
        if (!validCampus) {
            System.out.println("Invalid campus code.");
            return;
        }

        List<Student> list = rl.filterByCampusCode(campusCode);
        if (list.isEmpty()) {
            System.out.println("No students have registered under this campus.");
        } else {
            System.out.println("Registered Students Under Campus (" + campusCode + "):");
            rl.showAll(list);
        }
    }

    private static void exitProgram(Inputter ndl, Students rl) {
        if (!rl.isSaved()) {
            String ans = ndl.getString("Do you want to save the changes before exiting? (Y/N): ");
            if (ans.trim().equalsIgnoreCase("Y")) {
                rl.saveToFile();
            } else {
                String confirm = ndl
                        .getString("You have unsaved changes. Are you sure you want to exit without saving? (Y/N): ");
                if (!confirm.trim().equalsIgnoreCase("Y")) {
                    System.out.println("Exit cancelled.");
                    return;
                }
            }
        }
        System.out.println("Goodbye!");
        System.exit(0);
    }
}