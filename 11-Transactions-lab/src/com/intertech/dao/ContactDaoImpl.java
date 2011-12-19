package com.intertech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.intertech.domain.Contact;

@Repository("contactDao")
public class ContactDaoImpl implements ContactDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Contact getContact(Long id) {
        String sql = "select * from contact where id = ?";
        System.out.println("DAO: returning contact id " + id);
        return jdbcTemplate.queryForObject(sql, new ContactRowMapper(), id);
    }

    @Override
    public List<Contact> getContacts() {
        String sql = "select * from contact";
        System.out.println("DAO: returning all contacts");
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public long saveContact(Contact contact) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("first_name", contact.getFirstName());
        params.put("last_name", contact.getLastName());
        params.put("date_of_birth", contact.getDateOfBirth());
        params.put("married", contact.isMarried());
        params.put("children", contact.getChildren());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("contact").usingGeneratedKeyColumns("id").usingColumns("first_name", "last_name", "date_of_birth", "married", "children");
        System.out.println("DAO: saved " + contact);
        return (Long) insert.executeAndReturnKeyHolder(params).getKey();
    }

    @Override
    public void updateContact(Contact contact) {
        String sql = "update contact set first_name = ?, last_name = ?, date_of_birth = ?, married = ?, children = ? where id = ?";
        System.out.println("DAO: updated " + contact);
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getDateOfBirth(), contact.isMarried(), contact.getChildren(), contact.getId());
    }

    @Override
    public void deleteContact(Long id) {
        String sql = "delete from contact where id = ?";
        System.out.println("DAO: removing contact id " + id);
        jdbcTemplate.update(sql, id);
    }

    public class ContactRowMapper implements RowMapper<Contact> {

        public Contact mapRow(ResultSet rs, int index) throws SQLException {
            Contact contact = new Contact();
            contact.setId(rs.getLong("id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setDateOfBirth(rs.getDate("date_of_birth"));
            contact.setChildren(rs.getInt("children"));
            contact.setMarried(rs.getBoolean("married"));
            return contact;
        }
    }
}
