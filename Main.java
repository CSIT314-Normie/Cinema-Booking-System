import java.io.*;


// Run this to start the app
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // compile the code
        String[] cmd = {
            "javac",
            "-classpath",
            "./Out/;./lib/mysql-connector-j-8.0.32.jar",
            "-d",
            "out",

            // Core files
            "Database/DB.java",
            "Main/Driver.java", 
            
            // Main/Boundary java files
            "Main/Boundary/*.java",
            "Main/Boundary/Admin/*.java",
            "Main/Boundary/CinemaOwner/*.java",
            "Main/Boundary/Customer/*.java",
            "Main/Boundary/Manager/*.java",
            
            // Main/Controller java files
            "Main/Controller/*.java",
            "Main/Controller/Admin/*.java",
            "Main/Controller/Customer/*.java",
            "Main/Controller/Manager/*.java",

            // Main/Entity java files
            "Main/Entity/*.java"
        };

        // print out cmd as a string
         String cmdString = "";
         for (String s : cmd) {
             cmdString += s + " ";
         }

        System.out.println(cmdString);


        ProcessBuilder compileBuilder = new ProcessBuilder(cmd);
        compileBuilder.redirectErrorStream(true);
        Process compileProcess = compileBuilder.start();
        compileProcess.waitFor();
        if (compileProcess.exitValue() != 0) {
            System.err.println("Compilation failed with exit code " + compileProcess.exitValue());
            System.exit(1);
        }

        // run the program
        ProcessBuilder runBuilder = new ProcessBuilder("java", "-classpath", "./Out;./lib/mysql-connector-j-8.0.32.jar", "Main.Driver");
        runBuilder.redirectErrorStream(true);
        Process runProcess = runBuilder.start();

        // FOR DEBUGGING PURPOSE: captures the output from the program
        // try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()))) {
        //     String outputLine;
        //     while ((outputLine = outputReader.readLine()) != null) {
        //         System.out.println(outputLine);
        //     }
        // }

        runProcess.waitFor();
        if (runProcess.exitValue() != 0) {
            System.err.println("Program exited with non-zero exit code " + runProcess.exitValue());
            System.exit(1);
        }
    }
}

// Manual compilation and running
// javac -classpath "./Out/;./lib/mysql-connector-j-8.0.32.jar" -d out Database/DB.java Main/Driver.java Main/Boundary/*.java Main/Boundary/Admin/*.java Main/Boundary/CinemaOwner/*.java Main/Boundary/Customer/*.java Main/Boundary/Manager/*.java Main/Controller/*.java Main/Controller/Admin/*.java Main/Controller/Customer/*.java Main/Controller/Manager/*.java Main/Entity/*.java 
//  java -classpath "./Out;./lib/mysql-connector-j-8.0.32.jar" Main.Driver 