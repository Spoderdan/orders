package start;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;
import presentation.PDF;
import presentation.Parser;
import java.util.Map;

/**
 * The start class is responsible of starting the application. It uses objects of type "parser", to process the input file in a form
 * that is usable, and "PDF" to generate PDF files for bills and reports.
 * @author Anghel Dan-Marian 30422
 */
public class Start {

    public static void main(String[] args){

        Parser parser = new Parser();
        PDF pdf = new PDF();

        ClientBLL ClientBLL = new ClientBLL();
        ProductBLL ProductBLL = new ProductBLL();
        OrderBLL OrderBLL = new OrderBLL();

        Map<String[], String[]> commands;
        commands = parser.processFile(args[0]);

        int i = 0;
        for(Map.Entry<String[], String[]> entry : commands.entrySet())
            switch (entry.getKey()[0]) {
                // INSERT
                case "Insert":
                    // CLIENT
                    if (entry.getKey()[1].equals("client")) {
                        Client c = ClientBLL.findClientByName(entry.getValue()[0]);
                        if (c == null)
                            ClientBLL.insert(new Client(entry.getValue()[0], "email@email.com", "0712345678",entry.getValue()[1]));
                        else
                            System.out.println("Client " + entry.getValue()[0] + " already exists");
                    }
                    // PRODUCT
                    else if (entry.getKey()[1].equals("product")) {
                        Product p = ProductBLL.findProductByName(entry.getValue()[0]);
                        if (p != null) {
                            int newStock = Integer.parseInt(entry.getValue()[1]) + p.getProduct_stock();
                            ProductBLL.update("product_stock", Integer.toString(newStock), p.getIdproduct());
                        } else
                            ProductBLL.insert(new Product(entry.getValue()[0], Float.parseFloat(entry.getValue()[2]), Integer.parseInt(entry.getValue()[1])));
                    }
                    break;
                // DELETE
                case "Delete":
                    // CLIENT
                    if (entry.getKey()[1].equals("client")) {
                        Client c = ClientBLL.findClientByName(entry.getValue()[0]);
                        if (c == null)
                            System.out.println("Client " + entry.getValue()[0] + " does not exist");
                        else if (!c.getClient_name().equals(entry.getValue()[0]))
                            System.out.println("That client does not exist");
                        else
                            ClientBLL.delete(c.getIdclient());
                    }
                    // PRODUCT
                    else if (entry.getKey()[1].equals("Product")) {
                        Product p = ProductBLL.findProductByName(entry.getValue()[0]);
                        if (p == null)
                            System.out.println("Product " + entry.getValue()[0] + " does not exist");
                        else
                            ProductBLL.delete(p.getIdproduct());
                    }
                    break;
                // ORDER
                case "Order":
                    Client c = ClientBLL.findClientByName(entry.getValue()[0]);
                    Product p = ProductBLL.findProductByName(entry.getValue()[1]);
                    if (c == null)
                        System.out.println("Client " + entry.getValue()[0] + " does not exist");
                    else {
                        if (p == null)
                            System.out.println("Product " + entry.getValue()[1] + " does not exist");
                        else {
                            int newStock = p.getProduct_stock() - Integer.parseInt(entry.getValue()[2]);
                            if (newStock < 0)
                                System.out.println(entry.getValue()[1] + " stock not enough");
                            else {
                                OrderBLL.insert(new Order(Integer.parseInt(entry.getValue()[2]), c.getIdclient(), p.getIdproduct(), 1));
                                ProductBLL.update("product_stock", Integer.toString(newStock), p.getIdproduct());
                                pdf.generateBill(c, p, Integer.parseInt(entry.getValue()[2]));
                            }
                        }
                    }
                    break;
                // REPORT
                default:
                    pdf.generateReport(entry.getKey()[1], Integer.toString(i), ClientBLL, ProductBLL, OrderBLL);
                    i++;
                    break;
            }

    }

}