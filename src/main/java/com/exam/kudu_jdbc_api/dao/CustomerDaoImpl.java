package com.exam.kudu_jdbc_api.dao;

import com.cloudera.impala.jdbc41.DataSource;
import com.exam.kudu_jdbc_api.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerDaoImpl extends JdbcDaoSupport implements CustomerDao{

    DataSource dataSource;

    @Value("${spring.datasource.url}")
    private String url;

    @PostConstruct
    private void initialize(){
        dataSource = new com.cloudera.impala.jdbc41.DataSource();
        dataSource.setURL(url);
        setDataSource(dataSource);
    }

    @Override
    public void insertCustomer(Customer cus) {
        String sql = "INSERT INTO customer_info " +
                "(id,name,age,address,salary,created_at,updated_at) VALUES (?, ?,?, ?,?, ?,?)" ;
//        getJdbcTemplate()
//                .update(sql,
//                        new Object[]{
//                                cus.getId(), cus.getName(), cus.getAge(), cus.getAddress(), cus.getSalary(),
//                                cus.getCreatedAt(), cus.getUpdatedAt()
//                        });
        getJdbcTemplate().update(sql,
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        Customer customer = cus;
                        ps.setInt(1, customer.getId());
                        ps.setString(2, customer.getName());
                        ps.setInt(3, customer.getAge());
                        ps.setString(4, customer.getAddress());
                        ps.setInt(5, customer.getSalary());
                        ps.setLong(6, customer.getCreatedAt());
                        ps.setLong(7, customer.getUpdatedAt());
                    }
                }
        );
    }

    @Override
    public boolean insertCustomers(List<Customer> customers) {
        try {
            String sql = "INSERT INTO customer_info (id,name,age,address,salary,created_at,updated_at) VALUES (?, ?,?, ?,?, ?,?)";
            getJdbcTemplate()
                    .batchUpdate(sql,
                            new BatchPreparedStatementSetter() {

                                public void setValues(PreparedStatement ps, int i) throws SQLException {
                                    Customer customer = customers.get(i);
                                    ps.setInt(1, customer.getId());
                                    ps.setString(2, customer.getName());
                                    ps.setInt(3, customer.getAge());
                                    ps.setString(4, customer.getAddress());
                                    ps.setInt(5, customer.getSalary());
                                    ps.setLong(6, customer.getCreatedAt());
                                    ps.setLong(7, customer.getUpdatedAt());
                                }

                                public int getBatchSize() {
                                    System.out.println(customers.size() + " statements inserted into database.");
                                    return customers.size();
                                }
                            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer_info order by id";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Customer> result = new ArrayList<Customer>();
        for(Map<String, Object> row : rows){
            Customer customer = new Customer();
            customer.setId((Integer) row.get("id"));
            customer.setName((String) row.get("name"));
            customer.setAge((Integer) row.get("age"));
            customer.setAddress((String) row.get("address"));
            customer.setSalary((Integer) row.get("salary"));
            customer.setCreatedAt((Long) row.get("created_at"));
            customer.setUpdatedAt((Long) row.get("updated_at"));

            result.add(customer);
        }

        return result;
    }

    @Override
    public Customer getCustomerById(Integer custid) {
        Customer customer = null;
        try {
            String sql = "SELECT * FROM customer_info WHERE id = ?";
            customer = getJdbcTemplate()
                    .queryForObject(sql,
                            new Object[]{custid},
                            (rs, rwNumber) -> {
                                Customer customer1 = new Customer();
                                customer1.setId( rs.getInt("id"));
                                customer1.setName( rs.getString("name"));
                                customer1.setAge( rs.getInt("age"));
                                customer1.setAddress( rs.getString("address"));
                                customer1.setSalary( rs.getInt("salary"));
                                customer1.setCreatedAt(rs.getLong("created_at"));
                                customer1.setUpdatedAt( rs.getLong("updated_at"));
                                return customer1;
                            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer updateCustomer(Customer cus) {
        String sql = "UPDATE customer_info SET name = ?, age=?, address=?, salary=?, updated_at=? WHERE id=?" ;
        getJdbcTemplate()
                .update(sql,
                        new Object[]{
                                cus.getName(), cus.getAge(), cus.getAddress(), cus.getSalary(), cus.getUpdatedAt(),
                                cus.getId(),
                        });
        return null;
    }

    @Override
    public void deleteCustomer(Integer custId) {
        //DELETE FROM kudu_table WHERE c1 = 100;
        String sql = "DELETE FROM customer_info WHERE id=?" ;
        getJdbcTemplate()
                .update(sql,
                        new Object[]{
                                custId
                        });
    }
}
