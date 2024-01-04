package com.informatica.b2btools.smbp.utils;

import com.informatica.b2btools.smbp.exception.SMBPException;
import com.informatica.b2btools.smbp.model.SMBConfig;
import com.informatica.b2btools.smbp.model.SMBStats;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The SMBUtilsV1 class provides utility methods for profiling SMB operations for SMBV1.
 * It includes functionality to check the existence of a file, verify if it's a directory,
 * and measure the length of a file using the jcifs library.
 * @author asmishra
 * @since 17-12-2023
 */
public class SMBUtilsV1 {

    private static final Logger logger = LoggerFactory.getLogger(SMBUtils.class);

    /**
     * Checks the existence of a file on an SMB share.
     * @param smbConfig The SMB configuration.
     * @param smbStats An SMBStats object to record profiling information.
     * @return True if the file exists, false otherwise.
     * @throws SMBPException Thrown if an unexpected SMB exception occurs.
     */
    public static boolean exists(SMBConfig smbConfig, SMBStats smbStats) throws SMBPException {
        logger.debug("ENTRY -> exists()");
        boolean exists = false;
        String smbFilename = getSMBFileName(smbConfig.getFILE_NAME(), smbConfig.getSERVER_ADDRESS(), smbConfig.getSHARE_NAME());
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", smbConfig.getUSERNAME(), smbConfig.getPASSWORD());
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        try {
            SmbFile smbFile = new SmbFile(smbFilename, auth);

            long smbFileEndTime = System.currentTimeMillis();
            logger.debug("Time take for SmbFile creation: " + (smbFileEndTime - startTime) + " milliseconds");

            exists = smbFile.exists();

            long existsCheckEndTime = System.currentTimeMillis();
            logger.debug("Time taken for exists check: " + (existsCheckEndTime - smbFileEndTime) + " milliseconds");

            endTime = existsCheckEndTime - startTime;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SMBPException(e);
        }
        logger.debug("Time taken for exists(): " + endTime + " milliseconds");
        smbStats.setExists_call_time_taken(String.valueOf(endTime));
        logger.debug("EXIT -> exists()");
        return exists;
    }

    private static String getSMBFileName(String fileName, String serverAddress, String shareName) {
        return "smb://" + serverAddress + "/" + shareName + "/" + fileName;
    }

    /**
     * Checks if a path on an SMB share represents a directory.
     * @param smbConfig The SMB configuration.
     * @param smbStats An SMBStats object to record profiling information.
     * @return True if the path is a directory, false otherwise.
     * @throws SMBPException Thrown if an unexpected SMB exception occurs.
     */
    public static boolean isDirectory(SMBConfig smbConfig, SMBStats smbStats) throws SMBPException {
        logger.debug("ENTRY -> isDirectory()");
        boolean isDirectory = false;
        String smbFilename = getSMBFileName(smbConfig.getDIR_NAME(), smbConfig.getSERVER_ADDRESS(), smbConfig.getSHARE_NAME());
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", smbConfig.getUSERNAME(), smbConfig.getPASSWORD());
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        try {
            SmbFile smbFile = new SmbFile(smbFilename, auth);

            long smbFileEndTime = System.currentTimeMillis();
            logger.debug("Time take for SmbFile creation: " + (smbFileEndTime - startTime) + " milliseconds");

            isDirectory = smbFile.isDirectory();

            long isDirectoryEndTime = System.currentTimeMillis();
            logger.debug("Time taken for directory check: " + (isDirectoryEndTime - smbFileEndTime) + " milliseconds");
            endTime = isDirectoryEndTime - startTime;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SMBPException(e);
        }
        logger.debug("Time taken for isDirectory(): " + endTime + " milliseconds");
        smbStats.setIs_directory_call_time_taken(String.valueOf(endTime));
        logger.debug("EXIT -> isDirectory()");
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
        logger.debug("ENTRY -> length()");
        long length = 0L;
        String smbFilename = getSMBFileName(smbConfig.getFILE_NAME(), smbConfig.getSERVER_ADDRESS(), smbConfig.getSHARE_NAME());
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", smbConfig.getUSERNAME(), smbConfig.getPASSWORD());
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        try {
            SmbFile smbFile = new SmbFile(smbFilename, auth);

            long smbFileEndTime = System.currentTimeMillis();
            logger.debug("Time take for SmbFile creation: " + (smbFileEndTime - startTime) + " milliseconds");

            length = smbFile.length();

            long lengthCheckEndTime = System.currentTimeMillis();
            logger.debug("Time taken for length check: " + (lengthCheckEndTime - smbFileEndTime) + " milliseconds");

            endTime = lengthCheckEndTime - startTime;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SMBPException(e);
        }
        logger.debug("Time taken for length(): " + endTime + " milliseconds");
        smbStats.setLength_call_time_taken(String.valueOf(endTime));
        logger.debug("EXIT -> length()");
        return length;
    }

}
