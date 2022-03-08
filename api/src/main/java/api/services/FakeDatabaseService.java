package api.services;

import java.util.ArrayList;

import api.models.User;

public class FakeDatabaseService {

  private static ArrayList<User> users = new ArrayList<User>();

  public static void initializeFakeUsers() {
    var mathieu = new User("Mathieu", "BES", "pwdTest");
    var garik = new User("Garik", "DERMINJYAN", "flk");
    var florian = new User("Florian", "QUINESTPASLA", "dksjskj");

    users.add(mathieu);
    users.add(garik);
    users.add(florian);
  }

  public static User[] getUsers() {
    return users.toArray(new User[0]);
  }

}