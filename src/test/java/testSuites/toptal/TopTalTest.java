package testSuites.toptal;

import main.java.Engine.DriverFactory;
import main.java.api.TaskOneMethods;
import main.java.utils.SetEnvironmentDataUtility;
import main.java.web.pageClasses.Home;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

public class TopTalTest extends DriverFactory {

    SetEnvironmentDataUtility setEnv = new SetEnvironmentDataUtility();
    TaskOneMethods taskOne = new TaskOneMethods();
































    //TODO Comment out code below
    //region <Demo>

//    @Features("Task 1")
//    @Description("Perform an API request to produce a list of all dog breeds")
//    @Stories("Task 1.a")
//    @Test(priority = 0)
//    public void GetListOfAllBreeds() {
//
//        response = taskOne.getListOfAllBreeds();
//        taskOne.validateGetListOfAllBreeds(response, 200);
//
//        System.out.println("\n=============== GetListOfAllBreeds test executed successfully ===============\n");
//    }
//
//    @Features("Task 1")
//    @Description("Verify “retriever” breed is within the list")
//    @Stories("Task 1.b")
//    @Test(priority = 1)
//    public void VerifyRetriever() {
//
//        response = taskOne.getListOfAllBreeds();
//        taskOne.validateVerifyRetriever(response, 200);
//
//        System.out.println("\n=============== VerifyRetriever test executed successfully ===============\n");
//    }
//
//    @Features("Task 2")
//    @Description("* Navigate to link")
//    @Stories("Task 2.a")
//    @Test(priority = 4)
//    public void NavigateToUrl() {
//
//        Home.navigateToUrl();
//
//        log("\n=============== NavigateToUrl test executed successfully ===============\n","INFO",  "text");
//    }
//
//    @Features("Task 2")
//    @Description("* Validate that you are on the User List Table")
//    @Stories("Task 2.b")
//    @Test(priority = 5)
//    public void ValidateUserListTable() {
//
//        Home.validateTable();
//
//        log("\n=============== ValidateUserListTable test executed successfully ===============\n","INFO",  "text");
//    }

//    @Features("Task 2")
//    @Description("* Validate that you are on the User List Table")
//    @Stories("Task 2.b")
////    @Parameters({"testCase"})
//    @Test()
//    public void ValidateUserListTable(String testCase) {
//
//        setTestDataForTest(testCase); //set Data to extract from sheet
//
//        Home.validateTable();
//
//        log("\n=============== ValidateUserListTable test executed successfully ===============\n","INFO",  "text");
//    }
    //endregion
}
