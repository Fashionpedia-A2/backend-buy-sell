package id.ac.ui.cs.advprog.backendbuysell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class BuySellController {
    @RequestMapping("")
    public String main(){
        return "uglyhtml";
    }
}
