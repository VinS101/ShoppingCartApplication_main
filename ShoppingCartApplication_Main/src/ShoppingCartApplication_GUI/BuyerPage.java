//BUYER PAGE

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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import shoppingcartapplication_main.Product;
import shoppingcartapplication_main.ProductList;
import shoppingcartapplication_main.ShoppingCartSystem;


/**
 * A GUI class for the Buyer Page.
 */
public class BuyerPage 
{
    JTable table;
    DefaultTableModel dm;
    DefaultTableCellRenderer centerRenderer;
    /**
     * For debugging purposes
     * @param args 
     */
    
    public BuyerPage(){
       viewedShoppingCart = false; 
    }
    
    
    /**
     * Displays buyer page when active user account is of type buyer.
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
        String name = ShoppingCartSystem.getActiveBuyer().getUsername();
        JLabel logo = new JLabel("Hello, " + name + "!");
        logo.setFont(logo.getFont().deriveFont(40.0f));
        logoPanel.add(logo);
        
        //Make buttons
        JButton logout = new JButton("Logout");
        JButton shoppingCart = new JButton("Shopping Cart");
        logout.setPreferredSize(new Dimension(150,75));
        shoppingCart.setPreferredSize(new Dimension(150,75));
        
        
        //Populate buttons
        buttonPanel.add(shoppingCart);
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
        
        
        
        table.setModel(dm);
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       //table.setDefaultRenderer(String.class, centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        
        table.getColumn("Button").setCellRenderer(new ButtonRenderer());
        table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Description").setCellRenderer(new ButtonRenderer());
        table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
        //Create the scrollpane
        JScrollPane scroll = new JScrollPane(table);
        
        
        //Populate MainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(400,400)));
        mainPanel.add(scrollBarPanel, BorderLayout.EAST);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //Populate SouthPanel
        southPanel.add(Box.createRigidArea(new Dimension(100,100)));
        
        //Make JFrame
           //Create the main frame    
        frame.getContentPane().setBackground(Color.green);  //set background color
        frame.setLayout(new BorderLayout());
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //fullscreen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //close
        frame.add(scroll, BorderLayout.CENTER );
        frame.add(northPanel, BorderLayout.NORTH);
        //frame.add(mainPanel, BorderLayout.CENTER);   //Add main panel
        frame.add(southPanel, BorderLayout.SOUTH);
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
          ShoppingCartSystem.getActiveBuyer().getCart().clearCart();
          ShoppingCartSystem.clearActiveBuyer();
          ShoppingCartSystem.loginPage.display();
        }
    } );
        
        shoppingCart.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          if (ShoppingCartSystem.getActiveBuyer().getCart().getSize() != 0)
          {
            if(viewedShoppingCart == false)
            {
              viewedShoppingCart = true;
              frame.setVisible(false); 
              ShoppingCartSystem.cartpage.display(ShoppingCartSystem.getActiveBuyer().getCart());
            }
            else
            {
                frame.setVisible(false);
                ShoppingCartSystem.cartpage.repaintTable(ShoppingCartSystem.getActiveBuyer().getCart());
            }
          }
          else
          {
              JOptionPane.showMessageDialog(frame, "You have no items in your shopping cart.");
          }
          
        }
    } );
    }
    
      /**
      * Repaints table with updated product list if user leaves page and then comes back with updated list info.
      */
    
     public void repaintTable()
    {
        frame.setVisible(true);
        dm = generateTable();
        table.setModel(dm);
        
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumn("Button").setCellRenderer(new ButtonRenderer());
        table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Description").setCellRenderer(new ButtonRenderer());
        table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
        //table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );  
        table.repaint();
    }
     
     /**
      * Generates the table model using the product list created by the ShoppingCartSystem class.
      * @return a DefaultTableModel consisting of all the products in the product list along with "Add" and "Click for Description" buttons for each row.
      */
    
    public DefaultTableModel generateTable()
    {
        dm = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                if(column == 1 || column == 0 || column == 4) return true;
                else return false;
                 
            }
        };  
        dm.setDataVector(new Object[][]  {  }, new Object[] { "Button", "Quantity", "Product", "Price", "Description", "Stock", "Sold by"});
        Iterator iter = ProductList.getAllProducts();
        
        
        while(iter.hasNext())
        {
            
            Product tempProduct = (Product) iter.next();
            
            Object[] row = new Object[] {"Add", "1", tempProduct.getName(), tempProduct.getPrice(),"Click for Description", tempProduct.getinventoryQuantity(), tempProduct.getSoldBy()};
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
          if (isSelected) 
          {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
          } else 
          {
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
        private boolean add;
        
        /**
         * Constructor for button editor.
         * @param checkBox checkbox to be added.
         */

        public ButtonEditor(JCheckBox checkBox) {
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
        boolean isSelected, int row, int column) {
      if (isSelected) 
      {
          
        button.setForeground(table.getSelectionForeground());
        button.setBackground(table.getSelectionBackground());
      } else 
      {
        button.setForeground(table.getForeground());
        button.setBackground(table.getBackground());
      }
      label = (value == null) ? "" : value.toString();
      System.out.println(table.getValueAt(row, column + 1).toString());
      if(column == 0)
      {
          //ADD TO SHOPPING CART
        String name = table.getValueAt(row, column + 2).toString();  
        String seller = table.getValueAt(row, column + 6).toString();  
        String quantity = table.getValueAt(row, column + 1).toString();
        Product temp = ProductList.getOneProduct(name, seller);
        ShoppingCartSystem.getActiveBuyer().getCart().addToCart(temp, Integer.parseInt(quantity));
        add = true;
        JOptionPane.showMessageDialog(button, "Product Added to Shopping Cart");
      }
      if(column == 4)
      {
          String name = table.getValueAt(row, column - 2).toString();
          String soldBy =  table.getValueAt(row, column + 2).toString();
          Product temp = ProductList.getOneProduct(name, soldBy);
          frame.setVisible(false);
          try
          {
              ShoppingCartSystem.descriptionPage.display(temp);
          } catch (IOException ex)
          {
              Logger.getLogger(SellerPage.class.getName()).log(Level.SEVERE, null, ex);
          }
          add = false;
      }
      
      button.setText(label);
      isPushed = true;
      return button;
    }
    
    /**
     * Returns object depending on button presses
     * @return Object depending on button presses.
     */

    public Object getCellEditorValue() 
    {
      if (isPushed) 
      {
        
        //JOptionPane.showMessageDialog(button, "Product Added to Shopping Cart");
        
        // System.out.println(label + ": Ouch!");
      }
      isPushed = false;
      return new String(label);
    }

    public boolean stopCellEditing() 
    {
      isPushed = false;
      return super.stopCellEditing();
    }

    protected void fireEditingStopped() 
    {
      super.fireEditingStopped();
    }
   }

     JFrame frame = new JFrame("Buyer Page");
     boolean viewedShoppingCart;
}
