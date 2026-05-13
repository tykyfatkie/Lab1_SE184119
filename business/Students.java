package business;

import model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Students quản lý danh sách đăng ký leo núi.
 * Kế thừa từ ArrayList để tận dụng các phương thức có sẵn.
 */
public class Students extends ArrayList<Student> {
    private final String pathFile;
    private boolean isSaved;

    private static final String HEADER_TABLE = "|-----------------------------------------------------------------------|\n"
            + "| StudentID  | Name               | Phone       | PeakCode   | Fee       |\n"
            + "|-----------------------------------------------------------------------|";
    private static final String FOOTER_TABLE = "|-----------------------------------------------------------------------|";

    public Students() {
        this.pathFile = "registrations.dat";
        this.isSaved = true;
        readFromFile(); // Tự động nạp dữ liệu khi khởi tạo
    }

    public boolean isSaved() {
        return isSaved;
    }

    @Override
    public boolean add(Student x) {
        boolean result = super.add(x);
        if (result)
            isSaved = false;
        return result;
    }

    public void update(Student x) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equalsIgnoreCase(x.getId())) {
                this.set(i, x);
                isSaved = false;
                return;
            }
        }
    }

    public void delete(String id) {
        Student s = searchById(id);
        if (s != null) {
            this.remove(s);
            isSaved = false;
        }
    }

    public Student searchById(String id) {
        for (Student s : this) {
            if (s.getId().equalsIgnoreCase(id.trim()))
                return s;
        }
        return null;
    }

    public void searchByName(String name) {
        List<Student> found = new ArrayList<>();
        for (Student s : this) {
            if (s.getName().toLowerCase().contains(name.toLowerCase()))
                found.add(s);
        }
        if (found.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            showAll(found);
        }
    }

    public void showAll() {
        showAll(this);
    }

    public void showAll(List<Student> l) {
        if (l.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }
        System.out.println(HEADER_TABLE);
        for (Student i : l) {
            System.out.println("| " + i.toString());
        }
        System.out.println(FOOTER_TABLE);
    }

    public List<Student> filterByCampusCode(String campusCode) {
        List<Student> result = new ArrayList<>();
        for (Student s : this) {
            if (s.getId().toUpperCase().startsWith(campusCode.toUpperCase()))
                result.add(s);
        }
        return result;
    }

    public void statisticalizeByMountainPeak() {
        if (this.isEmpty()) {
            System.out.println("No registration data available.");
            return;
        }
        new Statistics(this).show();
    }

    /**
     * Nạp toàn bộ đối tượng Student từ file nhị phân vào List.
     */
    public void readFromFile() {
        File f = new File(pathFile);
        if (!f.exists())
            return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            while (true) {
                try {
                    Student x = (Student) ois.readObject();
                    super.add(x);
                } catch (EOFException e) {
                    break; // Kết thúc file thành công
                }
            }
            this.isSaved = true;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, "Error loading file", ex);
        }
    }

    /**
     * Ghi toàn bộ danh sách hiện tại xuống file registrations.dat.
     */
    public void saveToFile() {
        if (this.isSaved) {
            System.out.println("No new changes to save.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathFile))) {
            for (Student i : this) {
                oos.writeObject(i);
            }
            this.isSaved = true;
            System.out.println("Registration data has been successfully saved to `registrations.dat`.");
        } catch (IOException ex) {
            System.out.println("Error saving file: " + ex.getMessage());
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}