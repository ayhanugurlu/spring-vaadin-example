package com.au.example.service;

import com.au.example.dto.Status;
import com.au.example.dto.TodoDTO;
import com.au.example.dto.TodoHistoryDTO;
import com.au.example.dto.TodoResponseDTO;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BackendService {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${backend.url:http://localhost:8081}")
  private String baseUrl;

  private static final String URL_TEMPLATE = "%s/%s";

  public TodoResponseDTO saveTodo(TodoDTO todo) {
    ResponseEntity<TodoDTO> resp = restTemplate.postForEntity(
        String.format(URL_TEMPLATE, baseUrl, "todo"),
        todo, TodoDTO.class);
    if (resp.getStatusCode().is2xxSuccessful()) {
      return new TodoResponseDTO(Status.OK, resp.getBody());
    } else {
      return new TodoResponseDTO(Status.FAILED, resp.getBody());
    }
  }

  public TodoResponseDTO updateTodo(TodoDTO todoDTO) {
    restTemplate.put(String.format("%s/%s/%s", baseUrl, "todo", todoDTO.id()), todoDTO, Long.class);
    return new TodoResponseDTO(Status.OK, todoDTO);
  }


  public TodoResponseDTO getTodo(Long id) {
    ResponseEntity<TodoDTO> responseEntity = restTemplate.getForEntity(
        String.format("%s/%s/%s", baseUrl, "todo", id), TodoDTO.class, Long.class);
    return new TodoResponseDTO(Status.OK, responseEntity.getBody());
  }

  public List<TodoResponseDTO> getTodos() {
    ResponseEntity<List<TodoDTO>> responseEntity = restTemplate.exchange(
        String.format(URL_TEMPLATE, baseUrl, "todos"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<TodoDTO>>() {
        }
    );
    return Objects.requireNonNull(responseEntity.getBody())
        .stream().map(todo -> new TodoResponseDTO(Status.OK, todo))
        .toList();

  }


  public List<TodoHistoryDTO> getTodoHistory() {
    ResponseEntity<List<TodoHistoryDTO>> responseEntity = restTemplate.exchange(
        String.format(URL_TEMPLATE, baseUrl, "todo/history"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<TodoHistoryDTO>>() {
        }
    );
    return Objects.requireNonNull(responseEntity.getBody());
  }

}
