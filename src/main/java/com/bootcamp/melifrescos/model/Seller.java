package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Parent;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("products")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Column(length = 11, nullable = false)
    @NotBlank(message = "O celular é obrigatório.")
    @Pattern(regexp = "^[1-9]{2}(?:[2-8]|9[1-9])[0-9]{3}[0-9]{4}$", message = "Número fora do padrao.")
    private String phoneNumber;

    @Email
    @Column(length = 45, nullable = false)
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @CNPJ
    @Column(length = 20, nullable = false)
    @NotBlank(message = "O CNPJ é obrigatório.")
    private String cnpj;

    @OneToMany(mappedBy = "seller")
    @JsonIgnoreProperties("seller")
    private List<Product> products;
}
