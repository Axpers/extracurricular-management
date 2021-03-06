package api.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

import api.models.http.ChildPostDTO;

public class Parent extends User {
  private double coefNumber;
  private double annualIncome;
  private ArrayList<Child> children;

  public Parent(UUID id, String firstname, String lastname, String password, String email, String address,
      String phoneNumber) {
    super(id, firstname, lastname, password, email, address, phoneNumber);

    this.coefNumber = 0;
    this.annualIncome = 0;
    this.children = new ArrayList<>();
  }

  public double getCoefNumber() {
    if (this.annualIncome == 0) {
      return 0;
    } else {
      this.coefNumber = this.computeCoefNumber(this, this.annualIncome);
      return this.coefNumber;
    }
  }

  public double getCoefNumber(double annualIncome) {
    this.annualIncome = annualIncome;
    this.coefNumber = this.computeCoefNumber(this, this.annualIncome);
    return this.coefNumber;
  }

  public double getAnnualIncome() {
    return this.annualIncome;
  }

  public ArrayList<Child> getChildren() {
    return children;
  }

  public void addChild(ChildPostDTO childPostDTO) {
    this.children.add(new Child(UUID.randomUUID(), childPostDTO));
    this.computeCoefNumber(this, this.annualIncome);
  }

  public void removeChild(String id) {
    this.children.removeIf(child -> child.getId().toString().equals(id));
  }

  public int getDependentChildrenCount() {
    return (int) this.children.stream().filter(child -> {
      return child.isDependent();
    }).count();
  }

  public Child childById(String id) {
    return this.children.stream().filter(child -> UUID.fromString(id).equals(child.getId())).findFirst().orElse(null);
  }

  // Formula used by the Evry City Council
  private double computeCoefNumber(Parent parent, double annualIncome) {
    if (parent != null) {
      this.annualIncome = annualIncome;
      final var dependChildrenCount = this.getDependentChildrenCount();

      double coef = 0;
      double calcul = (Math.max(annualIncome / 0.9 / 12,
          551 * (1.5 + 0.3 * dependChildrenCount) + 0.61 * annualIncome / 0.9 / 12)
          - (1.5 + 0.3 * dependChildrenCount) * 240) / (2 + (dependChildrenCount));

      if (calcul >= 1300)
        coef = 0.8f;
      else if (calcul >= 418)
        coef = 0.175 + 0.625 / 1300 * calcul;
      else if (calcul >= 200)
        coef = -0.022 + (0.197 / 418 + 0.625 / 1300) * calcul;
      else
        coef = 0.168;

      coef *= 100;

      DecimalFormat df = new DecimalFormat("00.00");
      return Double.parseDouble(df.format(coef));
    } else
      return 0;
  }
}
