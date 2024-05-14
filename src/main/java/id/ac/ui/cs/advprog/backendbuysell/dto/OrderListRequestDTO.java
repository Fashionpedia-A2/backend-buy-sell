package id.ac.ui.cs.advprog.backendbuysell.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
public class OrderListRequestDTO {
    private List<String> statuses;
    private Long sellerId;
    private Long buyerId;
    private Long paymentId;
    private Date createdAtStart;
    private Date createdAtEnd;

    private Pageable pageable;
}
