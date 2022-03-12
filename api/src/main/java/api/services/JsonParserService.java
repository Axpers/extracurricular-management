package api.services;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class JsonParserService {
  ObjectMapper objectMapper;

  @PostConstruct
  public void initializeObjectMapper() {
    this.objectMapper = new ObjectMapper();
  }

  public String parseToJson(String key, Object message) throws JsonParseException, IOException {
    return ("{" + this.objectMapper.writeValueAsString(key) + " : " + this.objectMapper.writeValueAsString(message)
        + "}");
  }

}
