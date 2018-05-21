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
package com.mkdika.lwa.helper;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import static com.mkdika.lwa.helper.AppUtil.getAppProperty;
import java.sql.SQLException;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
public final class DbConnectionFactory {

    private static final String datasourceDbUrl;

    private static String datasourceDbUsername;

    private static String datasourceDbPassword;

    private static long maxConnectionAgeMillis;

    private static int maxConnectionsFree;
    
    private static volatile JdbcPooledConnectionSource connectionSource;

    static {       
        datasourceDbUrl = getAppProperty("datasource.db.url");
        datasourceDbUsername = getAppProperty("datasource.db.username");
        datasourceDbPassword = getAppProperty("datasource.db.password");
        maxConnectionAgeMillis = Long.valueOf(getAppProperty("connectionpool.max-connection-age-millis"));
        maxConnectionsFree = Integer.valueOf(getAppProperty("connectionpool.max-connections-free"));
    }
    
    private DbConnectionFactory() {
    }

    public static final JdbcPooledConnectionSource getConnection() throws SQLException {
        if (connectionSource != null) {
            return connectionSource;
        }
        synchronized (DbConnectionFactory.class) {
            if (connectionSource == null) {                
                connectionSource = new JdbcPooledConnectionSource(datasourceDbUrl,
                        datasourceDbUsername,
                        datasourceDbPassword);
                connectionSource.setMaxConnectionAgeMillis(maxConnectionAgeMillis);
                connectionSource.setMaxConnectionsFree(maxConnectionsFree);
            }
            return connectionSource;
        }
    }

}
