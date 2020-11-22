import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;     // ResultSet can store a table of data from the db
    private int primaryKey;     // internal variable to keep track of PK
    private final String databaseName = "inventory_db";
    private final String tableName = "inventory_table";

    /*
     * Constructor
     * connects to the database
     */
    public DBConnect(){
        // connects to database
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, "root", "");
            stmt = conn.createStatement();
        }catch(Exception e){
            System.out.println("Error: " + e);
        }

        // initialize PK
        initializePrimaryKey();
    }

    /*
     * Helper method to increment primary key
     */
    private void incrementPrimaryKey(){
        primaryKey++;
    }

    /*
     * Helper method to initialize primary key
     * gets the maximum value of id and sets the next one
     * as the primary key
     * defaults to 1000
     */
    private void initializePrimaryKey(){

        // select the maximum value of id
        String q = "SELECT MAX(`id`) AS `hpk` FROM `" + tableName + "`";
        try {
            // execute
            rset = stmt.executeQuery(q);
            if(rset.next()!=false){
                primaryKey = rset.getInt("hpk") + 1;
            }
            else{
                // default
                primaryKey = 1000;
            }
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    // helper method to get primaryKey
    public int getPrimaryKey(){
        return primaryKey;
    }

    /*
     * returns all rows from the table as an ArrayList of
     * Products
     */
    public ArrayList<Product> getData(){
        ArrayList<Product> objArr = new ArrayList<Product>();
        try{
            // select everything from the table
            String q = "select * from " + tableName;
            rset = stmt.executeQuery(q);

            //System.out.println("Records from db");
            while(rset.next()){
                int id = rset.getInt("id");
                String name = rset.getString("name");
                int quantity = rset.getInt("quantity");
                float ppi = rset.getFloat("price_per_item");
                String supplier = rset.getString("supplier");
                int purchased_qty = rset.getInt("purchased_qty");
                int sold_qty = rset.getInt("sold_qty");

                Product n = new Product(id,name,quantity,ppi,supplier,purchased_qty,sold_qty);
                objArr.add(n);
            }
        }catch(Exception e){
            System.out.println("Error: " + e);
        }

        return objArr;
    }

    /*
     * returns a specific Product identified by id from the table
     */
    public Product getProduct(int id){
        Product n = null;
        try{
            // select row with id = id
            String q = "select * from `" + tableName +"` WHERE `id` = " + id;

            // execute
            rset = stmt.executeQuery(q);

            if(rset.next() != false){
                int i = rset.getInt("id");
                String name = rset.getString("name");
                int quantity = rset.getInt("quantity");
                float ppi = rset.getFloat("price_per_item");
                String supplier = rset.getString("supplier");
                int purchased_qty = rset.getInt("purchased_qty");
                int sold_qty = rset.getInt("sold_qty");

                n = new Product(i,name,quantity,ppi,supplier,purchased_qty,sold_qty);
            }

        }catch (SQLException t){
            t.printStackTrace();
        }
        return n;
    }


    /*
     * adds a Product into the table
     * id for the new Product is the current primaryKey value
     */
    void addProduct(Product p){
        p.setId(primaryKey);

        // insert a row into the table
        String q = "INSERT INTO `" + tableName +"`(`id`, `name`, `quantity`, `price_per_item`, `supplier`, `purchased_qty`, `sold_qty`)";
        q += " VALUES " + p.getAsString();

        try {
            // execute
            stmt.executeUpdate(q);

        } catch (SQLException t) {
            t.printStackTrace();
        }

        // increment primaryKey
        incrementPrimaryKey();
    }

    /*
     * updates a specific Product to values of
     * newP, in the table
     */
    void updateProduct(Product newP){
        // update the row with id = id
        String q = "UPDATE `"+tableName +"` "
                +"SET `name`='" + newP.getName()
                +"',`quantity`=" + newP.getQuantity()
                +",`price_per_item`=" + newP.getPpi()
                +",`supplier`='" + newP.getSupplier()
                +"',`purchased_qty`=" + newP.getPurchasedQuantity()
                +",`sold_qty`=" + newP.getPurchasedQuantity()
                +" WHERE id=" + newP.getId();
        try {
            // execute
            stmt.executeUpdate(q);

        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    /*
     * deletes a specific Product identified by its id
     */
    void deleteProduct(int id){
        // query statement
        String q = "DELETE FROM `" + tableName + "` WHERE id=" + id;
        try {
            // execute
            stmt.executeUpdate(q);

        } catch (SQLException t) {
            t.printStackTrace();
        }
    }


    /*
     * deletes all rows from the table
     */
    public void clearTable(){
        // query statement
        String q = "DELETE FROM `" + tableName + "` WHERE 1";
        try {
            // execute
            stmt.executeUpdate(q);

        } catch (SQLException t) {
            t.printStackTrace();
        }
    }


    /*
     * returns the total number of rows, which correspond
     * to the total types of products
     */
    public int getTotalProducts(){
        // query statement
        String q = "SELECT COUNT(*) AS `nr` FROM `" + tableName + "`";

        int res = 0;
        try {
            // execute
            rset = stmt.executeQuery(q);
        } catch (SQLException t) {
            t.printStackTrace();
        }
        try {
            if(rset.next() != false) {
                res = rset.getInt("nr");
            }
        } catch (SQLException t) {
            t.printStackTrace();
        }
        return res;
    }

    /*
     * returns the total number of product items
     */
    public int getTotalNumProducts(){
        String q = "SELECT SUM(`quantity`) AS `npi` FROM `" + tableName + "`";
        int res = 0;
        try {
            rset = stmt.executeQuery(q);
        } catch (SQLException t) {
            t.printStackTrace();
        }
        try {
            if(rset.next() != false) {
                res = rset.getInt("npi");
            }
        } catch (SQLException t) {
            t.printStackTrace();
        }
        return res;
    }


    /*
     * returns the info about total types of Products and total
     * number of product items in the table
     */
    public String getInfo(){
        String s = "Total categories of products: " + getTotalProducts();
        s += "\nTotal number of product items: " + getTotalNumProducts();
        return s;
    }

}
