package org.example.businessLogic;

import org.example.dataAccess.ProductDAO;
import org.example.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * this class acts as an intermediate layer between the presentation layer and the product dao layer
 */
public class ProductBLL {
    private static final Product theProduct = new Product();
    private final ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) throws NoSuchElementException {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException();
        }
        return product;
    }

    public List<Product> findAllProducts() throws NoSuchElementException {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            throw new NoSuchElementException();
        }
        return products;
    }

    public void insertProduct(Product product) throws SQLException {
        productDAO.insert(product);
    }

    public void updateProductName(int id, String newName) throws SQLException {
        productDAO.updateProductName(id, newName);
    }

    public void updateProductQuantity(int id, int newQuantity) throws SQLException {
        productDAO.updateProductQuantity(id, newQuantity);
    }

    public void deleteProductById(int id) throws SQLException {
        productDAO.deleteById(id);
    }

    public String[] getFieldNames() {
        return productDAO.getFieldNames(theProduct);
    }
}
