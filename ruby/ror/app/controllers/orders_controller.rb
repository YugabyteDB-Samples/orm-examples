class OrdersController < ApplicationController

    def index
        orders = Order.all
        render json:{content:orders},status: :ok
    end

    def create
        req_products = params[:products]
        user_id = params[:userId]
        productIDs = req_products.map{|p|  p[:productId]; }
        orderInfo = [];
        db_products = Product.where(id:productIDs);
        total = 0;
        db_products.each{
            |pro| orderedProduct = req_products.select{|prod|
                pro[:id] == prod[:productId]
            }
            p = ProductInfo . new
            p.productId = pro[:id];
            p.units = orderedProduct[0][:units];
            p.price = pro[:price];
            total += p.price * orderedProduct[0][:units];
            orderInfo.push(p);
       }
       order = Order.new(orderTotal:total, userId:user_id )
       if order.save
            orderInfo.each{|item|
                o = OrderLine.new(productId:item.productId, orderId:order[:id]);
                if o.save
                     puts("saved #{o}")
                else
                     puts("error#{o.errors}")
                end
            }
            render html:'TBD'
        else
            render json:{error:order.errors}
        end
    end

    private
        def order_params
            params.permit(:userId, :orderTotal, {products:[:productId, :units]} )
        end

       class ProductInfo
           attr_accessor :productId;
           attr_accessor :units;
           attr_accessor :price;
       end
end
