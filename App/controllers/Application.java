package controllers;

import com.google.inject.Inject;
import models.Task;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

public class Application extends Controller {

    @Inject
    public FormFactory formFactory;

    public Result index() {
        return redirect(routes.Application.tasks());
    }

    public Result tasks() {
        return ok(views.html.index.render(Task.all(), formFactory.form(Task.class))
        );
    }

    public Result newTask() {
        Form<Task> filledForm = formFactory.form(Task.class).bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.index.render(Task.all(), filledForm));
        } else {
            Task.create(filledForm.get());
            return redirect(routes.Application.tasks());
        }
    }

    public Result deleteTask(Long id) {
        Task.delete(id);
        return redirect(routes.Application.tasks());
    }

}