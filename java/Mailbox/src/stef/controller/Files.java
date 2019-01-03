package stef.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author StevenDrea
 */
public class Files {

    private static final String slash = File.separator;
    private static final String begin = "." + slash + "user_"; // This will save the file in the current working directory. If the project is run through
    // netbeans it will save it in the Project Folder. If it is run via the .jar file, it will be saved in the location from which the java -jar command is executed
    private static final String end = "_log.txt";
    private static final String header = String.format("%-30s%-30s%-30s%s", "Date", "Sender", "Receiver", "Message Content");
    private File file = null;
    private PrintWriter logger = null;

    private String setFilename(int UID) {
        return begin + UID + end;
    }

    private void openLogger(int UID) {
        file = new File(this.setFilename(UID));
        try {
            if (file.exists()) {
                logger = new PrintWriter(new FileWriter(file, true));
            } else {
                logger = new PrintWriter(new FileWriter(file, true));
                logger.append(header);
                logger.println();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void writeToFile(String s){
        logger.append(s);
        logger.println();
    }
    
    private void closeLogger(){
        logger.flush();
        logger.close();
    }
    
    public void runLogger(int UID, String s){
        this.openLogger(UID);
        this.writeToFile(s);
        this.closeLogger();
    }
}
