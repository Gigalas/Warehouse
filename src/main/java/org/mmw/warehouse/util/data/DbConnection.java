package org.mmw.warehouse.util.data;

import org.mmw.warehouse.util.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates connections to the SQLite database and prepares its schema.
 * <p>
 * Repositories receive an instance of this class in their constructor and call
 * {@link #getConnection()} for every operation. The caller must close the connection.
 */
public class DbConnection
{
    private final String url;
    private static final String SCHEMA_RESOURCE = "/schema.sql";

    /**
     * Creates a connection factory for the given database.
     *
     * @param url the JDBC url, for example {@code jdbc:sqlite:warehouse.db}
     */
    public DbConnection(String url)
    {
        this.url = url;
    }

    /**
     * Opens a new connection and enables foreign key checks for it
     * (SQLite disables them by default).
     * The caller is responsible for closing the connection.
     *
     * @return a new open connection
     * @throws DataAccessException if the connection cannot be opened
     */
    public Connection getConnection()
    {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url);
            try(Statement st = con.createStatement())
            {
                st.execute("PRAGMA foreign_keys = ON");
            }
            return con;
        }
        catch (SQLException e)
        {
            if (con != null)
            {
                try {
                    con.close();
                } catch (SQLException ignored) {}
            }
            throw new DataAccessException("Failed to open database connection: " + url, e);
        }
    }

    /**
     * Creates all tables and indexes from {@code schema.sql}.
     * It is safe to call this method on every application start,
     * because the schema uses {@code IF NOT EXISTS}.
     *
     * @throws DataAccessException if the schema cannot be read or executed
     */
    public void initSchema()
    {
        String script = readSchema();
        try(Connection con = getConnection(); Statement st = con.createStatement())
        {
            for (String sql : splintStatements(script)){
                st.execute(sql);
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Failed to initialize the database schema", e);
        }
    }

    /**
     * Splits an SQL script into single statements: removes {@code --} comments,
     * cuts the text at semicolons and skips empty parts.
     *
     * @param script the whole SQL script
     * @return the list of statements without trailing semicolons
     */
    static List<String> splintStatements(String script)
    {
        StringBuilder withoutComments = new StringBuilder();
        for (String line : script.split("\n")){
            int comment = line.indexOf("--");
            withoutComments.append(comment >= 0 ? line.substring(0, comment) : line).append('\n');
        }

        List<String> statements = new ArrayList<>();
        for (String part : withoutComments.toString().split(";")){
            String sql = part.trim();
            if (!sql.isEmpty()){
                statements.add(sql);
            }
        }
        return statements;
    }

    /** Reads schema.sql from the classpath (src/main/resources). */
    private String readSchema()
    {
        try(InputStream in = DbConnection.class.getResourceAsStream(SCHEMA_RESOURCE))
        {
            if (in == null){
                throw new DataAccessException("Resource not found: " + SCHEMA_RESOURCE);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            throw new DataAccessException("Failed to read " + SCHEMA_RESOURCE, e);
        }
    }
}
