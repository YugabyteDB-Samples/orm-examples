class UsersController < ApplicationController

    def index
    users = User.all;
   render json:{content:users}, status: :ok
    end


    def create
            user = User.new(user_params)
            if user.save
        render json:user,status: :ok
        else
        render json:{responseBody:{content:[{message:'Error',error:user.errors}]}}
        end
    end

    private
        def user_params
        params.permit(:firstName, :lastName, :email)
        end
end
