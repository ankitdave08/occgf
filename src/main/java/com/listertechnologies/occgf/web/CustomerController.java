package com.listertechnologies.occgf.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.listertechnologies.occgf.api.OccCustomerDetail;
import com.listertechnologies.occgf.core.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody OccCustomerDetail customerDetail) {
        System.out.println("Customer: " + customerDetail);
        customerService.transformAndSend(customerDetail);
        return customerDetail.toString();
    }

    @RequestMapping(value = "/dump", method = RequestMethod.POST)
    @ResponseBody
    public String dump(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestBody = IOUtils.toString(request.getInputStream(), "UTF-8");
            System.out.println(requestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Success";
    }

}
