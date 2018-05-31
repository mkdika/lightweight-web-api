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
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
public class CustomerServiceImpl implements CustomerService {
    
    @Inject
    private JdbcPooledConnectionSource connection;

    private final Dao<Customer, Integer> customerDao;

    public CustomerServiceImpl() throws SQLException {
        customerDao = DaoManager.createDao(connection, Customer.class);
    }

    @Override
    public List<Customer> findAllCustomer() throws SQLException {
        return customerDao.queryForAll();
    }

    @Override
    public Customer findCustomerById(int id) throws SQLException {
        return customerDao.queryForId(id);
    }

    @Override
    public void insertCustomer(Customer c) throws SQLException {
        customerDao.create(c);
    }

    @Override
    public void updateCustomer(Customer c) throws SQLException {
        customerDao.update(c);
    }

    @Override
    public void deleteCustomer(Customer c) throws SQLException {
        customerDao.delete(c);
    }
}
