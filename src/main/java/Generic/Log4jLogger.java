package Generic;

import org.apache.log4j.Logger;

public class Log4jLogger {

    private static final Logger log = Logger.getLogger(Log4jLogger.class);
    private static final Logger debugLog = Logger.getLogger("debugLogger");
    private static final Logger resultLog = Logger.getLogger("reportsLogger");

    public static Logger getLogger(){
        return  log;
    }
}

