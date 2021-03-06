
package ShoppingCartApplication_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
//SELLER PAGE
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import shoppingcartapplication_main.Product;
import shoppingcartapplication_main.ShoppingCartSystem;

/**
 * A GUI class for the Seller Page.
 */

public class SellerPage
{
    static JFrame frame;
    DefaultTableModel dm; 
    JTable table;
    JTable addTable;
    DefaultTableCellRenderer centerRenderer;
    ArrayList<String> productNames = new ArrayList();
    DefaultTableCellRenderer descriptionRenderer = new DefaultTableCellRenderer();
    
    
    public SellerPage()
    {
       frame = new JFrame();    //Create the main frame  
      
    }
    
     /**
     * Displays seller page when active user account is of type seller.
     */
    
    public void display()
    {
        
        //Make panels
        JPanel mainPanel = new JPanel();    //panel for table
        JPanel northPanel = new JPanel();   //Big panel for north, containing the logo panel and buttonPanel
        JPanel southPanel = new JPanel();   //panel for below the main panel
        JPanel buttonPanel = new JPanel();   //panel for displaying log in and financial summary buttons
        JPanel scrollBarPanel = new JPanel();   //panel for creating scroll bar
        JPanel removeButtonPanel = new JPanel();    //panel for displaying remove buttons
        JPanel logoPanel = new JPanel();
        
        //Make scrollbar
        JScrollBar verticalBar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);

