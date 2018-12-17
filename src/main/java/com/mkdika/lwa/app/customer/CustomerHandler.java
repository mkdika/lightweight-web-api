/*
 * The MIT License
 *
 * Copyright 2018 Maikel Chandika <mkdika@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mkdika.lwa.app.customer;

import com.google.inject.Inject;
import com.mkdika.lwa.app.item.Item;
import io.javalin.Context;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
public class CustomerHandler {
    
    @Inject
    private CustomerService customerService;
    
    public void getCustomerAll(Context ctx) throws SQLException {
        List<Customer> list = customerService.findAllCustomer();
        if (list.size() > 0) {
            ctx.json(list).status(200);
        } else {
            ctx.json(list).status(204);
        }
    }

    public void getCustomerById(Context ctx) throws SQLException {
        Customer customer = customerService.findCustomerById(Integer.valueOf(ctx.param("id")));
        if (customer != null) {
            ctx.json(customer).status(200);
        } else {
            ctx.status(404);
        }
    }

    public void deleteCustomer(Context ctx) throws SQLException {
        Customer customer = customerService.findCustomerById(Integer.valueOf(ctx.param("id")));
        if (customer != null) {
            customerService.deleteCustomer(customer);
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }

    public void insertCustomer(Context ctx) throws SQLException {
        Customer customer = ctx.bodyAsClass(Customer.class);
        customerService.insertCustomer(customer);
        ctx.json(customer).status(200);
    }

    public void updateCustomer(Context ctx) throws SQLException {
        Customer customer = customerService.findCustomerById(Integer.valueOf(ctx.param("id")));
        Customer customerUpdate = ctx.bodyAsClass(Customer.class);
        if (customer != null) {
            customerService.updateCustomer(customerUpdate);
            ctx.json(customerUpdate).status(200);
        } else {
            ctx.status(404);
        }
    }

    
}
