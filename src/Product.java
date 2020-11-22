import javax.swing.event.InternalFrameEvent;

public class Product{
    private int id;
    private String name;
    private int quantity;
    private float ppi;  // price per item
    private String supplier;
    private int purchased_qty;
    private int sold_qty;

    /*
     * constructor, initializes the values
     */
    public Product(int id, String name, int quantity, float ppi,
                   String supplier, int purchased_qty, int sold_qty) {
        this.id = id;
        this.name = name;
        this. quantity = quantity;
        this.ppi = ppi;
        this.supplier = supplier;
        this.purchased_qty = purchased_qty;
        this.sold_qty = sold_qty;
    }


    // accessors
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPpi() {
        return ppi;
    }

    public String getSupplier() {
        return supplier;
    }

    public int getPurchasedQuantity() {
        return purchased_qty;
    }

    public int getSoldQuantity() {
        return sold_qty;
    }


    // modifiers
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPpi(float ppi) {
        this.ppi = ppi;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setPurchasedQuantity(int purchased_qty) {
        this.purchased_qty = purchased_qty;
    }

    public void setSoldQuantity(int sold_qty) {
        this.sold_qty = sold_qty;
    }


    /*
     * converts the Product to a Object[] and returns it
     */
    public Object[] toObject() {
        Object[] t = new Object[7];
        t[0] = this.id;
        t[1] = this.name;
        t[2] = this.quantity;
        t[3] = this.ppi;
        t[4] = this.supplier;
        t[5] = this.purchased_qty;
        t[6] = this.sold_qty;
        return t;
    }


    /*
     *  returns the string representation of the Product
     */
    public String getAsString() {
        String s = "(";
        s += Integer.toString(this.id) + ", ";
        s += "'" + this.name + "', ";
        s += Integer.toString(this.quantity) + ", ";
        s += Float.toString(this.ppi) + ", ";
        s += "'" + supplier + "', ";
        s += Integer.toString(this.purchased_qty) + ", ";
        s += Integer.toString(this.sold_qty) + ")";
        return s;
    }

    // prints the Products to console
    public void print() {
        System.out.println("Object: ");
        System.out.print(" {id: " + this.id);
        System.out.print(" name: " + this.name);
        System.out.println(" qty: " + this.quantity);
        System.out.print(" ppi: " + this.ppi);
        System.out.print(" supplier: " + this.supplier);
        System.out.print(" purchased qty: " + this.purchased_qty);
        System.out.println(" sold qty: " + this.sold_qty + " }");
    }
}
