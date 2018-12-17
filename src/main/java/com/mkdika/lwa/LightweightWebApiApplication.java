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
import com.mkdika.lwa.config.GuiceBasicModule;
import com.mkdika.lwa.init.InitDatabase;
import com.mkdika.lwa.router.ApplicationRouter;
import io.javalin.Javalin;
import java.sql.SQLException;
import java.util.function.Consumer;
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
        Injector injector = Guice.createInjector(new GuiceBasicModule());
        LightweightWebApiApplication starter = injector.getInstance(LightweightWebApiApplication.class);
        ApplicationRouter appRouter = injector.getInstance(ApplicationRouter.class);

        // init database structure
        executeRunner(starter.getConnection(),InitDatabase::createDbStructure);
        
        // init database dummy data
        executeRunner(starter.getConnection(), InitDatabase::initDbDataFromSQL);

        // start JAVALIN
        Javalin app = Javalin.create()
                .port(starter.getAppServerPort())
                .start();

        
        // load all API handler to Javalin app
        executeRunner(app, appRouter::loadApiHandler);
    }

    private static <T> void executeRunner(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }
}
