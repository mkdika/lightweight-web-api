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

import com.j256.ormlite.table.TableUtils;
import com.mkdika.lwa.app.customer.Customer;
import com.mkdika.lwa.app.customer.CustomerCart;
import com.mkdika.lwa.app.item.Item;
import com.mkdika.lwa.helper.AppUtil;
import static com.mkdika.lwa.helper.DbConnectionFactory.getConnection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */

public class LightweightWebApiApplication {
                
    public static void main(String[] args) throws SQLException {    
        preInit();
    }
    
    private static void preInit() throws SQLException { 
        // drop all table & create
        TableUtils.dropTable(getConnection(), Customer.class, true);
        TableUtils.dropTable(getConnection(), CustomerCart.class, true);
        TableUtils.dropTable(getConnection(), Item.class, true);
        TableUtils.createTableIfNotExists(getConnection(), Customer.class);
        TableUtils.createTableIfNotExists(getConnection(), CustomerCart.class);
        TableUtils.createTableIfNotExists(getConnection(), Item.class);                
    }
    
}
