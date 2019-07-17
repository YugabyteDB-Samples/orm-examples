package controllers;


import models.User;
import play.libs.Json;
import play.mvc.*;
import java.util.List;

public class UserController extends Controller {

    public Result GetAllusers() {
        try {
            List<User> users = User.find.all();

            return ok(Json.toJson(users));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to get the users list." + ex.getStackTrace());
        }
    }

    public Result GetUserById(Long id) {
        try {
            User user = User.find.byId(id);
            if(user == null)
                return notFound("User id does not exists");
            return ok(Json.toJson(user));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to get the user." + ex.getStackTrace());
        }
    }
    
    public Result AddUser(Http.Request request) {    	
        try {
            User user = Json.fromJson(request.body().asJson(), User.class);
            user.save();
            return ok(Json.toJson(user));
        } catch (Exception ex) {
            return ok("An exception has occured while trying to add the user." + ex.getStackTrace());
        } finally {
        }
    }
    
    public Result UpdateUser(Http.Request request) {
        try {
            User user = Json.fromJson(request.body().asJson(), User.class);
            User oldUser = User.find.byId(user.userId);
            if (oldUser == null)
                return notFound("Invalid User Id.");
            oldUser.firstName = user.firstName;
            oldUser.lastName = user.lastName;
            oldUser.email = user.email;
            oldUser.update();
            return ok("User updated successfully.");
        } catch (Exception ex) {
            return ok("An exception has occured while trying to update the user." + ex.getStackTrace());
        }
    }

    public Result DeleteUser(Long id) {
        try {
            User user = User.find.byId(id);
            if (user == null)
                return notFound("Invalid User Id.");
            user.delete();
            return ok("User deleted successfully.");
        } catch (Exception ex) {
            return ok("An exception has occured while trying to delete the user." + ex.getStackTrace());
        }
    }
}
