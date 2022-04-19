package api.controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import api.models.http.ChildPostDTO;
import api.models.http.SigninPostDTO;
import api.models.http.UserPostDTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.models.Child;
import api.models.Parent;
import api.services.UserService;
import api.services.JsonService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

  // #region Properties
  @Autowired
  JsonService jsonService;

  @Autowired
  UserService userService;
  // #endregion

  // #region Get parents
  @RequestMapping(value = "/parents", method = RequestMethod.GET)
  public ResponseEntity<String> getParents() throws JsonProcessingException {
    try {
      ArrayList<Parent> parents = userService.getParents();
      String toReturn = jsonService.stringify("parents", parents);
      return ResponseEntity.ok(toReturn);
    } catch (Exception e) {
      throw e;
    }
  }
  // #endregion

  // #region Add parent
  @RequestMapping(value = "/parents/add", method = RequestMethod.POST)
  public ResponseEntity<String> addParent(@Valid @RequestBody UserPostDTO userPostDTO) throws JsonProcessingException {
    try {
      Parent toReturn = userService.addParent(userPostDTO);
      return ResponseEntity.ok(jsonService.stringify("parent", toReturn));
    } catch (Exception e) {
      throw e;
    }
  }
  // #endregion

  // #region Add child
  @RequestMapping(value = "/parents/{id}/children/add", method = RequestMethod.POST)
  public ResponseEntity<String> addChild(@Valid @RequestBody ChildPostDTO childPostDTO, @PathVariable String id)
      throws JsonProcessingException {
    try {
      ArrayList<Child> toReturn = userService.addChild(childPostDTO, id);
      return ResponseEntity.ok(jsonService.stringify("children", toReturn));
    } catch (Exception e) {
      throw e;
    }
  }
  // #endregion

  // #region Update existing child
  @RequestMapping(value = "/parents/{id}/children/{childId}/update", method = RequestMethod.POST)
  public ResponseEntity<String> updateChild(@Valid @RequestBody ChildPostDTO childPostDTO, @PathVariable String id,
      @PathVariable String childId)
      throws JsonProcessingException {
    try {
      Child toReturn = userService.updateChild(childPostDTO, id, childId);
      return ResponseEntity.ok(jsonService.stringify("child", toReturn));
    } catch (Exception e) {
      throw e;
    }
  }
  // #endregion

  // #region Delete parent
  @RequestMapping(value = "/parents/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteParent(@PathVariable String id) {
    try {
      userService.deleteParent(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      throw e;
    }
  }
  // #endregion

  // #region Signin
  @RequestMapping(value = "/parents/signin", method = RequestMethod.POST)
  public ResponseEntity<String> signin(@RequestBody SigninPostDTO signinPostDTO) throws JsonProcessingException {
    try {
      Optional<Parent> toReturn = userService.areSigninCredentialsCorrect(signinPostDTO);
      if (toReturn.isPresent()) {
        return ResponseEntity.ok(jsonService.stringify("parent", toReturn.get()));
      } else {
        return ResponseEntity.badRequest().build();
      }
    } catch (Exception e) {
      throw e;
    }
  }
  // #endregion

  // #region Get coef number
  @RequestMapping(value = "/parents/{id}/fiscal", method = RequestMethod.GET)
  public ResponseEntity<String> getParentCoef(@PathVariable("id") String parentID,
      @RequestParam(name = "annualIncome") Double annualIncome) throws JsonProcessingException {
    try {
      final var parent = this.userService.getParentByID(parentID);

      String toReturn = jsonService.stringify("fiscalCoef", parent.getCoefNumber(annualIncome));
      return ResponseEntity.ok(toReturn);
    } catch (Exception e) {
      throw e;
    }
  }
  // #endregion
}