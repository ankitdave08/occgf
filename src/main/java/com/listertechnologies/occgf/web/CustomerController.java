package com.listertechnologies.occgf.web;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.listertechnologies.occgf.api.CustomerDetail;
import com.listertechnologies.occgf.core.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody CustomerDetail customerDetail) {
        System.out.println("Customer: " + customerDetail);
        return customerDetail.toString();
    }
    
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() throws ClientProtocolException, IOException {
        new CustomerService().sendCustomerData();
        return "Success";
    }

}
