package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Order;
import model.Product;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

/**
 * The PDF class creates pdf files with the data from the data base.
 */
public class PDF {

    /**
     * Generates a bill containing information about the client, the product and the amount to be paid.
     * @param c the client that placed the order
     * @param p the product that was ordered
     * @param quantity the amount of product that is ordered
     */
    public void generateBill(Client c, Product p, int quantity){
        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(c.getClient_name() + "_" + p.getProduct_name() + ".pdf"));
        } catch (FileNotFoundException | DocumentException e) {
            System.out.println("Bill could not be created");
            e.printStackTrace();
        }
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk title = new Chunk("Order Bill", font);
        Chunk client = new Chunk("Client: " + c.getClient_name() + ", " + c.getClient_phone_number() + ", " + c.getClient_email() + ", " + c.getClient_city(), font);
        Chunk product = new Chunk("Product: " + p.getProduct_name() + ", quantity: " + quantity, font);
        float total = p.getProduct_price() * quantity;
        Chunk cost = new Chunk("Total Cost: " + total, font);

        try{
            document.add(title);
            document.add(new Paragraph());
            document.add(client);
            document.add(new Paragraph());
            document.add(product);
            document.add(new Paragraph());
            document.add(cost);
            document.add(new Paragraph());
        } catch (DocumentException e) {
            System.out.println("Bill could not be created");
            e.printStackTrace();
        }

        document.close();
    }

    /**
     * Adds rows into a table corresponding to the data base table selected.
     * @param table pdf table
     * @param str data base table
     * @param clients list of clients
     * @param products list of products
     * @param orders list of products
     */
    private static void addRows(PdfPTable table, String str, List<Client> clients, List<Product> products, List<Order> orders) {
        switch (str){
            case "client" :
                for(Client c : clients){
                    table.addCell(Integer.toString(c.getIdclient()));
                    table.addCell(c.getClient_name());
                    table.addCell(c.getClient_email());
                    table.addCell(c.getClient_phone_number());
                    table.addCell(c.getClient_city());
                }
                break;

            case "product" :
                for(Product p : products){
                    table.addCell(Integer.toString(p.getIdproduct()));
                    table.addCell(p.getProduct_name());
                    table.addCell(Float.toString(p.getProduct_price()));
                    table.addCell(Integer.toString(p.getProduct_stock()));
                }
                break;

            default :
                for(Order o : orders){
                    table.addCell(Integer.toString(o.getIdorder()));
                    table.addCell(Integer.toString(o.getOrder_quantity()));
                    table.addCell(Integer.toString(o.getClient_idclient()));
                    table.addCell(Integer.toString(o.getProduct_idproduct()));
                    table.addCell(Integer.toString(o.getOrderdetails_idorderdetails()));
                }
                break;
        }
    }

    /**
     * Creates the header of the table corresponding to the data base table selected.
     * @param table pdf table
     * @param str data base table
     */
    private static void addTableHeader(PdfPTable table, String str) {

        if(str.equals("client"))
            Stream.of("id", "name", "email", "phone number", "city")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(String.valueOf(columnTitle)));
                        table.addCell(header);
                    });
        else if(str.equals("product"))
            Stream.of("id", "name", "price", "stock")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(String.valueOf(columnTitle)));
                        table.addCell(header);
                    });
        else
            Stream.of("id", "quantity", "clientid", "productid", "orderdetailsid")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(String.valueOf(columnTitle)));
                        table.addCell(header);
                    });
    }

    /**
     * Generates a report containing the data in a table, displayed in a tabular form.
     * @param table data base table
     * @param ClientBLL business logic object to get the data from the client table
     * @param ProductBLL business logic object to get the data from the product table
     * @param OrderBLL business logic object to get the data from the order table
     */
    public void generateReport(String table, String it, ClientBLL ClientBLL, ProductBLL ProductBLL, OrderBLL OrderBLL){
        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(table + it + "_report.pdf"));
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        document.open();

        int nCols;
        List<Client> clientList = null;
        List<Product> productList = null;
        List<Order> orderList = null;
        switch (table) {
            case "client" :
                clientList = ClientBLL.findAll();
                nCols = Client.class.getDeclaredFields().length;
                break;
            case "product" :
                productList = ProductBLL.findAll();
                nCols = Product.class.getDeclaredFields().length;
                break;
            default:
                orderList = OrderBLL.findAll();
                nCols = Order.class.getDeclaredFields().length;
                break;
        }

        PdfPTable pdfPTable = new PdfPTable(nCols);
        addTableHeader(pdfPTable, table);
        addRows(pdfPTable, table, clientList, productList, orderList);

        try{
            document.add(pdfPTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
    }

}
