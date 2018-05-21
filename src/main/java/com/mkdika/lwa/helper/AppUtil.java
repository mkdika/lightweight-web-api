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

import com.mkdika.lwa.app.customer.CustomerService;
import com.mkdika.lwa.app.customer.CustomerServiceImpl;
import com.mkdika.lwa.app.item.ItemService;
import com.mkdika.lwa.app.item.ItemServiceImpl;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 *
 * Double-check locking Singleton class
 */
public final class AppUtil {

    private static volatile CustomerService customerServiceInstance;

    private static volatile ItemService itemServiceInstance;
    
    private static volatile ResourceBundle rb;

    private AppUtil() {
    }

    public static final CustomerService getCustomerService() {
        if (customerServiceInstance != null) {
            return customerServiceInstance;
        }
        synchronized (AppUtil.class) {
            if (customerServiceInstance == null) {
                try {
                    customerServiceInstance = new CustomerServiceImpl();
                } catch (SQLException ex) {
                    Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return customerServiceInstance;
        }
    }

    public static final ItemService getItemService() {
        if (itemServiceInstance != null) {
            return itemServiceInstance;
        }
        synchronized (AppUtil.class) {
            if (itemServiceInstance == null) {
                try {
                    itemServiceInstance = new ItemServiceImpl();
                } catch (SQLException ex) {
                    Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return itemServiceInstance;
        }
    }
    
    public static final String getAppProperty(String keyProp) {
        if (rb != null) {
            return rb.getString(keyProp);
        }
        synchronized (AppUtil.class) {
            if (rb == null) {
                rb = ResourceBundle.getBundle("application");
            }
            return rb.getString(keyProp);
        }                
    }

}
