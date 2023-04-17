import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // compile the code
        ProcessBuilder compileBuilder = new ProcessBuilder("javac", "-classpath", "./Out/;./lib/mysql-connector-j-8.0.32.jar", "-d", "out", "Database/*.java", "Main/*.java", "Main/Boundary/*.java", "Main/Controller/*.java", "Main/Entity/*.java");
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
        runProcess.waitFor();
        if (runProcess.exitValue() != 0) {
            System.err.println("Program exited with non-zero exit code " + runProcess.exitValue());
            System.exit(1);
        }
    }
}
