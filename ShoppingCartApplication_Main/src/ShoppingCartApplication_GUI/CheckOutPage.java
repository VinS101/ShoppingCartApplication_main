
package ShoppingCartApplication_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import shoppingcartapplication_main.Product;
//CHECKOUTPAGE

import shoppingcartapplication_main.Seller;
import shoppingcartapplication_main.ShoppingCart;
import shoppingcartapplication_main.ShoppingCartSystem;
import static ShoppingCartApplication_GUI.CartPage.cart;

/**
 * GUI class for the checkout page.
 */
public class CheckOutPage 
{
    static JFrame frame;
    static ShoppingCart cart;
    DefaultTableModel dm; 
    JTable table;
    JTable addTable;
    DefaultTableCellRenderer centerRenderer;
    DefaultTableCellRenderer descriptionRenderer = new DefaultTableCellRenderer();
    double total;
    JLabel totalTextLabel;
    
    /**
     * Constructor for checkout page.
     */
    
     public CheckOutPage()
    { 
       frame = new JFrame();    //Create the main frame  
      
    }
     
     /**
      * Displays the checkout page.
      * @param aCart The active user's shopping cart.
      * @precondition aCart.size > 0
      */

     public void display(ShoppingCart aCart)
    {
        cart = aCart;
        
        System.out.println(cart.getSize());
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
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
        String name = ShoppingCartSystem.getActiveBuyer().getUsername().substring(0, 1).toUpperCase() + ShoppingCartSystem.getActiveBuyer().getUsername().substring(1);
        JLabel logo = new JLabel("Your Checkout Page");
        logo.setFont(logo.getFont().deriveFont(33.0f));
        logoPanel.add(logo);
        
        //Make buttons
        JButton logout = new JButton("Logout");
        JButton back = new JButton("Back to Cart");
        JButton checkout = new JButton("Confirm Purchase");
        logout.setPreferredSize(new Dimension(150,75));
        back.setPreferredSize(new Dimension(150,75));
        checkout.setPreferredSize(new Dimension(150,75));
        
        JLabel creditCard = new JLabel("Credit Card Number: ");
        creditCard.setFont(logo.getFont().deriveFont(24.0f));
        
        //Make credit cart texfiled
        JTextField field = new JTextField("Please Enter your Credit Card Number");
        field.setSize(50,200);
        
        //Get total
        total = ShoppingCartSystem.getActiveBuyer().getCart().getTotalPrice();
        String totalText = new DecimalFormat("#.##").format(total);
        totalTextLabel = new JLabel("Total: $" + totalText);
        totalTextLabel.setFont(logo.getFont().deriveFont(33.0f));
        
        
        //Populate buttons
        buttonPanel.add(back);
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

       table.setModel(dm);
       
        //table.setDefaultRenderer(String.class, centerRenderer);
       table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
       table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
       table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
       table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
       
      
        
        //Create the scrollpane
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(70, 179, 43));
        scroll.getViewport().setOpaque(true);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        
        
      
        
        
        //Populate MainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(400,400)));
        mainPanel.add(scroll, BorderLayout.CENTER);
        
        
        //Populate SouthPanel
        southPanel.add(totalTextLabel);
        southPanel.add(Box.createRigidArea(new Dimension(300,0)));
        southPanel.add(creditCard);
        southPanel.add(field);
        southPanel.add(Box.createRigidArea(new Dimension(100,0)));
        southPanel.add(checkout);

        
        //mainPanel.add(southPanel,BorderLayout.SOUTH );
        //Make JFrame
        
        
        frame.getContentPane().setBackground(Color.green);  //set background color
        frame.setLayout(new BorderLayout());
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //fullscreen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //close
        //frame.add(scroll, BorderLayout.CENTER );
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(mainPanel);   //Add main panel
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
        
        back.addActionListener(new ActionListener()
       {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false);
          
          
          ShoppingCartSystem.cartpage.repaintTable(cart);
           
        }
    } ); 
        
                   
        checkout.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent ae)
            {
                Iterator iter = cart.getAllProducts();
                Product tempProduct;
                int count = 0;
                while(iter.hasNext())
                {
                    tempProduct = (Product) iter.next();
                    Seller s = ShoppingCartSystem.findSeller(tempProduct.getSoldBy());
                    Iterator iter2 = s.getInventory().getAllProducts();
                    
                    while(iter2.hasNext())
                    {
                        Product temp2 = (Product) iter2.next();
                        
                        if(tempProduct.getName().equals(temp2.getName()))
                        {
                            if(tempProduct.getCartQuantity() < temp2.getinventoryQuantity())
                            {
                                count++;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(frame, "There is not enough stock to purchase "+ temp2.getCartQuantity()  +" of this item inventory");
                                break;
                            }
                        }
                    }
                    }
                
                    if(count == cart.getSize())
                    {
                        Iterator iter3 = cart.getAllProducts();
                        Product tempProduct2;
                        while(iter3.hasNext())
                        {
                        
                            tempProduct2 = (Product) iter3.next();
                            Seller s2 = ShoppingCartSystem.findSeller(tempProduct2.getSoldBy());
                            Iterator iter4 = s2.getInventory().getAllProducts();

                            while(iter4.hasNext())
                            {
                                Product temp2 = (Product) iter4.next();

                                if(tempProduct2.getName().equals(temp2.getName()))
                                {
                                        ShoppingCartSystem.makePurchases(cart);

                                }
                            }
                        } 
                        
                        frame.setVisible(false);
                        ShoppingCartSystem.updateProductList(ShoppingCartSystem.getSellerList());
                        ShoppingCartSystem.invoicepage.display(cart);
                        
                    }
                    
                
                
            }
        });
        
    }
     
     /**
      * Generates the table model using the active user's shopping cart.
      * @return a DefaultTableModel consisting of all the products in the shopping cart along with "update" and "Click for Description" buttons for each row.
      */
     
     public DefaultTableModel generateTable()
    {
        dm = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };   
        
        dm.setDataVector(new Object[][]  {  }, new Object[] {"Product", "Price", "Quantity", "Sold By"});
        Iterator iter = cart.getAllProducts();
        
       
        while(iter.hasNext())
        {
            
            Product tempProduct = (Product) iter.next();

            Object[] row = new Object[] {tempProduct.getName(), tempProduct.getPrice(),tempProduct.getCartQuantity(), tempProduct.getSoldBy()};
            dm.addRow(row);
           
        }
        
        return dm;
    }
     
     /**
      * Repaints table with updated cart if user leaves page and then comes back with updated cart info.
      * @param newCart updated cart.
      */
     
     public void repaintTable(ShoppingCart newCart)
    {
        frame.setVisible(true);
        cart = newCart;
        dm = generateTable();
        table.setModel(dm);
        
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        //table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        double newTotal = 0;
        newTotal = newCart.getTotalPrice();
        String newTotalText = new DecimalFormat("#.##").format(newTotal);
        totalTextLabel.setText(null);
        totalTextLabel.setText("Total: " + newTotalText);
        totalTextLabel.repaint();
          
        table.repaint();
    }
}

