#AttendanceSystem
---

A sample project to demonstrate how a web app can be built using a Spring / AngularJs stack. The frontend is based on Angular and the backend is composed of JSON REST web services based on Spring / JPA, secured with Spring Security.


##Table of Contents
   * *[OVERVIEW](#overview)*
   * *[TECHNOLOGIES](#technologies used)*
   * *[FRONTEND](#frontend)*
   * *[BACKEND](#backend)*
   * *[SECURITY](#security)*
   * *[USAGE](#usage)*
   
####OVERVIEW

This project is meant to create an advanced *attendance* tacking of the Employees. It is designed keeping scalable and code-reusability, i.e, the same project can be altered by changing few variables to get the result as expected. Furthermore, there's scope of adding more functionality without disturbing the existing one. This was made possible by using Object Oriented Programming(JAVA) and  Frameworks(Jersey and Spring).

####TECHNOLOGIES USED

<ol>

<li>Angularjs</li>
<li>Bootstrap</li>
<li>Hibernate / JPA</li>
<li>JAVA</li>
<li>Jersey</li>
<li>Postgresql</li>
</ol>

####FRONTEND OVERVIEW

The sample project is a web application with an AngularJs-based frontend for form validation

####BACKEND OVERVIEW

The backend is based on Java 8, Spring 4, JPA 2/ Hibernate 4. The Spring configuration is based on Java. The main Spring modules used where Spring MVC and Spring Security. The backend was built using the DDD approach, which includes a domain model, services, resources and transfer for frontend/backend data transfer.

The REST web services are based on Spring MVC and JSON. The unit tests are made with spring test and the REST API.

#### SECURITY

The Spring Security module was used to secure the REST backend (these guidelines are in general applied). The application can be made to run in HTTPS-only mode via a server parameter, meaning no pages will be served if the user tries to access it via HTTP.

The Spring Security Form Login mode was used, with fallback to HTTP-Basic Authentication for non-browser based HTTP clients. Protection is in-place against CSRF (cross-site request forgery).

The passwords are not stored in the database in plain text but in a digested form, using the Spring Security SHA1 password encoder 

####USAGE

######Admin:

When you login as admin you can find list of Records, Events, Employees, Roles, Department, RecordTypes, Leave-setting and EmployeeLeave where he can edit, delete or create new Entries. And he is the one who approves for the Events and Leave that the employee applies

`Events:` when an employee is willing to attend some events that are available he can book the date and wait for the reply from the admin. Once admin is ready with his answer he provides the action based on his decision.

`Leave-setting:` Mainly there are two types **official and unofficial leaves**
 
 ***Official***- where the employee can apply in the Annual leave provided by the company, so he can keep the record of used and unused days of leave from Annual Leave.

***UnOfficial***- are the Employees Personal or Sick Leave. 

                   *By this the Employee can notify his activities to the Admin.*


######Employee:

When you login in as employee you can give all your personal details and apply for Leaves  or events 



