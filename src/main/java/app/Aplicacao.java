package app;

import static spark.Spark.*;
import service.PeopleService;


public class Aplicacao {
	
	private static PeopleService peopleService = new PeopleService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/people/insert", (request, response) -> peopleService.insert(request, response));

        get("/people/:id", (request, response) -> peopleService.get(request, response));
        
        get("/people/list/:orderby", (request, response) -> peopleService.getAll(request, response));

        get("/people/update/:id", (request, response) -> peopleService.getToUpdate(request, response));
        
        post("/people/update/:id", (request, response) -> peopleService.update(request, response));
           
        get("/people/delete/:id", (request, response) -> peopleService.delete(request, response));

             
    }
}
