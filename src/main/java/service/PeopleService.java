package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.PeopleDAO;
import model.People;
import spark.Request;
import spark.Response;


public class PeopleService {

	private PeopleDAO peopleDAO = new PeopleDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NAME = 2;
	private final int FORM_ORDERBY_AGE = 3;
	
	
	public PeopleService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new People(), FORM_ORDERBY_NAME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new People(), orderBy);
	}

	
	public void makeForm(int tipo, People people, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umPeople = "";
		if(tipo != FORM_INSERT) {
			umPeople += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/people/list/1\">Novo People</a></b></font></td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t</table>";
			umPeople += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/people/";
			String name, age, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir People";
				age = "-1";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + people.getID();
				name = "Atualizar People (ID " + people.getID() + ")";
				age = people.getName();
				buttonLabel = "Atualizar";
			}
			umPeople += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umPeople += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td>&nbsp;Name: <input class=\"input--register\" type=\"text\" name=\"age\" value=\""+ age +"\"></td>";
			umPeople += "\t\t\t<td>Age: <input class=\"input--register\" type=\"text\" name=\"age\" value=\""+ people.getAge() +"\"></td>";
			umPeople += "\t\t\t<td>Work: <input class=\"input--register\" type=\"text\" name=\"work\" value=\""+ people.getWork() +"\"></td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t</table>";
			umPeople += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umPeople += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar People (ID " + people.getID() + ")</b></font></td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td>&nbsp;Name: "+ people.getName() +"</td>";
			umPeople += "\t\t\t<td>Age: "+ people.getAge() +"</td>";
			umPeople += "\t\t\t<td>Work: "+ people.getWork() +"</td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t\t<tr>";
			umPeople += "\t\t\t<td>&nbsp;</td>";
			umPeople += "\t\t</tr>";
			umPeople += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PEOPLE>", umPeople);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Peoples</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/people/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/people/list/" + FORM_ORDERBY_NAME + "\"><b>Name</b></a></td>\n" +
        		"\t<td><a href=\"/people/list/" + FORM_ORDERBY_AGE + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<People> people1;
		if (orderBy == FORM_ORDERBY_ID) {                 	people1 = peopleDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NAME) {		people1 = peopleDAO.getOrderByName();
		} else if (orderBy == FORM_ORDERBY_AGE) {			people1 = peopleDAO.getOrderByAge();
		} else {											people1 = peopleDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (People p : people1) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getName() + "</td>\n" +
            		  "\t<td>" + p.getAge() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/people/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/people/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePeople('" + p.getID() + "', '" + p.getName() + "', '" + p.getAge() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PEOPLE>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String name = request.queryParams("name");
		int age = Integer.parseInt(request.queryParams("age"));
		String work = request.queryParams("work");
		
		String resp = "";
		
		People people = new People(-1, name, age, work);
		
		if(peopleDAO.insert(people) == true) {
            resp = "People (" + name + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "People (" + name + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		People people = (People) peopleDAO.get(id);
		
		if (people != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, people, FORM_ORDERBY_NAME);
        } else {
            response.status(404); // 404 Not found
            String resp = "People " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		People people = (People) peopleDAO.get(id);
		
		if (people != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, people, FORM_ORDERBY_NAME);
        } else {
            response.status(404); // 404 Not found
            String resp = "People " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		People people = peopleDAO.get(id);
        String resp = "";       

        if (people != null) {
        	people.setName(request.queryParams("name"));
        	people.setAge(Integer.parseInt(request.queryParams("age")));
        	people.setWork(request.queryParams("work"));
        	peopleDAO.update(people);
        	response.status(200); // success
            resp = "People (ID " + people.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "People (ID \" + people.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        People people = peopleDAO.get(id);
        String resp = "";       

        if (people != null) {
            peopleDAO.delete(id);
            response.status(200); // success
            resp = "People (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "People (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
