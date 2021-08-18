# TopTal_Project
This project is part of TopTal QA Assessment to test Web and API.

# TopTal Assessment Automation framework#

## FEATURES ##

The **TopTal Assessment Automation framework** implements a 'Page Object Model' approach.

Page Object Model(POM) Framework has recently become a very popular test automation framework since it enables easy test maintenance 
and reduces the duplication of code ie. maximizes re-usabilty of code.
The main advantage of POM is that if the UI changes for any page, no tests need to be updated.  
Instead, we just need to change only the code within the page objects ie. only at one place.

Another new feature is the implementation of a new build system called Gradle which replaces 'Ant'.
Gradle nicely combines both Ant and Maven, taking the best from both tools. 
Flexibility from Ant and convention over configuration, dependency management and plugins from Maven.

This Automation framework allows you to run tests on almost any browser and OS. 

It also provides the ability to run locally on Windows, Mac, Ubuntu with minimal changes to any config files.

## TOOLS USED ##

IntelliJ IDE, Java(version 14), Gradle(version 5.2.1), RestAssured, Allure Report, TestNG, SDK(1.8).

---

## FRAMEWORK STRUCTURE ##

### build.gradle files ###
The build.gradle file found on the **Automation framework root folder** contains all the common dependencies that are used in the project

### Engine ###
Contains functionality that form the backbone of the framework
**DriverFactory.java**
- contains configurations to setup and connect to webdriver locally
- specifies the desired capabilities for each browser instance i.e. Browser version, OS/Platform, timeouts etc
- functionality to quit browser/app on completion or failure of a test

### pageObjects ###
- provides the base structure and properties of a page object which extend onto the tests

### utils ###
Contains logic or functionality needed to run tests with efficiency:

**PropertyFileReader.java** - reads element id's, xpaths and other identifier types that are specified in the ".properties" files

**SeleniumUtility.java** - where the custom action methods are stored

---

## EXECUTING TEST SUITE ##
1. Go to top menu bar and navigate to Run > Edit Configurations...
2. On the 'Run/Debug Configurations' window, 'Add New Configuration' by clicking the (+) at the top-left corner
3. Select 'Gradle'
4. Then fill in the fields below:
	* Gradle projects: AbsaTest (Navigate to the project location)
	* Tasks: clean run_test
	* Arguments: --stacktrace
5. Press 'Apply', then 'Ok' button
6. Finally to run the test
	* Press the Green Play button
	* Or navigate to Run > Run 'AbsaTest [clean run_test]'
	
## GENERATE ALLURE REPORT on Mac ##
Reference: https://docs.qameta.io/allure/

1. Execute a test

2. From the terminal, navigate to the project root:

	e.g. $ cd ~/Documents/Projects/Java/AbsaTest
	
3. Execute the terminal command: 

	$ allure serve ./build/allure-results/ 
	
---

**Edit a file, create a new file, and clone from Bitbucket in under 2 minutes**

When you're done, you can delete the content in this README and update the file with details for others getting started with your repository.

*We recommend that you open this README in another tab as you perform the tasks below. You can [watch our video](https://youtu.be/0ocf7u76WSo) for a full demo of all the steps in this tutorial. Open the video in a new tab to avoid leaving Bitbucket.*

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: *Delete this line to make a change to the README from Bitbucket.*
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

---

## Create a file

Next, you’ll add a new file to this repository.

1. Click the **New file** button at the top of the **Source** page.
2. Give the file a filename of **contributors.txt**.
3. Enter your name in the empty file space.
4. Click **Commit** and then **Commit** again in the dialog.
5. Go back to the **Source** page.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).
