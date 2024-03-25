package StoreApp.StoreApp.entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Data // lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "specifications")
public class Specifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "op_cpu", columnDefinition = "nvarchar(1111)")
    private String op_cpu;

    @Column(name = "memory", columnDefinition = "nvarchar(1111)")
    private String memory;

    @Column(name = "display", columnDefinition = "nvarchar(1111)")
    private String display;

    @Column(name = "battery", columnDefinition = "nvarchar(1111)")
    private String battery;

    @Column(name = "connect", columnDefinition = "nvarchar(1111)")
    private String connect;

    @Column(name = "design", columnDefinition = "nvarchar(1111)")
    private String design;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;
}
