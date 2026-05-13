package business;

import model.Mountain;
import java.io.*;
import java.util.ArrayList;

public class Mountains extends ArrayList<Mountain> {
    private String pathFile;

    public Mountains() {
        this.pathFile = "MountainList.csv";
        readFromFile();
    }

    public Mountain get(String mountainCode) {
        for (Mountain m : this) {
            if (m.getMountainCode().equalsIgnoreCase(mountainCode.trim()))
                return m;
        }
        return null;
    }

    public boolean isValidMountainCode(String mountainCode) {
        return get(mountainCode) != null;
    }

    public Mountain dataToObject(String text) {
        String[] parts = text.split(",", 4);
        if (parts.length < 3)
            return null;
        String code = parts[0].trim();
        String name = parts[1].trim();
        String province = parts[2].trim();
        String desc = parts.length == 4 ? parts[3].trim() : "";
        if (code.isEmpty() || code.equalsIgnoreCase("Code"))
            return null;
        return new Mountain(code, name, province, desc);
    }

    public void readFromFile() {
        FileReader fr = null;
        try {
            File f = new File(pathFile);
            if (!f.exists()) {
                System.out.println("MountainList.csv file not found!");
                return;
            }
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while ((temp = br.readLine()) != null) {
                Mountain i = dataToObject(temp);
                if (i != null)
                    this.add(i);
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("Error reading mountain file: " + ex.getMessage());
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                }
        }
    }

    public void showAll() {
        System.out.println(
                "|--------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println(
                "| Code  | Mountain Name                  | Province        | Description                                                         |");
        System.out.println(
                "|--------------------------------------------------------------------------------------------------------------------------------|");
        for (Mountain m : this) {
            System.out.println("| " + m.toString());
        }
        System.out.println(
                "|--------------------------------------------------------------------------------------------------------------------------------|");
    }
}