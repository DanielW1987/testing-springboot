package rocks.danielw.mockmvc;

import javax.validation.constraints.NotBlank;

public class Person {

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  public Person() {
  }

  public Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }

}
