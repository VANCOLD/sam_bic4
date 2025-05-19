package fh.technikum.carsharing.persistence.entity.dto;

import lombok.Data;

@Data
public class InvoiceDto implements DataTransferObject {
    private Long userId;
}
