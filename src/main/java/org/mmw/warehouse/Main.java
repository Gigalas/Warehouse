package org.mmw.warehouse;

import org.mmw.warehouse.model.Category;
import org.mmw.warehouse.repository.CategoryRepository;
import org.mmw.warehouse.repository.jdbc.JdbcCategoryRepository;
import org.mmw.warehouse.util.data.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main
{
    static void main() throws SQLException
    {
        //test db
        DbConnection db = new DbConnection("jdbc:sqlite:warehouse.db");
        db.initSchema();
        try (Connection con = db.getConnection())
        {
            System.out.println("Connected: " + !con.isClosed());
        }

        //test repo categories
        CategoryRepository categories = new JdbcCategoryRepository(db);

        Category c = categories.save(new Category("Electronics", "Cables and gadgets"));
        System.out.println("id = " + c.getId());

        c.setDescription("Changed");
        categories.save(c);
        System.out.println(categories.findById(c.getId()).orElseThrow().getDescription());

        categories.findAll().forEach(System.out::println);

        categories.deleteById(c.getId());
    }
}
