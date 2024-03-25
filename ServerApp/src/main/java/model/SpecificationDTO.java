package StoreApp.StoreApp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
public class SpecificationDTO {
    private int id;
    private String op_cpu;

    private String memory;

    private String display;

    private String battery;

    private String connect;

    private String design;
}
