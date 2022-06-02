package mykola.tsupryk.vehiclemarketplacespring.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FeedbackAddRequest {
    private Long comentatorId;
    private Long userId;
    private String text;

}
