package com.CurrencyConverter.controller;

import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CurrencyConverter.service.CurrencyService;

@Controller
@RequestMapping("/")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public String showConverterPage(Model model) {
        Set<String> currencies = currencyService.getAllCurrencies();
        model.addAttribute("currencies", currencies);
        return "index";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam String from, 
                          @RequestParam String to, 
                          @RequestParam double amount, 
                          Model model) {
        double convertedAmount = currencyService.convertCurrency(from, to, amount);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("amount", amount);
        model.addAttribute("convertedAmount", convertedAmount);
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        return "index";
    }
}

