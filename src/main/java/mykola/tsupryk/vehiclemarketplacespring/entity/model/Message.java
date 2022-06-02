package mykola.tsupryk.vehiclemarketplacespring.entity.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter @Setter
public class Message {

    private String from;
    private String to;
    private String subject;
    private String text;
    private Date sentDate;

}
