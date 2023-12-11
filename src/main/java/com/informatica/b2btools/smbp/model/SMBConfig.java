package com.informatica.b2btools.smbp.model;

/**
 * The SMBConfig class represents configuration parameters for connecting to an SMB server and performing operations.
 * It encapsulates details such as server address, credentials, share information, and file/directory names.
 * @author asmishra
 * @since 11-12-2023
 */
public class SMBConfig {

    private String SERVER_ADDRESS;
    private String USERNAME;
    private String PASSWORD;
    private String DOMAIN;
    private String SHARE_NAME;
    private String FILE_NAME;

    private String DIR_NAME;

    public SMBConfig(String SERVER_ADDRESS, String USERNAME, String PASSWORD, String DOMAIN, String SHARE_NAME, String FILE_NAME, String DIR_NAME) {
        this.SERVER_ADDRESS = SERVER_ADDRESS;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.DOMAIN = DOMAIN;
        this.SHARE_NAME = SHARE_NAME;
        this.FILE_NAME = FILE_NAME;
        this.DIR_NAME = DIR_NAME;
    }

    public String getSERVER_ADDRESS() {
        return SERVER_ADDRESS;
    }

    public void setSERVER_ADDRESS(String SERVER_ADDRESS) {
        this.SERVER_ADDRESS = SERVER_ADDRESS;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getDOMAIN() {
        return DOMAIN;
    }

    public void setDOMAIN(String DOMAIN) {
        this.DOMAIN = DOMAIN;
    }

    public String getSHARE_NAME() {
        return SHARE_NAME;
    }

    public void setSHARE_NAME(String SHARE_NAME) {
        this.SHARE_NAME = SHARE_NAME;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    @Override
    public String toString() {
        return "SMBConfig{" +
                "SERVER_ADDRESS='" + SERVER_ADDRESS + '\'' +
                ", USERNAME='" + USERNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", DOMAIN='" + DOMAIN + '\'' +
                ", SHARE_NAME='" + SHARE_NAME + '\'' +
                ", FILE_NAME='" + FILE_NAME + '\'' +
                ", SHARE_NAME='" + DIR_NAME + '\'' +
                '}';
    }

    public String getDIR_NAME() {
        return DIR_NAME;
    }

    public void setDIR_NAME(String DIR_NAME) {
        this.DIR_NAME = DIR_NAME;
    }
}
