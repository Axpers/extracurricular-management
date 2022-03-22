package api.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.PostConstruct;

import api.models.http.ParentPostDTO;
import com.google.common.hash.Hashing;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import api.models.Parent;
import api.models.enums.EnvKey;
import api.services.env.EnvGlobalUseService;

@Service
@Scope("singleton")
public class UserService {
  private ArrayList<Parent> parents = new ArrayList<Parent>();

  @PostConstruct
  private void initFakeParents() {
    addParent(new ParentPostDTO("Mathieu", "BES", "pwdTest1", "matDu91@gmail.fr", "surLaA6", "06qlqchose"));
    addParent(new ParentPostDTO("Garik", "DERMINJYAN", "pwdTest2", "gd@gmail.fr", "surLePeriph", "07qlqchose"));
    addParent(new ParentPostDTO("Florian", "CARBONI", "pwdTest3", "bgDeL'IBGBI@gmail.fr", "versRis", "118 218"));
    addParent(new ParentPostDTO("Fawaz", "MOUSSOUGAN", "pwdTest4", "criminal@gmail.fr", "unknown", "Call the police"));
  }

  public ArrayList<Parent> getParents() {
    return parents;
  }

  public Parent addParent(ParentPostDTO parentPostDto) {
    UUID id = UUID.randomUUID();

    String saltedPassword = EnvGlobalUseService.getValue(EnvKey.SALT_HASH_KEY) + parentPostDto.getPassword();
    String hashedPassword = Hashing.sha256().hashString(saltedPassword, StandardCharsets.UTF_8).toString();

    Parent parent = new Parent(id, parentPostDto.getFirstname(), parentPostDto.getLastname(), hashedPassword,
        parentPostDto.getEmail(), parentPostDto.getAddress(), parentPostDto.getPhoneNumber(),
        parentPostDto.getFiscalNumber());
    this.parents.add(parent);
    return parent;
  }

}