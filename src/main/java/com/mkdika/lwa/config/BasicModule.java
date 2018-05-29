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
package com.mkdika.lwa.config;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.mkdika.lwa.app.customer.CustomerService;
import com.mkdika.lwa.app.customer.CustomerServiceImpl;
import com.mkdika.lwa.app.item.ItemService;
import com.mkdika.lwa.app.item.ItemServiceImpl;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), loadProperties());
        bind(CustomerService.class).to(CustomerServiceImpl.class).in(Scopes.SINGLETON);
        bind(ItemService.class).to(ItemServiceImpl.class).in(Scopes.SINGLETON);
        bind(JdbcPooledConnectionSource.class).toProvider(DataSourceProvider.class).in(Scopes.SINGLETON);
    }

    static class DataSourceProvider implements Provider<JdbcPooledConnectionSource> {

        private final String datasourceDbUrl;
        private final String datasourceDbUsername;
        private final String datasourceDbPassword;
        private final long maxConnectionAgeMillis;
        private final int maxConnectionsFree;

        @Inject
        public DataSourceProvider(@Named("datasource.db.url") final String datasourceDbUrl,
                @Named("datasource.db.username") final String datasourceDbUsername,
                @Named("datasource.db.password") final String datasourceDbPassword,
                @Named("connectionpool.max-connection-age-millis") final long maxConnectionAgeMillis,
                @Named("connectionpool.max-connections-free") final int maxConnectionsFree) {
            this.datasourceDbUrl = datasourceDbUrl;
            this.datasourceDbUsername = datasourceDbUsername;
            this.datasourceDbPassword = datasourceDbPassword;
            this.maxConnectionAgeMillis = maxConnectionAgeMillis;
            this.maxConnectionsFree = maxConnectionsFree;
        }

        @Override
        public JdbcPooledConnectionSource get() {          
            try {
                final JdbcPooledConnectionSource dataSource = new JdbcPooledConnectionSource(datasourceDbUrl,
                        datasourceDbUsername,
                        datasourceDbPassword);
                dataSource.setMaxConnectionAgeMillis(maxConnectionAgeMillis);
                dataSource.setMaxConnectionsFree(maxConnectionsFree);
                return dataSource;
            } catch (SQLException ex) {
                Logger.getLogger(BasicModule.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    private Map<String, String> loadProperties() {
        ResourceBundle rb = ResourceBundle.getBundle("application");
        Map<String, String> mapProp = new HashMap<>();
        rb.keySet().forEach((s) -> {
            mapProp.put(s, rb.getString(s));
        });
        return mapProp;
    }
}
