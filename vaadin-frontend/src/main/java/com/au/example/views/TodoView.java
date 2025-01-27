package com.au.example.views;

import com.au.example.dto.Status;
import com.au.example.dto.TodoDTO;
import com.au.example.dto.TodoResponseDTO;
import com.au.example.service.BackendService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class TodoView extends VerticalLayout {


  public TodoView(BackendService backendService) {

    MenuBar menuBar = new MenuBar();
    menuBar.addItem("Todo", e -> getUI().ifPresent(ui -> ui.navigate("/")));
    menuBar.addItem("Todo History", e -> getUI().ifPresent(ui -> ui.navigate("/todo-history")));
    add(menuBar);

    var task = new TextField();
    var button = new Button("Add");
    var todos = new VerticalLayout();
    todos.setPadding(false);
    button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    button.addClickShortcut(Key.ENTER);
    button.addClickListener(click -> {
      var todo = backendService.saveTodo(new TodoDTO(null, task.getValue(), false));
      todos.add(createCheckbox(backendService, todo.todoDTO()));
      task.clear();
    });

    backendService.getTodos()
        .forEach(todo -> todos.add(createCheckbox(backendService, todo.todoDTO())));

    add(
        new H1("Todo"),
        new HorizontalLayout(task, button),
        todos
    );
  }

  private Component createCheckbox(BackendService backendService, TodoDTO todo) {

    return new Checkbox(todo.title(), todo.done(), e -> {
      TodoResponseDTO todoResponseDTO = backendService.updateTodo(
          new TodoDTO(todo.id(), todo.title(), todo.done()));
      if (todoResponseDTO.status() == Status.FAILED) {
        showErrorNotification();
        return;
      }
      showNotification();
    });
  }

  private void showNotification() {
    Notification.show("Task updated");

  }

  private void showErrorNotification() {
    Notification notification = new Notification();
    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

    Div text = new Div(new Text("Failed to update task"));

    Button closeButton = new Button(new Icon("lumo", "cross"));
    closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
    closeButton.setAriaLabel("Close");
    closeButton.addClickListener(event -> notification.close());

    HorizontalLayout layout = new HorizontalLayout(text, closeButton);
    layout.setAlignItems(Alignment.CENTER);

    notification.add(layout);
    notification.open();
  }
}
