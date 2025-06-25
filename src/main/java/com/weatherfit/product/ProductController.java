package com.weatherfit.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@Controller
public class ProductController {

    @GetMapping("/product-list")
    public String productList(){
        return "product/productList";
    }

    @GetMapping("/search-list")
    public String searchList(){
        return "product/searchList";
    }
}
