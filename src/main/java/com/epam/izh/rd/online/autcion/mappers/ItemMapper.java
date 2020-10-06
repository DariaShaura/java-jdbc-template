package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        Item item = new Item();

        item.setItemId(resultSet.getLong("items.item_id"));
        item.setBidIncrement(resultSet.getDouble("items.bid_increment"));
        item.setBuyItNow(resultSet.getBoolean("items.buy_it_now"));
        item.setDescription(resultSet.getString("items.description"));
        item.setStartDate(new java.sql.Date(resultSet.getDate("items.start_date").getTime()).toLocalDate());
        item.setStartPrice(resultSet.getDouble("items.start_price"));
        item.setStopDate(new java.sql.Date(resultSet.getDate("items.stop_date").getTime()).toLocalDate());
        item.setTitle(resultSet.getString("items.title"));
        item.setUserId(resultSet.getLong("items.user_id"));

        return item;
    }
}
