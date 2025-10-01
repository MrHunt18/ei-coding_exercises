class Logger {
    private static Logger instance;
    private Logger() {}
    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
    public void log(String msg) { System.out.println("LOG: " + msg); }
}

public class LoggerDemo {
    public static void main(String[] args) {
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();
        log1.log("Only one logger exists!");
        System.out.println(log1 == log2); // true
    }
}
