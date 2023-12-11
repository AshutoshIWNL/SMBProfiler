package com.informatica.b2btools.smbp.model;

/**
 * The SMBStats class represents statistics related to SMB operations, including time taken for existence check,
 * directory check, and length check.
 * @author asmishra
 * @since 11-12-2023
 */
public class SMBStats {
    private String exists_call_time_taken;
    private String is_directory_call_time_taken;
    private String length_call_time_taken;

    public SMBStats() {

    }

    public SMBStats(String exists_call_time_taken, String is_directory_call_time_taken, String length_call_time_taken) {
        this.exists_call_time_taken = exists_call_time_taken;
        this.is_directory_call_time_taken = is_directory_call_time_taken;
        this.length_call_time_taken = length_call_time_taken;
    }

    public String getExists_call_time_taken() {
        return exists_call_time_taken;
    }

    public void setExists_call_time_taken(String exists_call_time_taken) {
        this.exists_call_time_taken = exists_call_time_taken;
    }

    public String getIs_directory_call_time_taken() {
        return is_directory_call_time_taken;
    }

    public void setIs_directory_call_time_taken(String is_directory_call_time_taken) {
        this.is_directory_call_time_taken = is_directory_call_time_taken;
    }

    public String getLength_call_time_taken() {
        return length_call_time_taken;
    }

    public void setLength_call_time_taken(String length_call_time_taken) {
        this.length_call_time_taken = length_call_time_taken;
    }

    @Override
    public String toString() {
        return "SMBStats{" +
                "exists_call_time_taken='" + exists_call_time_taken + '\'' +
                ", is_directory_call_time_taken='" + is_directory_call_time_taken + '\'' +
                ", length_call_time_taken='" + length_call_time_taken + '\'' +
                '}';
    }
}
