package com.bootcamp.melifrescos.model;

import com.bootcamp.melifrescos.enums.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sectors")
@Getter
@Setter
@NoArgsConstructor
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double capacity;
    
    @Enumerated(EnumType.STRING)
    private Type type;
}
