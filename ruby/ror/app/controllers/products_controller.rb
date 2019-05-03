class ProductsController < ApplicationController

    def index
    products = Product.all;
   render json:{content:products},status: :ok
    end


    def create
            product = Product.new(product_params)
            if product.save
        render json:product,status: :ok
        else
        render json:{error:product.errors}
        end
    end

    private
        def product_params
        params.permit(:productName, :description, :price)
        end
end
