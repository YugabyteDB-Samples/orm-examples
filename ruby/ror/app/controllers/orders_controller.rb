class OrdersController < ApplicationController

 def index
    orders = Order.all
   render json:{content:orders},status: :ok
    end


    def create
            req_products = params[:products]
          productIDs = req_products.map{|p|  p[:productId];  }
            db_products = Product.where(id:productIDs);
           db_products.each{|pro| orderedProduct = req_products.select{|prod| pro[:id] == prod[:productId]}
#           orderInfo.push();
           orderedProduct
            }

#            order = Order.new(order_params)
#            if order.save
        render json:db_products,status: :ok
#        else
#        render json:{error:order.errors}
#        end
    end

    private
        def order_params
        params.permit(:userId, :orderTotal, {products:[:productId, :units]} )
        end
end
