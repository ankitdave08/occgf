package com.listertechnologies.occgf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.listertechnologies.occgf.api.CustomerDetail;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody CustomerDetail customerDetail) {
        System.out.println("Customer: " + customerDetail);
        return customerDetail.toString();
    }

}
