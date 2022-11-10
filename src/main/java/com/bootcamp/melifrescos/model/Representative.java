package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Email
    @Column(length = 45, nullable = false)
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @CPF
    @Column(length = 11, nullable = false)
    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @OneToOne(mappedBy = "representative")
    @JsonIgnoreProperties("representative")
    private Warehouse warehouse;
}
