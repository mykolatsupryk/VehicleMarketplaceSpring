package mykola.tsupryk.vehiclemarketplacespring.entity;


import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Message {


    private Long buyerId;
    private Long ownerId;
    private String message;

}
