package testSuites;

import main.java.Engine.DriverFactory;
import main.java.web.pageClasses.Home;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

public class TaskTwoTest extends DriverFactory {

    @Features("ABSA Task 2")
    @Description("* Navigate to link")
    @Stories("Task 2.a")
    @Test(priority = 4)
    public void NavigateToUrl() {

        Home.navigateToUrl();

        log("\n=============== NavigateToUrl test executed successfully ===============\n","INFO",  "text");
    }

    @Features("ABSA Task 2")
    @Description("* Validate that you are on the User List Table")
    @Stories("Task 2.b")
    @Test(priority = 5)
    public void ValidateUserListTable() {

        Home.validateTable();

        log("\n=============== ValidateUserListTable test executed successfully ===============\n","INFO",  "text");
    }

    @Features("ABSA Task 2")
    @Description("* Click Add user")
    @Stories("Task 2.c")
    @Test(priority = 6)
    public void ClickAddUser() {

        Home.clickAddUser();

        log("\n=============== ClickAddUser test executed successfully ===============\n","INFO",  "text");
    }

    @Features("ABSA Task 2")
    @Description("* Add users")
    @Stories("Task 2.d")
    @Test(priority = 7)
    public void AddUsersToTable() {

        Home.addUsersToTable();

        log("\n=============== AddUsersToTable test executed successfully ===============\n","INFO",  "text");
    }

    @Features("ABSA Task 2")
    @Description("* Ensure that User Name (*) is unique on each run")
    @Stories("Task 2.e")
    @Test(priority = 8)
    public void ValidateUserNameIsUnique() {

        Home.validateUserNameIsUnique();

        log("\n=============== ValidateUserNameIsUnique test executed successfully ===============\n","INFO",  "text");
    }

    @Features("ABSA Task 2")
    @Description("* Ensure that your users are added to the list")
    @Stories("Task 2.f")
    @Test(priority = 9)
    public void ValidateUserAreAddedToList() {

        Home.validateUserAreAddedToList();

        log("\n=============== ValidateUserAreAddedToList test executed successfully ===============\n","INFO",  "text");
    }
}
