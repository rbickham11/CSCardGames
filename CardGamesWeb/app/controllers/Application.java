package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Ryan is Awesome"));
    }
    
    public static Result tables() {
    	return ok(tables.render());
    }
    
    public static Result holdemtable() {
    	return ok(holdemtable.render());
    }
}
