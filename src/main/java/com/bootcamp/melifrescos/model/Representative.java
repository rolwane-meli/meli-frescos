package com.bootcamp.melifrescos.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "representatives")
@Getter
@Setter
@NoArgsConstructor
public class Representative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String email;

    @Column(length = 11, nullable = false)
    private String cpf;
}
