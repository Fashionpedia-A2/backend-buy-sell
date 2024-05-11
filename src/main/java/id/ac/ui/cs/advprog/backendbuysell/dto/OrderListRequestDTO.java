package id.ac.ui.cs.advprog.backendbuysell.dto;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

public class OrderListRequestDTO {
    private List<String> statuses;
    private String sellerId;
    private String buyerId;
    private Long paymentId;
    private Date createdAtStart;
    private Date createdAtEnd;

    private Pageable pageable;
}
