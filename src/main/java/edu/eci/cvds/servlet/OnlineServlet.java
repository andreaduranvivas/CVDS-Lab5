package edu.eci.cvds.servlet;
import edu.eci.cvds.servlet.model.Todo;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@WebServlet(
    urlPatterns = "/onlineServlet"
)

public class OnlineServlet extends HttpServlet{
    
    static final long serialVersionUID = 35L;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Writer responseWriter = resp.getWriter();

        String res = "";

        try {

            Optional<String> optid = Optional.ofNullable(req.getParameter("id"));
            int id = Integer.parseInt(optid.isPresent() && !optid.get().isEmpty() ? optid.get() : "");
            Todo item = Service.getTodo(id);

            ArrayList<Todo> infoToDo = new ArrayList<>();
            infoToDo.add(item);

            resp.setStatus(HttpServletResponse.SC_OK);

            responseWriter.write(Service.todosToHTMLTable(infoToDo));
            responseWriter.flush();

        } catch (NumberFormatException e){

            resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            res= "Requerimiento Inválido";

        } catch (MalformedURLException e){

            res="Error interno en el Servidor ";
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e){

            res="No existe un item con el identificador dado";
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            e.printStackTrace();
        } finally{

            responseWriter.write(res);
        }





        resp.setStatus(HttpServletResponse.SC_OK);

        responseWriter.flush();

        }
}