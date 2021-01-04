package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/populate-database")
public class DatabasePopulationController {

    private final UserRepository userRepository;

    public DatabasePopulationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    String[] sourceFirstNames = {"John", "Mike", "Chris", "Mary", "Carla", "Paige", "Jordan", "Don", "Andrea",
            "Sandra", "Bob", "Margot", "Ben", "Luke", "Elizabeth", "Anna", "Daniel", "David", "Dora", "Emma", "Mia",
            "William", "Zoey", "Alice", "Carlos", "Jacob", "Ariana", "Sofia", "Camila", "Juan", "Oliver", "Valentina"};

    String[] sourceLastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Hall",
            "Martinez", "Marques", "Lopez", "Pop", "Thomas", "Anderson", "Peterson", "Moore", "Jackson", "Lee", "Perez",
            "Martin", "Clark", "Ramirez", "Walker", "Young", "Allen", "Torres", "Li", "Hill", "King", "Scott", "Wright"};

    String[] sourceDetails = {"Welder", "Pilot", "Jockey", "Programmer", "Singer", "Entertainer", "Driver", "Salesman",
            "Scientist", "Lawyer", "Janitor", "Electrician", "Mechanic", "Teacher", "Director", "Doctor", "Pharmacist"};

    //username and birthday

    List<String> firstNames = Arrays.asList(sourceFirstNames);
    List<String> lastNames = Arrays.asList(sourceLastNames);
    List<String> details = Arrays.asList(sourceDetails);


    @PostMapping("/i-am-sure-i-want-to-do-this")
    public ResponseEntity<List<UserEntity>> insertInDatabase() {
        List<UserEntity> usersToSaveToDb = new ArrayList<>();

        for (int i = 1; i <= 5000; i++) {
            Collections.shuffle(firstNames);
            Collections.shuffle(lastNames);
            Collections.shuffle(details);

            UserEntity userEntity = new UserEntity();
            userEntity.setId(UUID.randomUUID());
            userEntity.setDetails(details.get(10));
            userEntity.setLastName(lastNames.get(10));
            userEntity.setBirthday(generateBirthday());
            userEntity.setFirstName(firstNames.get(10));
            userEntity.setUsername(generateUserName(userEntity.getFirstName(), userEntity.getLastName()));

            usersToSaveToDb.add(userEntity);
        }

        userRepository.saveAll(usersToSaveToDb);

        List<UserEntity> usersFromDb = userRepository.findAll();

        return new ResponseEntity<>(usersFromDb, HttpStatus.OK);
    }

    /*
     * generates a random birthday from 1970 to 2020
     */
    private LocalDate generateBirthday() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1970, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 12, 31).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    /*
     * generates a username based on first name and last name
     */
    private String generateUserName(String firstName, String lastName) {
        return firstName.toLowerCase() + "." + lastName.toLowerCase();
    }
}
