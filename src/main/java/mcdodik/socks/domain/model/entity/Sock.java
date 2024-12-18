package mcdodik.socks.domain.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "sock")
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "cotton_percentage", nullable = false)
    private Integer cottonPercentage = 0;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

}