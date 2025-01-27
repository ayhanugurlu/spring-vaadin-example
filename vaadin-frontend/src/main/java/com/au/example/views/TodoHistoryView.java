package com.au.example.views;

import com.au.example.dto.TodoHistoryDTO;
import com.au.example.service.BackendService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("/todo-history")
@PermitAll
public class TodoHistoryView extends VerticalLayout {

  public TodoHistoryView(BackendService backendService) {
    MenuBar menuBar = new MenuBar();
    Grid<TodoHistoryDTO> grid = new Grid<>();
    grid.setItems(backendService.getTodoHistory());
    grid.addColumn(TodoHistoryDTO::getId).setHeader("ID");
    grid.addColumn(TodoHistoryDTO::getTitle).setHeader("Task");
    grid.addColumn(TodoHistoryDTO::getCreatedOn).setHeader("Created On");
    grid.addColumn(TodoHistoryDTO::getLastUpdatedOn).setHeader("Last Updated On");
    menuBar.addItem("Todo", e -> getUI().ifPresent(ui -> ui.navigate("/")));
    menuBar.addItem("Todo History", e -> getUI().ifPresent(ui -> ui.navigate("/todo-history")));

    add(menuBar);
    add("Todo History");
    add(grid);
  }

}
