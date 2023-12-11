package com.informatica.b2btools.smbp;

import com.informatica.b2btools.smbp.exception.SMBPException;
import com.informatica.b2btools.smbp.model.SMBConfig;
import com.informatica.b2btools.smbp.model.SMBStats;
import com.informatica.b2btools.smbp.utils.SMBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SMBClientWrapper class is responsible for profiling SMB operations using SMBUtils.
 * It checks the existence of a file, verifies if a smb file is a directory, and measures the length
 * of a file, introducing sleep intervals to simulate local application activity.
 * @author asmishra
 * @since 11-12-2023
 */
public class SMBClientWrapper {

    private static final Logger logger = LoggerFactory.getLogger(SMBClientWrapper.class);

    /**
     * Profiles various SMB operations by utilizing SMBUtils cals.
     * @param smbConfig The SMB configuration.
     * @return An SMBStats object containing profiling information.
     * @throws SMBPException Thrown if an unexpected SMB exception occurs.
     */
    public static SMBStats profileSMBOperations(SMBConfig smbConfig) throws SMBPException {
        SMBStats smbStats = new SMBStats();

        if(!SMBUtils.exists(smbConfig, smbStats)) {
            logger.warn("File doesn't exist, but still calculated the time taken for the existence check");
        }

        // Introducing sleep to simulate some local application activity that might occur between SMB operations
        try {
            logger.debug("Sleeping for 1 second...");
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            //Do nothing
        }

        if(!SMBUtils.isDirectory(smbConfig, smbStats)) {
            logger.warn("Not a directory, but still calculated the time taken for the directory check");
        }

        try {
            logger.debug("Sleeping for 1 second...");
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            //Do nothing
        }

        logger.debug("Length check: " + SMBUtils.length(smbConfig, smbStats));

        return smbStats;
    }
}
