package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/populate-database")
public class DatabasePopulationController {

    private final UserRepository userRepository;

    public DatabasePopulationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    String[] firstNamesArray = {"John", "Mike", "Chris", "Mary", "Carla", "Paige", "Jordan", "Don", "Andrea",
            "Sandra", "Bob", "Margot", "Ben", "Luke", "Elizabeth", "Anna", "Daniel", "David", "Dora", "Emma", "Mia",
            "William", "Zoey", "Alice", "Carlos", "Jacob", "Ariana", "Sofia", "Camila", "Juan", "Oliver", "Valentina"};

    String[] lastNamesArray = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Hall",
            "Martinez", "Marques", "Lopez", "Pop", "Thomas", "Anderson", "Peterson", "Moore", "Jackson", "Lee", "Perez",
            "Martin", "Clark", "Ramirez", "Walker", "Young", "Allen", "Torres", "Li", "Hill", "King", "Scott", "Wright"};

    String[] detailsArray = {"Welder", "Pilot", "Jockey", "Programmer", "Singer", "Entertainer", "Driver", "Salesman"};

    List<String> firstNamesList = Arrays.asList(firstNamesArray);
    List<String> lastNamesList = Arrays.asList(lastNamesArray);
    List<String> detailsList = Arrays.asList(detailsArray);


    @PostMapping("/i-am-sure-i-want-to-do-this")
    public ResponseEntity<List<UserEntity>> insertInDatabase() {
        for (int i = 0; i <= 5000; i++) {
            Collections.shuffle(firstNamesList);
            Collections.shuffle(lastNamesList);
            Collections.shuffle(detailsList);

            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(firstNamesList.get(10));
            userEntity.setName(lastNamesList.get(10));
            userEntity.setDetails(detailsList.get(5));

            userRepository.save(userEntity);
        }
        List<UserEntity> usersFromDb = userRepository.findAll();

        return new ResponseEntity<>(usersFromDb, HttpStatus.OK);
    }
}
