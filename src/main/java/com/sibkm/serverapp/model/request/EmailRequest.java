package com.sibkm.serverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

  private String to;
  private String subject;
  private String body;
  private String attachment;
}