package com.informatica.b2btools.smbp.utils;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.informatica.b2btools.smbp.exception.SMBPException;
import com.informatica.b2btools.smbp.model.SMBConfig;
import com.informatica.b2btools.smbp.model.SMBStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The SMBUtils class provides utility methods for profiling SMB operations.
 * It includes functionality to check the existence of a file, verify if it's a directory,
 * and measure the length of a file using the hierynomus SMBJ library.
 * @author asmishra
 * @since 08-12-2023
 */
public class SMBUtils {

    private static final Logger logger = LoggerFactory.getLogger(SMBUtils.class);

    /**
     * Checks the existence of a file on an SMB share.
     * @param smbConfig The SMB configuration.
     * @param smbStats An SMBStats object to record profiling information.
     * @return True if the file exists, false otherwise.
     * @throws SMBPException Thrown if an unexpected SMB exception occurs.
     */
    public static boolean exists(SMBConfig smbConfig, SMBStats smbStats) throws SMBPException {
        logger.debug("In exists()");
        boolean exists = false;
        SMBClient client = new SMBClient();
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        try ( Connection connection = client.connect(smbConfig.getSERVER_ADDRESS())) {
            long connectEndTime = System.currentTimeMillis();
            logger.debug("Time taken to connect: " + (connectEndTime - startTime) + " milliseconds");

            AuthenticationContext ac = new AuthenticationContext(smbConfig.getUSERNAME(), smbConfig.getPASSWORD().toCharArray(), smbConfig.getDOMAIN());
            Session session = connection.authenticate(ac);

            long authEndTime = System.currentTimeMillis();
            logger.debug("Time taken for authentication: " + (authEndTime - connectEndTime) + " milliseconds");

            try (DiskShare share = (DiskShare) session.connectShare(smbConfig.getSHARE_NAME())) {
                long shareConnectEndTime = System.currentTimeMillis();
                logger.debug("Time taken to connect to share: " + (shareConnectEndTime - authEndTime) + " milliseconds");

                exists = share.folderExists(smbConfig.getFILE_NAME()) || share.fileExists(smbConfig.getFILE_NAME());

                long existsCheckEndTime = System.currentTimeMillis();
                logger.debug("Time taken for exists check: " + (existsCheckEndTime - shareConnectEndTime) + " milliseconds");
                endTime = existsCheckEndTime - startTime;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SMBPException(e);
        } finally {
            client.close();
        }
        logger.debug("Time taken for exits(): " + endTime + " milliseconds");
        smbStats.setExists_call_time_taken(String.valueOf(endTime));
        logger.debug("Out exists()");
        return exists;
    }

    /**
     * Checks if a path on an SMB share represents a directory.
     * @param smbConfig The SMB configuration.
     * @param smbStats An SMBStats object to record profiling information.
     * @return True if the path is a directory, false otherwise.
     * @throws SMBPException Thrown if an unexpected SMB exception occurs.
     */
    public static boolean isDirectory(SMBConfig smbConfig, SMBStats smbStats) throws SMBPException {
        logger.debug("In isDirectory()");
        boolean isDirectory = false;
        SMBClient client = new SMBClient();
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        try (Connection connection = client.connect(smbConfig.getSERVER_ADDRESS())) {
            long connectEndTime = System.currentTimeMillis();
            logger.debug("Time taken to connect: " + (connectEndTime - startTime) + " milliseconds");

            AuthenticationContext ac = new AuthenticationContext(smbConfig.getUSERNAME(), smbConfig.getPASSWORD().toCharArray(), smbConfig.getDOMAIN());
            Session session = connection.authenticate(ac);

            long authEndTime = System.currentTimeMillis();
            logger.debug("Time taken for authentication: " + (authEndTime - connectEndTime) + " milliseconds");

            try (DiskShare share = (DiskShare) session.connectShare(smbConfig.getSHARE_NAME())) {
                long shareConnectEndTime = System.currentTimeMillis();
                logger.debug("Time taken to connect to share: " + (shareConnectEndTime - authEndTime) + " milliseconds");

                isDirectory = share.folderExists(smbConfig.getDIR_NAME());

                long existsCheckEndTime = System.currentTimeMillis();
                logger.debug("Time taken for isDirectory check: " + (existsCheckEndTime - shareConnectEndTime) + " milliseconds");
                endTime = existsCheckEndTime - startTime;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SMBPException(e);
        } finally {
            client.close();
        }
        logger.debug("Time taken for isDirectory(): " + endTime + " milliseconds");
        smbStats.setIs_directory_call_time_taken(String.valueOf(endTime));
        logger.debug("Out isDirectory()");
        return isDirectory;
    }

    /**
     * Measures the length of a file on an SMB share.
     * @param smbConfig The SMB configuration.
     * @param smbStats An SMBStats object to record profiling information.
     * @return The length of the file in bytes.
     * @throws SMBPException Thrown if an unexpected SMB exception occurs.
     */
    public static long length(SMBConfig smbConfig, SMBStats smbStats) throws SMBPException {
        logger.debug("In length()");
        long length = 0L;
        SMBClient client = new SMBClient();
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        try ( Connection connection = client.connect(smbConfig.getSERVER_ADDRESS())) {
            long connectEndTime = System.currentTimeMillis();
            logger.debug("Time taken to connect: " + (connectEndTime - startTime) + " milliseconds");

            AuthenticationContext ac = new AuthenticationContext(smbConfig.getUSERNAME(), smbConfig.getPASSWORD().toCharArray(), smbConfig.getDOMAIN());
            Session session = connection.authenticate(ac);

            long authEndTime = System.currentTimeMillis();
            logger.debug("Time taken for authentication: " + (authEndTime - connectEndTime) + " milliseconds");

            try (DiskShare share = (DiskShare) session.connectShare(smbConfig.getSHARE_NAME())) {
                long shareConnectEndTime = System.currentTimeMillis();
                logger.debug("Time taken to connect to share: " + (shareConnectEndTime - authEndTime) + " milliseconds");

                length = share.getFileInformation(smbConfig.getFILE_NAME()).getStandardInformation().getEndOfFile();

                long existsCheckEndTime = System.currentTimeMillis();
                logger.debug("Time taken for length check: " + (existsCheckEndTime - shareConnectEndTime) + " milliseconds");
                endTime = existsCheckEndTime - startTime;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SMBPException(e);
        } finally {
            client.close();
        }
        logger.debug("Time taken for exits(): " + endTime + " milliseconds");
        smbStats.setLength_call_time_taken(String.valueOf(endTime));
        logger.debug("Out length()");
        return length;
    }

}
