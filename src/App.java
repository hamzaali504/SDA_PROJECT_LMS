import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

class UserService {
    private static UserService instance = null;
    private List<User> users;

    private UserService() {
        users = new ArrayList<>();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean registerUser(String name, String email, String password) {
        if (emailExists(email)) {
            return false;
        }
        User user = new User(name, email, password);
        users.add(user);
        return true;
    }

    private boolean emailExists(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public List<User> getAllUsers() {
        return users;
    }
}


class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = UserService.getInstance();
    }

    public String register(String name, String email, String password) {
        if (userService.registerUser(name, email, password)) {
            return "User registered successfully!";
        } else {
            return "Email already exists. Please try another.";
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();

        System.out.println("=== Library Registration ===");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        String result = userController.register(name, email, password);
        System.out.println(result);

        scanner.close();
    }
}
