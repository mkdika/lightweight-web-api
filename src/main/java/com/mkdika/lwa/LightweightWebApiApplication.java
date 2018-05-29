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
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mkdika.lwa.app.customer.Customer;
import com.mkdika.lwa.app.customer.CustomerCart;
import com.mkdika.lwa.app.item.Item;
import com.mkdika.lwa.config.BasicModule;
import io.javalin.Javalin;
import java.sql.SQLException;
import lombok.Getter;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
@Getter
public class LightweightWebApiApplication {

    @Inject
    private JdbcPooledConnectionSource connection;

    @Inject
    @Named("javalin.server.port")
    private int appServerPort;

    public static void main(String[] args) throws SQLException {
        Injector injector = Guice.createInjector(new BasicModule());
        LightweightWebApiApplication app  = injector.getInstance(LightweightWebApiApplication.class);
        JdbcPooledConnectionSource connection = app.getConnection();
        
        preInitDatabase(connection);

        // start JAVALIN
        Javalin javalinServer = Javalin.create()
                .port(app.getAppServerPort())
                .start();
    }

    private static void preInitDatabase(JdbcPooledConnectionSource connection) throws SQLException {
        // drop all table & create
        TableUtils.dropTable(connection, Customer.class, true);
        TableUtils.dropTable(connection, CustomerCart.class, true);
        TableUtils.dropTable(connection, Item.class, true);
        TableUtils.createTableIfNotExists(connection, Customer.class);
        TableUtils.createTableIfNotExists(connection, CustomerCart.class);
        TableUtils.createTableIfNotExists(connection, Item.class);
    }
}
