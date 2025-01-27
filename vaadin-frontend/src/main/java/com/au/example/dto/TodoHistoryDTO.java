package com.au.example.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoHistoryDTO {
  Long id;
  String title;
  boolean done;
  Instant createdOn;
  Instant lastUpdatedOn;
}