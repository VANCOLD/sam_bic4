package fh.technikum.billing.config;

public final class Config {

    /* I know this isn't best practice but for the exercise this would be the easiest way to store this type of data */
    public static final String host     = "localhost";
    public static final String username = "guest";
    public static final String password = "guest";
    public static final String STATUS_UPDATE_QUEUE_NAME = "Status_Update";
    public static final String EMERGENCY_UPDATE_QUEUE_NAME = "Emergency_Update";
    public static final String CREATE_INVOICE_QUEUE_NAME = "Create_Invoice";
}
