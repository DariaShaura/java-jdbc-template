package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setUserId(resultSet.getLong("users.user_id"));
        user.setBillingAddress(resultSet.getString("users.billing_address"));
        user.setFullName(resultSet.getString("users.full_name"));
        user.setLogin(resultSet.getString("users.login"));
        user.setPassword(resultSet.getString("users.password"));

        return user;
    }
}
