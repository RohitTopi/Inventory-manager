import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.synth.ColorType;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainGUI extends JPanel {

    private JPanel jpMain;  // the main layout
    private JScrollPane scrollPane;
    private ArrayList<Product> all_products;
    final String[] columnNames = {"id", "name", "quantity",
            "price per item", "supplier", "purchased qty", "sold qty"};

    // database connection object
    private DBConnect database;

    private JPanel heading;

    // add product data fields
    private JTextField nameFieldAdd;
    private JTextField quantityFieldAdd;
    private JTextField ppiFieldAdd;
    private JTextField supplierFieldAdd;
    private JTextField purchasedQtyFieldAdd;
    private JTextField soldQtyFieldAdd;

    // update product data fields
    private JTextField idFieldUpd;
    private JTextField nameFieldUpd;
    private JTextField quantityFieldUpd;
    private JTextField ppiFieldUpd;
    private JTextField supplierFieldUpd;
    private JTextField purchasedQtyFieldUpd;
    private JTextField soldQtyFieldUpd;

    // for deleting product
    private JTextField idFieldDel;

    private JButton addBn;
    private JButton delBn;
    private JButton updBn;
    private JButton getSpecificDataBn;
    private JButton clearInventoryBn;

    private JTextPane textArea;

    private JTable table;


    /*
     * constructor
     */
    public MainGUI() {
        super(new GridLayout(3,0, 2, 2));

        // holds reference to main layout
        jpMain = this;
        jpMain.setBackground(Color.white);

        // database connection object, to be used throughout
        database = new DBConnect();
        all_products = database.getData();

        // create and add the heading
        heading = createHeadingPanel("INVENTORY MANAGEMENT");
        add(heading);

        // create and add the control panel
        JPanel controlPanel = new JPanel(new GridLayout(1,2));
        controlPanel.setSize(new Dimension(50,50));
        controlPanel.setBackground(new Color(0xFFFFFF));

        // tabbed pane will have four panels
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(0xDDEFDF));

        // panel1 to add a new Product
        JComponent panel1 = makeSubPanelAddProduct();
        panel1.setBackground(new Color(0xFFFFFF));
        tabbedPane.addTab("Add Products", null, panel1, "Add a product");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        // panel2 to update an existing Product
        JComponent panel2 = makeSubPanelUpdateProduct();
        panel2.setBackground(new Color(0xFFFFFF));
        tabbedPane.addTab("Update products", null, panel2, "Update an existing product");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // panel3 to delete an existing Product
        JComponent panel3 = makeSubPanelDeleteProduct();
        panel3.setBackground(new Color(0xFFFFFF));
        tabbedPane.addTab("Delete products", null, panel3, "Delete an existing product");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        // panel4 to clear database
        JComponent panel4 = makeSubPanelSettings();
        panel4.setBackground(new Color(0xFFFFFF));
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Clear All", null, panel4,
                "Clear All");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        // add tabbed pane
        controlPanel.add(tabbedPane);

        // create and add info panel
        JPanel infoPanel = createInfoPanel();
        controlPanel.add(infoPanel);

        // add this to main layout
        add(controlPanel);

        // initialize the table
        all_products = database.getData();
        table = new JTable(convertToObject(all_products), columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    /*
     * creates the heading Jpanel and returns it.
     * it inserts a heading text into the panel
     */
    private JPanel createHeadingPanel(String title){

        // main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setSize(0,10);
        panel.setMinimumSize(new Dimension(1024,60));
        panel.setBackground(new Color(16,166,206));

        // create and add text to main panel
        JLabel headingText = new JLabel(title, JLabel.CENTER);
        headingText.setForeground(Color.white);
        headingText.setFont(new Font("Sans-serif", Font.PLAIN, 18));
        panel.add(headingText);


        return panel;
    }

    /*
     * helper method to create a JPanel containing a label
     * and a JTextField and return the JPanel
     */
    private JPanel createLabelledField(String label, JTextField textField){
        //  create the main panel
        JPanel p = new JPanel(new GridLayout(1,2));
        p.setBackground(new Color(0xFFFFFF));

        // add text label
        JLabel t = new JLabel(label);
        t.setBackground(new Color(0xFFFFFF));
        p.add(t);

        // add textField
        p.add(textField);

        return p;
    }

    /*
     * creates the sub panel for the 'add' functionality, and adds the input fields
     * and buttons and returns it.
     */
    private JPanel makeSubPanelAddProduct(){

        // main sub-panel
        JPanel subPanel = new JPanel(new GridLayout(1,2));
        subPanel.setBackground(new Color(0xFFFFFF));

        // subPanel1 for input fields
        JPanel subPanel1 = new JPanel(new FlowLayout());
        subPanel1.setBackground(new Color(0xFFFFFF));
        nameFieldAdd = new JTextField("", 16);
        quantityFieldAdd = new JTextField("", 16);
        ppiFieldAdd = new JTextField("", 16);
        supplierFieldAdd = new JTextField("", 16);
        purchasedQtyFieldAdd = new JTextField("", 16);
        soldQtyFieldAdd = new JTextField("", 16);

        // add all label-field pairs to subPanel1
        subPanel1.add(createLabelledField("name: ", nameFieldAdd));
        subPanel1.add(createLabelledField("quantity: ", quantityFieldAdd));
        subPanel1.add(createLabelledField("price per item: ", ppiFieldAdd));
        subPanel1.add(createLabelledField("supplier: ", supplierFieldAdd));
        subPanel1.add(createLabelledField("purchased qty: ", purchasedQtyFieldAdd));
        subPanel1.add(createLabelledField("sold qty: ", soldQtyFieldAdd));
        subPanel.add(subPanel1);

        // subPanel2 to add buttons and related text
        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.PAGE_AXIS));
        subPanel2.setBackground(new Color(0xFFFFFF));

        addBn = new JButton("Add product");
        addBn.setBackground(new Color(0x53576F));
        addBn.setForeground(new Color(0xE3FCF8));

        // action handler to create an entry in the database
        addBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // take input and create a Product
                Product p = new Product(database.getPrimaryKey(),
                        nameFieldAdd.getText(),
                        Integer.parseInt(quantityFieldAdd.getText()),
                        Float.parseFloat(ppiFieldAdd.getText()),
                        supplierFieldAdd.getText(),
                        Integer.parseInt(purchasedQtyFieldAdd.getText()),
                        Integer.parseInt(soldQtyFieldAdd.getText())
                );

                // add the product to the database
                database.addProduct(p);

                // update view
                refreshDataTable();
            }
        });
        subPanel2.add(addBn);
        subPanel2.add(new JLabel("add a new product and click 'Add product'"));
        subPanel.add(subPanel2);


        return subPanel;
    }


    /*
     * creates the sub panel for the 'update' functionality
     * adds the input fields and buttons and returns it.
     */
    private JPanel makeSubPanelUpdateProduct(){
        // create the main sub-panel
        JPanel subPanel = new JPanel(new GridLayout(1,2));
        subPanel.setBackground(new Color(0xFFFFFF));

        JPanel subPanel1 = new JPanel(new FlowLayout());
        subPanel1.setBackground(new Color(0xFFFFFF));

        // initialize the fields
        idFieldUpd = new JTextField("", 16);
        nameFieldUpd = new JTextField("", 16);
        quantityFieldUpd = new JTextField("", 16);
        ppiFieldUpd = new JTextField("", 16);
        supplierFieldUpd = new JTextField("", 16);
        purchasedQtyFieldUpd = new JTextField("", 16);
        soldQtyFieldUpd = new JTextField("", 16);

        // add label and corresponding fields to subPanel1
        subPanel1.add(createLabelledField("id: ", idFieldUpd));
        subPanel1.add(createLabelledField("name: ", nameFieldUpd));
        subPanel1.add(createLabelledField("quantity: ", quantityFieldUpd));
        subPanel1.add(createLabelledField("price per item: ", ppiFieldUpd));
        subPanel1.add(createLabelledField("supplier: ", supplierFieldUpd));
        subPanel1.add(createLabelledField("purchased qty: ", purchasedQtyFieldUpd));
        subPanel1.add(createLabelledField("sold qty: ", soldQtyFieldUpd));
        subPanel.add(subPanel1);

        // create subPanel2
        JPanel subPanel2 = new JPanel();
        subPanel2.setBackground(new Color(0xFFFFFF));
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.PAGE_AXIS));

        // button to fetch existing Product data
        getSpecificDataBn = new JButton("Get data for id");
        getSpecificDataBn.setBackground(new Color(0x53576F));
        getSpecificDataBn.setForeground(new Color(0xE3FCF8));

        // set an action listener to fetch a Product on button click
        getSpecificDataBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sid = Integer.parseInt(idFieldUpd.getText());
                Product p = database.getProduct(sid);
                nameFieldUpd.setText(p.getName());
                quantityFieldUpd.setText(Integer.toString(p.getQuantity()));
                ppiFieldUpd.setText(Float.toString(p.getPpi()));
                supplierFieldUpd.setText(p.getSupplier());
                purchasedQtyFieldUpd.setText(Integer.toString(p.getPurchasedQuantity()));
                soldQtyFieldUpd.setText(Integer.toString(p.getSoldQuantity()));
            }
        });
        subPanel2.add(getSpecificDataBn);

        // create and add update button
        updBn = new JButton("Update product");
        updBn.setBackground(new Color(0x53576F));
        updBn.setForeground(new Color(0xE3FCF8));

        // set actionListener to update Product data
        updBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // take input and create a Product
                Product p = new Product(Integer.parseInt(idFieldUpd.getText()),
                        nameFieldUpd.getText(),
                        Integer.parseInt(quantityFieldUpd.getText()),
                        Float.parseFloat(ppiFieldUpd.getText()),
                        supplierFieldUpd.getText(),
                        Integer.parseInt(purchasedQtyFieldUpd.getText()),
                        Integer.parseInt(soldQtyFieldUpd.getText())
                );

                // update the product in the database
                database.updateProduct(p);

                // update view
                refreshDataTable();
            }
        });

        // add the update button and the related text
        subPanel2.add(updBn);
        subPanel2.add(new JLabel("Enter the id of an existing product "));
        subPanel2.add(new JLabel("and click 'get data' to get the "));
        subPanel2.add(new JLabel("current data, and then modify"));
        subPanel2.add(new JLabel("it and click 'Update product'"));
        subPanel.add(subPanel2);

        return subPanel;
    }

    /*
     * creates the sub panel for the 'delete' functionality
     * adds the input fields and buttons and returns it.
     */
    private JPanel makeSubPanelDeleteProduct(){
        // main sub-panel
        JPanel subPanel = new JPanel(new GridLayout(1,2));
        subPanel.setBackground(new Color(0xFFFFFF));

        JPanel subPanel1 = new JPanel(new FlowLayout());
        subPanel1.setBackground(new Color(0xFFFFFF));

        // for id of the product to delete
        idFieldDel = new JTextField("", 16);
        subPanel1.add(createLabelledField("id:", idFieldDel));
        subPanel.add(subPanel1);

        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.PAGE_AXIS));
        subPanel2.setBackground(new Color(0xFFFFFF));

        delBn = new JButton("Delete product");
        delBn.setBackground(new Color(0x53576F));
        delBn.setForeground(new Color(0xE3FCF8));

        // deletes the product with id from idFieldDel
        delBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.deleteProduct(Integer.parseInt(idFieldDel.getText()));
                refreshDataTable();
            }
        });

        // add components to subPanel2 and then to subPanel
        subPanel2.add(delBn);
        subPanel2.add(new JLabel("Enter the id of an existing product to delete it"));
        subPanel.add(subPanel2);

        return subPanel;
    }

    /*
     * creates the sub panel for the 'clear database' functionality
     * adds the button and returns it.
     */
    private JPanel makeSubPanelSettings(){
        // main sub-panel
        JPanel subPanel = new JPanel(new GridLayout(1,2));
        subPanel.setBackground(new Color(0xFFFFFF));

        // subPanel2 holds the buttons and the text
        JPanel subPanel2 = new JPanel();
        subPanel2.setBackground(new Color(0xFFFFFF));
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.PAGE_AXIS));

        clearInventoryBn = new JButton("Clear Inventory");
        clearInventoryBn.setBackground(new Color(0xE84D4D));
        clearInventoryBn.setForeground(new Color(0xE3FCF8));

        // clears the database on button-click
        clearInventoryBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.clearTable();
                refreshDataTable();
            }
        });

        // add components to subPanel2 and then to subPanel
        subPanel2.add(clearInventoryBn);
        subPanel2.add(new JLabel("clears all the products. (Non-reversible)"));
        subPanel.add(subPanel2);

        return subPanel;
    }

    /*
     * creates the panel for the info
     * adds info text fetched from the DBConnect
     */
    private JPanel createInfoPanel(){

        // create the infoPanel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(0xECF5FF));

        // create and add a TextPane to the infoPanel
        textArea = new JTextPane();
        textArea.setBackground(new Color(0xECF5FF));
        textArea.setText(database.getInfo());
        infoPanel.add(textArea);

        return infoPanel;
    }

    /*
     * Refreshes info panel, called every time an action occurs
     * The action includes the  button clicks
     */
    private void refreshInfoPanel(){
        textArea.setText(database.getInfo());
    }


    /*
     * Refreshes the data table in Scroll panel, called every time an action occurs
     * The action includes the  button clicks
     */
    private void refreshDataTable(){
        jpMain.remove(scrollPane);

        all_products = database.getData();
        table = new JTable(convertToObject(all_products), columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(table);


        jpMain.add(scrollPane);

        refreshInfoPanel();

        jpMain.setVisible(false);
        jpMain.setVisible(true);
    }

    /*
     * Helper method to convert the ArrayList of Products to
     * Object[][] array and return it
     */
    private Object[][] convertToObject(ArrayList<Product> a)
    {
        Object[][] o = new Object[a.size()][7];
        for(int i = 0; i<a.size(); i++){
            o[i] = a.get(i).toObject();
        }
        return o;
    }


    /**
     * Creates the GUI and display it
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Inventory manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel heading = new JLabel("Inventory manager");
        frame.add(heading);


        //Create and set up the content pane.
        MainGUI newContentPane = new MainGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread: creating and
        // showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
