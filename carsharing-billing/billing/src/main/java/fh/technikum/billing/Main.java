package fh.technikum.billing;

import fh.technikum.billing.persistence.EmergencyStatusDto;
import fh.technikum.billing.persistence.VehicleStatusDto;
import fh.technikum.billing.queues.InvoiceQueueHandler;
import fh.technikum.billing.queues.QueueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fh.technikum.billing.config.Config.*;

public class Main {


    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting Billing Service");

        QueueHandler<VehicleStatusDto> statusUpdate = new QueueHandler<>(STATUS_UPDATE_QUEUE_NAME, VehicleStatusDto.class);
        QueueHandler<EmergencyStatusDto> emergencyUpdate = new QueueHandler<>(EMERGENCY_UPDATE_QUEUE_NAME, EmergencyStatusDto.class);
        InvoiceQueueHandler invoice = new InvoiceQueueHandler(CREATE_INVOICE_QUEUE_NAME);

        statusUpdate.start();
        emergencyUpdate.start();
        invoice.start();
    }
}
