package SmartAirport.src.main.java;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogService {

 
    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator + "logs";

 
    public synchronized void writeRecord(String message) {
        try {
            String date = LocalDate.now().toString();

      
            File dir = new File(ROOT_PATH + File.separator + "system");
            if (!dir.exists()) dir.mkdirs();

            File logFile = new File(dir, "system_" + date + ".log");

       
            String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .format(LocalDateTime.now());

        
            try (FileWriter fw = new FileWriter(logFile, true)) {
                fw.write("[" + timestamp + "] " + message + System.lineSeparator());
            }

          
            System.out.println("✅ Log written to: " + logFile.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("⚠️ Error writing log: " + e.getMessage());
        }
    }

  
    public synchronized void writeRecord(String folder, String fileName, String message) {
        try {
            String date = LocalDate.now().toString();

            File dir = new File(ROOT_PATH + File.separator + folder);
            if (!dir.exists()) dir.mkdirs();

            File logFile = new File(dir, fileName + "_" + date + ".log");

       
            String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .format(LocalDateTime.now());

        
            try (FileWriter fw = new FileWriter(logFile, true)) {
                fw.write("[" + timestamp + "] " + message + System.lineSeparator());
            }

            System.out.println("✅ Log written to: " + logFile.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("⚠️ Error writing " + folder + " log: " + e.getMessage());
        }
    }
}