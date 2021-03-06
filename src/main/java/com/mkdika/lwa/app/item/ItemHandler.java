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
package com.mkdika.lwa.app.item;

import com.google.inject.Inject;
import io.javalin.Context;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Maikel Chandika (mkdika@gmail.com)
 */
public class ItemHandler {

    @Inject
    private ItemService itemService;

    public void getItemAll(Context ctx) throws SQLException {
        List<Item> list = itemService.findAllItem();
        if (list.size() > 0) {
            ctx.json(list).status(200);
        } else {
            ctx.json(list).status(204);
        }
    }

    public void getItemById(Context ctx) throws SQLException {
        Item item = itemService.findItemById(Integer.valueOf(ctx.param("id")));
        if (item != null) {
            ctx.json(item).status(200);
        } else {
            ctx.status(404);
        }
    }

    public void deleteItem(Context ctx) throws SQLException {
        Item item = itemService.findItemById(Integer.valueOf(ctx.param("id")));
        if (item != null) {
            itemService.deleteItem(item);
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }

    public void insertItem(Context ctx) throws SQLException {
        Item item = ctx.bodyAsClass(Item.class);
        itemService.insertItem(item);
        ctx.json(item).status(200);
    }

    public void updateItem(Context ctx) throws SQLException {
        Item item = itemService.findItemById(Integer.valueOf(ctx.param("id")));
        Item itemUpdate = ctx.bodyAsClass(Item.class);
        if (item != null) {
            itemService.updateItem(itemUpdate);
            ctx.json(itemUpdate).status(200);
        } else {
            ctx.status(404);
        }
    }

}
