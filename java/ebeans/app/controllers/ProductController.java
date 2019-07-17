package controllers;


import models.Product;
import play.libs.Json;
import play.mvc.*;
import java.util.List;

public class ProductController extends Controller {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("application");
    public Result GetAllProducts() {
        try {
            List<Product> products = Product.find.all();
            return ok(Json.toJson(products));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to get list of products." + ex.getStackTrace());
        }
    }

    public Result GetProductById(Long id) {
        try {
            Product product = Product.find.byId(id);
            if(product == null)
                return notFound("Product id does not exists");
            return ok(Json.toJson(product));
        } catch (Exception ex) {
        	return ok("An exception has occured while trying to get product." + ex.getStackTrace());
        }
    }

    public Result AddProduct(Http.Request request) {
        try {
            Product product = Json.fromJson(request.body().asJson(), Product.class);
            product.save();
            return ok(Json.toJson(product));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to add the product" + ex.getStackTrace());
        }
    }

    public Result UpdateProduct(Http.Request request) {
        try {
            Product product = Json.fromJson(request.body().asJson(), Product.class);
            Product oldProduct = Product.find.byId(product.productId);
            if (oldProduct == null)
                return notFound("Invalid Product Id.");
            oldProduct.productName = product.productName;
            oldProduct.description = product.description;
            oldProduct.price = product.price;
            oldProduct.update();
            return ok("Product updated successfully.");
        } catch (Exception ex) {
            return ok("An exception has occured while trying to update the product" + ex.getStackTrace());
        }
    }

    public Result DeleteProduct(Long id) {
        try {
            Product product = Product.find.byId(id);
            if (product == null)
                return notFound("Invalid Product Id.");
            product.delete();
            return ok("Product deleted successfully.");
        } catch (Exception ex) {
            return ok("An exception has occured while trying to delete the product" + ex.getStackTrace());
        }
    }
}
