package com.informatica.b2btools.smbp;

import com.informatica.b2btools.smbp.exception.SMBPException;
import com.informatica.b2btools.smbp.model.SMBConfig;
import com.informatica.b2btools.smbp.model.SMBStats;
import com.informatica.b2btools.smbp.utils.ReportGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The SMBProfiler class is the entry point of the SMBProfiler Application.
 * It reads configuration properties, utilizes SMBClientWrapper to get SMBStats instances and builds the report using ReportGenerator.
 * @author asmishra
 * @since 08-12-2023
 */

public class SMBProfiler {

    private static final Logger logger = LoggerFactory.getLogger(SMBProfiler.class);
    private static final long ITERATIONS = System.getProperty("smbp.iterations") != null ? Long.parseLong(System.getProperty("smbp.iterations")) : 1;

    private static final String VERSION = System.getProperty("smbp.version") != null ? System.getProperty("smbp.version") : "V2_3";

    private static final String SMB_SERVER_ADDRESS_KEY = "smb.server_address";
    private static final String SMB_USERNAME_KEY = "smb.username";
    private static final String SMB_PASSWORD_KEY = "smb.password";
    private static final String SMB_DOMAIN_KEY = "smb.domain";
    private static final String SMB_SHARE_NAME_KEY = "smb.share_name";
    private static final String SMB_FILE_NAME_KEY = "smb.file_name";
    private static final String SMB_DIR_NAME_KEY = "smb.dir_name";


    /**
     * The main method for starting SMB profiling. It reads configuration properties,
     * performs SMB operations iteratively, generates reports, and prints profiling results.
     * @param args Command-line arguments (expects the path to the configuration file).
     * @throws InterruptedException Thrown if the thread sleep is interrupted.
     */
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            logger.error("Invalid usage!");
            logger.info("Correct usage: SMBProfiler <CONFIG_FILE>");
            logger.info("Example: MFTProjectAnalyzer /root/smbp.properties");
            System.exit(-1);
        }

        String PROPS_FILE = args[0];

        logger.debug("Building SMBConfig object");
        SMBConfig smbConfig = loadSMBProperties(PROPS_FILE);
        logger.debug("SMBConfig object built successfully.\n" + smbConfig);

        logger.info("Starting SMBProfiler" + VERSION.toUpperCase());
        long startTime = System.currentTimeMillis();
        String statsFile = getFileName();
        List<SMBStats> statsList = new ArrayList<>();
        for (int i = 1; i <= ITERATIONS; i++) {
            SMBStats stats = null;
            try {
                stats = SMBClientWrapper.profileSMBOperations(smbConfig, VERSION);
            } catch (SMBPException e) {
                handleException(e);
            }
            statsList.add(stats);

            // Introducing sleep just for the sake of it?
            Thread.sleep(1000L);
        }
        try {
            ReportGenerator.generateReport(statsList, statsFile);
        } catch (SMBPException e) {
            handleException(e);
        }
        long endTime = System.currentTimeMillis() - startTime;
        logger.info("Profling completed, total duration: " + endTime + "ms");
        System.out.println("Profiling done, exiting...");

    }

    /**
     * Handles exceptions by logging an error message, printing a user-friendly message,
     * and terminating the application with an exit code of -1.
     * @param e The exception to handle.
     */
    private static void handleException(Exception e) {
        logger.error("An unexpected error occurred", e);
        System.err.println("Sorry, an unexpected error occurred. Please contact support.");
        System.exit(-1);
    }

    /**
     * Generates a timestamp-based file name for storing profiling statistics.
     * @return The generated file name.
     */
    private static String getFileName() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yy_HH_mm_ss_SSS");
        return System.getProperty("user.dir") + "/SMBSTATS_" + currentDateTime.format(formatter) + ".out";
    }

    /**
     * Loads SMB configuration properties from a file and creates an SMBConfig object.
     * @param propsFilePath The path to the configuration file.
     * @return The SMBConfig object representing the loaded configuration.
     */
    private static SMBConfig loadSMBProperties(String propsFilePath) {
        String SERVER_ADDRESS = "";
        String USERNAME = "";
        String PASSWORD = "";
        String DOMAIN = "";
        String SHARE_NAME = "";
        String FILE_NAME = "";
        String DIR_NAME = "";

        logger.debug("Reading from props file: " + propsFilePath);
        try (InputStream inputStream = Files.newInputStream(Paths.get(propsFilePath))) {
            Properties smbProperties = new Properties();
            smbProperties.load(inputStream);
            SERVER_ADDRESS = smbProperties.getProperty(SMB_SERVER_ADDRESS_KEY);
            USERNAME = smbProperties.getProperty(SMB_USERNAME_KEY);
            PASSWORD = smbProperties.getProperty(SMB_PASSWORD_KEY);
            DOMAIN = smbProperties.getProperty(SMB_DOMAIN_KEY);
            SHARE_NAME = smbProperties.getProperty(SMB_SHARE_NAME_KEY);
            FILE_NAME = smbProperties.getProperty(SMB_FILE_NAME_KEY);
            DIR_NAME = smbProperties.getProperty(SMB_DIR_NAME_KEY);
        } catch (IOException e) {
            handleException(e);
        }
        return new SMBConfig(SERVER_ADDRESS, USERNAME, PASSWORD, DOMAIN, SHARE_NAME, FILE_NAME, DIR_NAME);
    }
}