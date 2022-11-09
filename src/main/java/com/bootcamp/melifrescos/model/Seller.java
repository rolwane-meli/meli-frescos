package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Column(length = 11, nullable = false)
    @NotBlank(message = "O celular é obrigatório.")
    private String phoneNumber;

    @Email
    @Column(length = 45, nullable = false)
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @CNPJ
    @Column(length = 14, nullable = false)
    @NotBlank(message = "O CPF é obrigatório.")
    private String cnpj;

    @OneToMany(mappedBy = "seller")
    @JsonIgnoreProperties("seller")
    private List<Product> products;
}
