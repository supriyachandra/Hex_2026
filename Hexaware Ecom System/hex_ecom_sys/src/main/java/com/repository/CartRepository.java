package com.repository;

import com.enums.UserMembership;
import com.model.CartItem;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertUser(User user) {
        String sql= "Insert into user(name, membership) values(?, ? )";
        jdbcTemplate.update(sql, user.getName(), user.getUserMembership().toString());
    }

    public void insertItem(CartItem item) {
        String sql= "Insert into cart(name, price, qty, user_id) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, item.getName(), item.getPrice(), item.getQty(), item.getUser().getId());
    }

    public List<CartItem> fetchAllItems() {
        String sql= "Select c.*, u.id as u_id, u.name as u_name, u.membership as u_membership "+
                " from cart c join user u on c.user_id= u.id";

        return jdbcTemplate.query(sql, new RowMapper<CartItem>() {

            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user= new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("u_name"));
                user.setUserMembership(UserMembership.valueOf(rs.getString("u_membership")));

                return new CartItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("qty"),
                        user
                );
            }
        });
    }

    public List<CartItem> fetchAllItemsByUserName(String name) {
        String sql= "Select c.*, u.id as u_id, u.name as u_name, u.membership as u_membership "+
                " from cart c join user u on c.user_id= u.id where lower(u.name)= ?";


        return jdbcTemplate.query(sql, new RowMapper<CartItem>(){
            @Nullable
            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user= new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("u_name"));
                user.setUserMembership(UserMembership.valueOf(rs.getString("u_membership")));

                return new CartItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("qty"),
                        user
                );
            }
        }, name);
    }

    public int deleteItemById(int id) {
        String sql= "delete from cart where id=?";
        return jdbcTemplate.update(sql, id);
    }
}
