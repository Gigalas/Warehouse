package org.mmw.warehouse;

import org.mmw.warehouse.util.data.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main
{
    static void main() throws SQLException
    {
        DbConnection db = new DbConnection("jdbc:sqlite:warehouse.db");
        db.initSchema();
        try (Connection con = db.getConnection())
        {
            System.out.println("Connected: " + !con.isClosed());
        }
    }
}
