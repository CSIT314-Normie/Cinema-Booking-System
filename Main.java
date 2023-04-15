import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // compile the code
        Process compileProcess = Runtime.getRuntime().exec("javac -classpath ./Out/;./lib/mysql-connector-j-8.0.32.jar -d out Database/*.java Main/*.java Main/Boundary/*.java Main/Controller/*.java Main/Entity/*.java");
        compileProcess.waitFor();
        if (compileProcess.exitValue() != 0) {
            System.err.println("Compilation failed with exit code " + compileProcess.exitValue());
            System.exit(1);
        }

        // run the program
        Process runProcess = Runtime.getRuntime().exec("java -classpath ./Out;./lib/mysql-connector-j-8.0.32.jar Main.Driver");
        runProcess.waitFor();
        if (runProcess.exitValue() != 0) {
            System.err.println("Program exited with non-zero exit code " + runProcess.exitValue());
            System.exit(1);
        }
    }
}
