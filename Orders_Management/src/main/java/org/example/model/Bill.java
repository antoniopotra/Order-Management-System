package org.example.model;

import java.sql.Timestamp;

/**
 * bill model, just a record
 *
 * @param orderId         the id of the order
 * @param productName     the name of the product that was bought
 * @param productQuantity the quantity that was bought
 * @param orderTimestamp  the time when the order was placed
 */
public record Bill(int orderId, String productName, int productQuantity, Timestamp orderTimestamp) {
}
