package controllers;

import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController extends Controller {
    public Result GetAllOrders() {
        try {
            List<Order> orders = Order.find.all();
            return ok(Json.toJson(orders));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to get the orders list." + ex.getStackTrace());
        }
    }

    public Result GetOrderById(UUID id) {
        try {
            Order order = Order.find.byId(id);
            if (order == null)
                return notFound("Order id does not exist.");
            return ok(Json.toJson(order));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to get the order." + ex.getStackTrace());
        }
    }

    public Result CreateOrder(Http.Request request) {
        try {
            double orderTotal = 0;
            Order order = Json.fromJson(request.body().asJson(), Order.class);

            User orderOwner = User.find.byId(order.userId);

            if (orderOwner == null)
                return notFound("User id does not exist.");

            /* Find OrderTotal by taking Products and UnitPrice into account  */
            for (int i = 0; i < order.products.size(); i++) {
                try {
                    Long productId = order.products.get(i).productId;
                    Product product = Product.find.byId(productId);
                    orderTotal += product.price * order.products.get(i).units;
                } catch (Exception ex) {
                    return notFound("Product id does not exist.");
                }
            }
            Order singleOrder = new Order();

            /* Save the Order first, as its OrderId would be used for OrderLine */
            UUID uuid = UUID.randomUUID();
            singleOrder.orderTime = new Date();
            singleOrder.orderTotal = orderTotal;
            if (orderOwner != null) {
                try {
                    singleOrder.orderOwner = orderOwner;
                } catch (Exception ex) {
                }
            }
            singleOrder.save();

            /* Save OrderLine records for this Order */
            for (int i = 0; i < order.products.size(); i++) {
                OrderLine orderLine = new OrderLine();
                orderLine.units = order.products.get(i).units;
                Product orderProduct = Product.find.byId(order.products.get(i).productId);
                orderLine.pk = new OrderLineGroup(singleOrder.orderId, orderProduct.productId);
                orderLine.save();
            }
            return ok(Json.toJson(singleOrder));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to create the order" + ex.getStackTrace());
        }
    }

}
