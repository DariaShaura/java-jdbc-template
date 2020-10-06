package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Bid;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BidMapper implements RowMapper<Bid> {

    @Override
    public Bid mapRow(ResultSet resultSet, int i) throws SQLException {
        Bid bid = new Bid();

        bid.setBidId(resultSet.getLong("bids.bid_id"));
        bid.setBidDate(new java.sql.Date(resultSet.getDate("bids.bid_date").getTime()).toLocalDate());
        bid.setBidValue(resultSet.getDouble("bids.bid_value"));
        bid.setItemId(resultSet.getLong("bids.item_id"));
        bid.setUserId(resultSet.getLong("bids.user_id"));

        return bid;
    }
}
