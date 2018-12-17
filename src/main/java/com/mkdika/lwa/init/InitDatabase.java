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
package com.mkdika.lwa.init;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mkdika.lwa.app.customer.Customer;
import com.mkdika.lwa.app.customer.CustomerCart;
import com.mkdika.lwa.app.item.Item;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
public class InitDatabase {

    public static void createDbStructure(JdbcPooledConnectionSource connSource) {
        try {
            // drop all table & create
            TableUtils.dropTable(connSource, Customer.class, true);
            TableUtils.dropTable(connSource, CustomerCart.class, true);
            TableUtils.dropTable(connSource, Item.class, true);
            TableUtils.createTableIfNotExists(connSource, Customer.class);
            TableUtils.createTableIfNotExists(connSource, CustomerCart.class);
            TableUtils.createTableIfNotExists(connSource, Item.class);
        } catch (SQLException ex) {

        }
    }

    public static void initDbDataFromSQL(JdbcPooledConnectionSource connSource) {
        try {
            Dao dao = DaoManager.createDao(connSource, Item.class);
            URL url = Resources.getResource("data.sql");
            String sql = Resources.toString(url, Charsets.UTF_8);
            dao.executeRawNoArgs(sql);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(InitDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
