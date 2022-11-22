package com.bootcamp.melifrescos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyDTO {
    private String currencyQuote;
    private String currencyBase;
    private double rate;

    public CurrencyDTO(String currencyQuote, String currencyBase, double rate) {
        this.currencyQuote = currencyQuote;
        this.currencyBase = currencyBase;
        this.rate = rate;
    }
}
