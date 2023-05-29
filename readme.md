###Implementations
1. Implemented JWT token based authentication for APIs. Only the apis that has "/api/v1/user" are non-authenticated. All other APIs need token to access
2. On successful user creation and user login, a valid token is responded to user.
3. Implemented Caffeine caching to cache getEmployee method calls to reduce the database calls to fetch id based employee data.
4. Added swagger documentation annotation to controller classes and entity classes.
5. Implemented JUnit test cases achieving 94% test coverage in class level. Can improve more on method level and liine level.
6. Took the liberty to create a department entity and database table to handle entity relation between employee and department. 

###How to test the application
1. create departments using /api/v1/department/createDept api
2. create user using /api/v1/user/signup api
3. update user using /api/v1/employees - PUT method
4. we can get all employees using /api/v1/employees
5. logout using /api/v1/user/logout

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 8 years experience in Java and SpringBoot.
- I have worked across different domains and teams. I am both team player and individual contributor.
- I enjoy handling teams and managing my team in the journey towards accomplishing 100 % completion of tasks with efficient code and within the time lines
- ALthough I carry main strength in Java+spring boot , am ready to adapt myself to new challenges and technologies
- I have also worked on AWS
