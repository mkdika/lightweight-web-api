/*
 * The MIT License
 *
 * Copyright 2018 Maikel Chandika <mkdika@gmail.com>
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
package com.mkdika.lwa;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mkdika.lwa.app.customer.Customer;
import com.mkdika.lwa.app.customer.CustomerCart;
import com.mkdika.lwa.app.item.Item;
import com.mkdika.lwa.config.BasicModule;
import java.sql.SQLException;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */

public class LightweightWebApiApplication {
    
    private static JdbcPooledConnectionSource connection;
        
    public static void main(String[] args) throws SQLException {
        Injector injector = Guice.createInjector(new BasicModule());
        connection = injector.getInstance(JdbcPooledConnectionSource.class);
        preInit();
    }
    
    private static void preInit() throws SQLException { 
        // drop all table & create
        TableUtils.dropTable(connection, Customer.class, true);
        TableUtils.dropTable(connection, CustomerCart.class, true);
        TableUtils.dropTable(connection, Item.class, true);
        TableUtils.createTableIfNotExists(connection, Customer.class);
        TableUtils.createTableIfNotExists(connection, CustomerCart.class);
        TableUtils.createTableIfNotExists(connection, Item.class);          
    }
    
}
