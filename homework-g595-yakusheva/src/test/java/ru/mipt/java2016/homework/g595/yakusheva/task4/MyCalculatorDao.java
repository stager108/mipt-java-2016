package ru.mipt.java2016.homework.g595.yakusheva.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.List;

@Repository
public class MyCalculatorDao {
    private static final Logger LOG = LoggerFactory.getLogger(MyCalculatorDao.class);

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource, false);
        initSchema();
    }

    public void initSchema() {
        LOG.trace("Initializing schema");
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS myCalculator");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS myCalculator.variables " +
                "(name VARCHAR PRIMARY KEY, value DOUBLE)");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS myCalculator.functions " +
                "(name VARCHAR PRIMARY KEY, function VARCHAR, arguments VARCHAR)");
    }


    public MyVariable loadVariable(String name) throws EmptyResultDataAccessException {
        LOG.trace("Querying for user " + name);
        return jdbcTemplate.queryForObject(
                "SELECT name, value FROM myCalculator.variables WHERE name = ?",
                new Object[]{name},
                new RowMapper<MyVariable>() {
                    @Override
                    public MyVariable mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new MyVariable(
                                rs.getString("name"),
                                rs.getDouble("value")
                        );
                    }
                }
        );
    }

    public boolean insertVariable(MyVariable variable) {
        LOG.trace("Querying for variable " + variable.getName());
        return jdbcTemplate.update("merge into myCalculator.variables KEY (name, value) values(?,?)",
                variable.getName(), variable.getValue()) == 1;
    }

    public boolean removeVariable(String variableName) {
        LOG.trace("Removing variable " + variableName);
        return jdbcTemplate.update("delete from calc.variables where name = ?", variableName) == 1;
    }
    public MyFunction loadFunction(String name) throws EmptyResultDataAccessException {
        LOG.trace("Querying for user " + name);
        return jdbcTemplate.queryForObject(
                "SELECT name, function, arguments FROM myCalculator.functions WHERE name = ?",
                new Object[]{name},
                new RowMapper<MyFunction>() {
                    @Override
                    public MyFunction mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new MyFunction(
                                rs.getString("name"),
                                rs.getString("function"),
                                Arrays.asList(rs.getString("arguments").split(","))
                        );
                    }
                }
        );
    }

    public boolean insertFunction(MyFunction function) {
        LOG.trace("Querying for function " + function.getName());
        return jdbcTemplate.update("merge into myCalculator.functions KEY (name, function, arguments) values(?,?,?)",
                function.getName(), function.getFunction(), function.getArgs()) == 1;
    }

    public boolean removeFunction(String functionName) {
        LOG.trace("Removing function " + functionName);
        return jdbcTemplate.update("DELETE FROM myCalculator.functions WHERE name = ?", functionName) == 1;
    }

    public List<String> getFunctionsNames() {
        LOG.trace("Querying for all functions" );
        try {
            return jdbcTemplate.queryForList("SELECT name FROM MyCalculator.functions", String.class);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<String> getVariablesNames() {
        LOG.trace("Querying for all variables" );
        try {
            return jdbcTemplate.queryForList("SELECT name FROM MyCalculator.variables", String.class);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

}

