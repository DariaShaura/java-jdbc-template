package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Component
public class JdbcTemplatePublicAuction implements PublicAuction {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BidMapper bidMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Bid> getUserBids(long id) {

        String query_getUserBids = "select bids.* from bids where user_id = ?";

        return jdbcTemplate.query(query_getUserBids, bidMapper, id);
    }

    @Override
    public List<Item> getUserItems(long id) {

        String query_getUserItems = "select * from items where user_id = ?";

        return jdbcTemplate.query(query_getUserItems, itemMapper, id);
    }

    @Override
    public Item getItemByName(String name) {

        String query_getItemByName = "select * from items where title like ? limit 1";

        return jdbcTemplate.queryForObject(query_getItemByName, new Object[]{"%" + name + "%"}, itemMapper);
    }

    @Override
    public Item getItemByDescription(String name) {

        String query_getItemByDescription = "select * from items where description like ? limit 1";

        return jdbcTemplate.queryForObject(query_getItemByDescription, new Object[]{"%" + name + "%"}, itemMapper);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {

        String query_getAvgItemCost = "select users.*, avg(start_price) as avg " +
                "from items left join users on items.user_id = users.user_id " +
                "group by users.user_id order by users.user_id";
        return jdbcTemplate.query(query_getAvgItemCost, new ResultSetExtractor<Map<User, Double>>() {
            @Override
            public Map<User, Double> extractData(ResultSet rs)
                    throws SQLException, DataAccessException {
                Map<User, Double> map = new HashMap<>();
                int i = 0;
                while (rs.next()) {
                    User user = userMapper.mapRow(rs, i);
                    double avgCost = rs.getDouble("avg");
                    map.put(user, avgCost);
                }
                return map;
            }
        });
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        String query_getMaxBidsForEveryItem = "SELECT items.*, bids.* " +
                "FROM items LEFT JOIN bids ON items.Item_id = bids.item_id " +
                "where bids.bid_value = (select max(b.bid_value) from bids as b " +
                "where b.item_id = items.Item_id) " +
                "order by items.item_id";

        return jdbcTemplate.query(query_getMaxBidsForEveryItem, new ResultSetExtractor<Map<Item, Bid>>() {
            @Override
            public Map<Item, Bid> extractData(ResultSet rs)
                    throws SQLException, DataAccessException {
                Map<Item, Bid> map = new HashMap<>();
                int i = 0;
                while (rs.next()) {
                    Item item = itemMapper.mapRow(rs, i);
                    Bid bid = bidMapper.mapRow(rs, i);
                    map.put(item, bid);
                }
                return map;
            }
        });
    }

    @Override
    public boolean createUser(User user) {
        String query_insertUser = "insert into users (user_id, billing_address, full_name, login, password) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                query_insertUser,
                user.getUserId(), user.getBillingAddress(), user.getFullName(), user.getLogin(), user.getPassword()
        ) > 0;
    }

    @Override
    public boolean createItem(Item item) {
        String query_insertItem = "insert into items values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                query_insertItem,
                item.getItemId(), item.getBidIncrement(), item.getBuyItNow(), item.getDescription(),
                item.getStartDate(), item.getStartPrice(), item.getStopDate(), item.getTitle(), item.getUserId()
        ) > 0;
    }

    @Override
    public boolean createBid(Bid bid) {
        String query_insertBid = "insert into bids values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                query_insertBid,
                bid.getBidId(), bid.getBidDate(), bid.getBidValue(), bid.getItemId(), bid.getUserId()
        ) > 0;
    }

    @Override
    public boolean deleteUserBids(long id) {
        String query_deleteUserBids = "delete from bids where user_id = ?";
        return jdbcTemplate.update(
                query_deleteUserBids,
                id
        ) > 0;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        String query_updateItemsStartPrice = "update items set start_price = 2*start_price where user_id = ?";
        return jdbcTemplate.update(
                query_updateItemsStartPrice, id
        ) > 0;
    }
}