        //Set layouts for panels
        mainPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new FlowLayout());
        southPanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        scrollBarPanel.setLayout(new BorderLayout());
        removeButtonPanel.setLayout(new BorderLayout());
        logoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        //Configure colors
        mainPanel.setBackground(new Color(70, 179, 43)); //set color
        northPanel.setBackground(new Color(70, 179, 43)); //set color
        southPanel.setBackground(new Color(70, 179, 43)); //set color
        buttonPanel.setBackground(new Color(70, 179, 43)); //set color
        northPanel.setBackground(new Color(70, 179, 43)); //set color
        logoPanel.setBackground(new Color(70, 179, 43)); //set color
        
        //Make and add logo
        String name = ShoppingCartSystem.getActiveSeller().getUsername().substring(0, 1).toUpperCase() + ShoppingCartSystem.getActiveSeller().getUsername().substring(1);
        JLabel logo = new JLabel("Hello, " + name);
        logo.setFont(logo.getFont().deriveFont(33.0f));
        logoPanel.add(logo);
        
        //Make buttons
        JButton logout = new JButton("Logout");
        JButton financialSummary = new JButton("Financial Summary");
        JButton AddProduct = new JButton("Add Product");
        logout.setPreferredSize(new Dimension(150,75));
        financialSummary.setPreferredSize(new Dimension(150,75));
        
        
        //Populate buttons
        buttonPanel.add(financialSummary);
        buttonPanel.add(logout);
        
        //Populate northPanel
        northPanel.add(logoPanel);
        northPanel.add(Box.createRigidArea(new Dimension(600,5)));  //LOGO GOES HERE
        northPanel.add(buttonPanel);
        
        //Add scrollbar
        scrollBarPanel.add(verticalBar);
        
        //Create default table model
        DefaultTableModel dm = generateTable();
      
        //Create the Table
       table = new JTable();
               
       table.getTableHeader().setReorderingAllowed(false);
       table.setOpaque(false);
       
       
       //Create the bottom table
       addTable = new JTable();
       addTable.getTableHeader().setReorderingAllowed(false);
       DefaultTableModel addTableModel = generateAddTable();   //generate model
       
       addTable.setModel(addTableModel);    //add table model
     
       table.setModel(dm);
       
       table.getColumn("Button").setCellRenderer(new ButtonRenderer());
       table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
       table.getColumn("Description").setCellRenderer(new ButtonRenderer());
       table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
        
        //Create the scrollpane
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(70, 179, 43));
        scroll.getViewport().setOpaque(true);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        
       //table.setDefaultRenderer(String.class, centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        
        
        //Populate MainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(400,400)));
        mainPanel.add(scroll, BorderLayout.CENTER);
        
        
        //Populate SouthPanel
        
        southPanel.add(AddProduct);
        southPanel.add(addTable);
        
        mainPanel.add(southPanel,BorderLayout.SOUTH );
        //Make JFrame
        
        
        frame.getContentPane().setBackground(Color.green);  //set background color
        frame.setLayout(new BorderLayout());
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //fullscreen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //close
        //frame.add(scroll, BorderLayout.CENTER );
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(mainPanel);   //Add main panel
        //frame.add(southPanel, BorderLayout.SOUTH);
        frame.setSize(1000,700);
        frame.pack();       //pack
        frame.setLocationRelativeTo(null);  //set position
        frame.setVisible(true); //set visible
        
        // Logout Button Controller (Annonymous)
        logout.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false); 
          ShoppingCartSystem.clearActiveSeller();
          ShoppingCartSystem.loginPage.display();
        }
    } );
        
        financialSummary.addActionListener(new ActionListener()
       {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false); 
          
            try
            {
                ShoppingCartSystem.finanSummaryPage.display(ShoppingCartSystem.getActiveSeller());
            } catch (IOException ex)
            {
                Logger.getLogger(SellerPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } );
        
        AddProduct.addActionListener(new ActionListener()   //action listener for add button
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               
               String name = (String) addTable.getValueAt(0,0);
               String price =  (String) addTable.getValueAt(0,1);
               String description = (String) addTable.getValueAt(0, 2);
               String Stock = (String) addTable.getValueAt(0, 3);
               String cost = (String) addTable.getValueAt(0, 4);
               
               Product product = new Product(name, Double.parseDouble(price), description, Integer.parseInt(Stock),Double.parseDouble(cost) , ShoppingCartSystem.getActiveSeller().getUsername() ,0);
               ShoppingCartSystem.getActiveSeller().getInventory().addToInventory(product);
               ShoppingCartSystem.getActiveSeller().getInventory().overWriteInventoryFile(ShoppingCartSystem.getActiveSeller());
               //ShoppingCartSystem.updateProductList(Sho);
                DefaultTableModel dm = generateTable();
                table.setModel(dm);

                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));


                //table.setDefaultRenderer(String.class, centerRenderer);
                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );

                table.repaint();
            }
        });
        
    }
    
    /**
      * Generates the table model using the sellers inventory.
      * @return a DefaultTableModel consisting of all the products in the inventory along with "Update" and "Click for Description" buttons for each row.
      */
    
    public  DefaultTableModel generateAddTable()
    {
        dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]  { }, new Object[] {"Product", "Price","Description","Stock", "Cost",});
        JTextField tf7 = new JTextField(10);
        Object[] row = {"Product", "Price","Description","Stock", "Cost"};
        dm.addRow(row);
        return dm;
    }
    public DefaultTableModel generateTable()
    {
        dm = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                if(column == 6) return false;
                else return true;
                 
            }
        };   
        
        dm.setDataVector(new Object[][]  {  }, new Object[] { "Button", "Product", "Price","Description", "Stock", "Cost",  "Total Sold"});
        Iterator iter = ShoppingCartSystem.getActiveSeller().getInventory().getAllProducts();
        
       
        while(iter.hasNext())
        {
            
            Product tempProduct = (Product) iter.next();
            productNames.add(tempProduct.getName());
            
            
            Object[] row = new Object[] {"Update", tempProduct.getName(), tempProduct.getPrice(),"Click for Description", tempProduct.getinventoryQuantity(),  tempProduct.getCost(),  tempProduct.getTotalNumberSold()};
            dm.addRow(row);
           
        }
        
        return dm;
    }
    
    /**
     * Decorator class for JTable buttons.
     */
    
    class ButtonRenderer extends JButton implements TableCellRenderer 
    {

        public ButtonRenderer() 
        {
          setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
        {
          if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
          } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
          }
          setText((value == null) ? "" : value.toString());
          return this;
        }
}

    /**
     * Editor class for JTable buttons.
     */
    
    class ButtonEditor extends DefaultCellEditor 
    {
        protected JButton button;

        private String label;

        private boolean isPushed;
        
        /**
         * Constructor for button editor.
         * @param checkBox checkbox to be added.
         */
        
        public ButtonEditor(JCheckBox checkBox) 
        {
          super(checkBox);
          button = new JButton();
          button.setOpaque(true);
          button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              fireEditingStopped();
            }
          });
        }
        
        /**
        * Returns cell component and handles button presses.
        * @param table JTable
        * @param value Object inside cell.
        * @param isSelected boolean that is true if cell is selected.
        * @param row row value of cell.
        * @param column column value of cell.
        * @return button pressed for repaint.
        */

        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) 
        {
          if (isSelected) 
          {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
          } 
          else 
          {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
          }
          label = (value == null) ? "" : value.toString();
          System.out.println(table.getValueAt(row, column + 1).toString());

          if(column == 0)   // Update Button
          {
                if(table.getValueAt(row, column + 4).equals("0"))   //if quantity is 0
                {
                   String name = table.getValueAt(row, column + 1).toString();  
                   Product temp = ShoppingCartSystem.getActiveSeller().getInventory().getProduct(name);
                   ShoppingCartSystem.getActiveSeller().getInventory().removeProduct(temp);
                    //dm.removeRow(row - dm.getRowCount());

                   ShoppingCartSystem.getActiveSeller().getInventory().overWriteInventoryFile(ShoppingCartSystem.getActiveSeller());
                   ShoppingCartSystem.updateProductList(ShoppingCartSystem.getSellerList());
                   //this.cancelCellEditing();
                   //removeRow(row);

                }
                else    //Price and quantity change
                {
                    String name = table.getValueAt(row, column + 1).toString();
                    //Get new Price
                    String price = table.getValueAt(row, column + 2).toString();
                    String quantity = table.getValueAt(row, column + 4).toString();
                    int q = Integer.parseInt(quantity);

                    Product temp = ShoppingCartSystem.getActiveSeller().getInventory().getProduct(productNames.get(row));  //changed
                    temp.setName(name);
                    temp.setPrice(Double.parseDouble(price));
                    temp.setInventoryQuantity(q);
                    ShoppingCartSystem.getActiveSeller().getInventory().overWriteInventoryFile(ShoppingCartSystem.getActiveSeller());
                }


              if (ShoppingCartSystem.getActiveSeller().getInventory().getSize() > 0) //if inventory has something
              {
                    productNames.clear();
                    dm = generateTable();
                    table.setModel(dm);

                    table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                    table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                    table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                    table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));


                    //table.setDefaultRenderer(String.class, centerRenderer);
                    table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );

                    table.repaint();
              }

              else //No inventory empty table
              {
                productNames.clear();
                 dm.setDataVector(new Object[][]  {  }, new Object[] {"Button", "Product", "Price","Description", "Stock", "Cost",  "Total Sold"});
                 table.setModel(dm);
                  //table.setDefaultRenderer(String.class, centerRenderer);
                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( descriptionRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.repaint();
              }

              button.setText(label);

          }
          else if(column == 3)  //Description Button
          {
              String name = table.getValueAt(row, column - 2).toString();
              Product temp = ShoppingCartSystem.getActiveSeller().getInventory().getProduct(name);
              frame.setVisible(false);
              try
              {
                  ShoppingCartSystem.descriptionPage.display(temp);
              } catch (IOException ex)
              {
                  Logger.getLogger(SellerPage.class.getName()).log(Level.SEVERE, null, ex);
              }
          }

          else  //Anything else
          {

              if (ShoppingCartSystem.getActiveSeller().getInventory().getSize() > 0)
              {
                productNames.clear();
                DefaultTableModel dm2 = generateTable();
                table.setModel(dm2);

                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));

                //table.setDefaultRenderer(String.class, centerRenderer);
                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );

                table.repaint();
          }

          else
            {
                productNames.clear();
                 dm.setDataVector(new Object[][]  {  }, new Object[] { "Button", "Product", "Price","Description", "Stock", "Cost",  "Total Sold"});
                 table.setModel(dm);
                //table.setDefaultRenderer(String.class, centerRenderer);
                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));

                table.repaint();
            }
          }
          return button;
        }

        /**
        * Returns object depending on button presses
        * @return Object depending on button presses.
        */

        public Object getCellEditorValue() 
        {
          if (isPushed) {
            // 
            // 
            JOptionPane.showMessageDialog(button, "Product Removed From Inventory");
            // System.out.println(label + ": Ouch!");
          }
          isPushed = false;
          return new String(label);
        }

        public boolean stopCellEditing() {
          isPushed = false;
          return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
          super.fireEditingStopped();
        }
       }

        
        /**
         * Removes a row from the table.
         * @param row row to be removed.
         */
    

        public void removeRow(int row)
        {
            dm.removeRow(row);
        }
   
   
    }
