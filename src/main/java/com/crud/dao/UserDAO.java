package com.crud.dao;

import com.crud.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class UserDAO implements DAO<User> {
    private static Connection connection = null;
    private static final String INSERT_INTO_USER = "INSERT INTO Usuario (email, password, is_admin) values (?,?,?);";
    private static final String UPDATE_USER = "UPDATE Usuario set email = ?, password = ?, is_admin = ? where id = ?;";
    private static final String DELETE_USER = "DELETE FROM Usuario where id = ?;";

    public UserDAO(Connection conn) { connection = conn;}

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try(var ps = connection.prepareStatement("SELECT * FROM Usuario;")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(Integer id) {
        User ret = null;
        try(var ps = connection.prepareStatement("SELECT * FROM Usuario WHERE id = ?;")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ret = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(ret);
    }

    @Override
    public void save(User user) {
        try(var ps = connection.prepareStatement(INSERT_INTO_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.getIs_admin());
            if(ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try(var ps = connection.prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.getIs_admin());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try(var ps = connection.prepareStatement(DELETE_USER)) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try(var ps = connection.prepareStatement(DELETE_USER)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
