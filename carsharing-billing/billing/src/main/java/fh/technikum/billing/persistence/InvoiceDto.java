package fh.technikum.billing.persistence;

import lombok.Data;

@Data
public class InvoiceDto implements DataTransferObject {
    private Long userId;
}

