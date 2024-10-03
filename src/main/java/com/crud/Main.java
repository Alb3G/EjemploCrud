package com.crud;

import com.crud.dao.UserDAO;
import com.crud.models.User;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var dao = new UserDAO(DB.getConn());
        dao.deleteById(25);

        ArrayList<User> users = dao.findAll();
        users.forEach(System.out::println);
    }
}
