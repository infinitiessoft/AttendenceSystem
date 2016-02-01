#AttendanceSystem
---

A sample project to demonstrate how a web app can be built using a Spring / AngularJs stack. The frontend is based on Angular and the backend is composed of JSON REST web services based on Spring / JPA, secured with Spring Security.


##Table of Contents
   * *[OVERVIEW](#overview)*
   * *[TECHNOLOGIES USED](#technologies-used)*
   * *[FRONTEND](#frontend)*
   * *[BACKEND](#backend)*
   * *[SECURITY](#security)*
   * *[USAGE](#usage)*
   * *[ACKNOWLEDGEMENTS](#acknowledgements)*
   
###OVERVIEW

This project is meant to create an advanced *attendance* tracking of the Employees. It is designed keeping scalable and code-reusability, i.e, the same project can be altered by changing few variables to get the result as expected. Furthermore, there's scope of adding more functionality without disturbing the existing one. This was made possible by using Object Oriented Programming(JAVA) and  Frameworks(Jersey and Spring).

###TECHNOLOGIES USED

<ol>

<li>Angularjs</li>
<li>Bootstrap</li>
<li>Hibernate / JPA</li>
<li>Spring DI</li>
<li>Spring Retry</li>
<li>Spring Data</li>
<li>Spring Security</li>
<li>JAVA</li>
<li>Jersey</li>
<li>Postgresql</li>
</ol>

###FRONTEND OVERVIEW

The project is a web application made by AngularJs framework and integrate with Smart-table and Angular-formly. Smart-table plugin is a  use to display data in a table with filtering and sorting feature. Angular-formly plugin is use to render form by JSON data. The UI Style of this project is adapte from SB admin angularJS theme. 

###BACKEND OVERVIEW

The backend is based on Java 7, Jesery, Spring 4, JPA 2/ Hibernate 4. The Spring configuration is based on Java. The main Spring modules used where Spring MVC and Spring Security. The backend was built using the DDD approach, which includes a domain model, services, resources and transfer for frontend/backend data transfer.

The REST web services are based on Spring MVC and JSON. The unit tests are made with spring test and the REST API.

### SECURITY

The Spring Security module was used to secure the REST backend (these guidelines are in general applied). The application can be made to run in HTTPS-only mode via a server parameter, meaning no pages will be served if the user tries to access it via HTTP.

The Spring Security Form Login mode was used, with fallback to HTTP-Basic Authentication for non-browser based HTTP clients. Protection is in-place against CSRF (cross-site request forgery).

The passwords are not stored in the database in plain text but in a digested form, using the Spring Security SHA1 password encoder 

###USAGE

  Usually the Roles are divided into three types there are:
        
      1.Administrator - Manager Level2
      2.Administrator - Manager Level1
      3.Employees

####Administrator:

When you login as administrator you can find list of Records, Events, Employees, Roles, Department, RecordTypes, Leave-setting and EmployeeLeave where he can edit, delete or create new Entries. And he is the one who approves for the Events and Leave that the employee applies

 ***General Rule:*** When an Employee apply for a Leave based on days and reason first the Manager1 will recieve the notification by mail. And he is the one who permits and rejects the Events.
  If the Manager1 permits for Events the Manager2 recieves the Event notification for permission else Manager2 will not be notified.

`Events:` when an employee is willing to attend some events that are available he can book the date and wait for the reply from the admin. Once admin is ready with his answer he provides the action based on his decision.

**Event Requested by the Employee**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_3.png "Event")
  

**The Decision of the Manager1**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_4.png "Event")
  

**Event if the Manager whats to know the reason for Leave he can just click on corresponding row and see the reason**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_7.png "Event")


**Event to Manager2 if Manager1 is Permitted**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_6.png "Event")


  


`Leave-setting:` Mainly there are two types **official and unofficial leaves**
 
 **Leave Setting**
 ![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_8.png "Leave Setting")


If the Admin wants to add any other extra Records he can use the `+` symbol on the right end corner to Create


 **Create AttendRecord Type**
 ![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_9.png "Leave Setting")
  
**After Adding the New Record**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_10.png "Leave Setting")
  

Admin can also assign the days allotment for AttendRecord by giving the Name, selecting the type from the drop down menu, he can also mention to which corresponding year he's assigning the Record to and how many days has been alloted. 


**Assigning the Record**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_17.png "Leave Setting")

**Can view the Record based on Year**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_13.png "Leave Setting")



 ***Official***- where the employee can apply in the Annual leave provided by the company, so he can keep the record of used and unused days of leave from Annual Leave.

***UnOfficial***- are the Employees Personal or Sick Leave. 


**Complete Records of Employees to Admin**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_14.png "Leave Setting")
  

In this the Admin can also get the Record into the Excel sheet by clicking the Export Button on Left hand corner of the Page



                   By this the Employee can notify his activities to the Admin.


####Employee:

When you login in as employee you can give all your personal details and apply for Leaves  or events 

**Employee Info**
 ![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160120_4.png "Employee Info")
  
And the Employee can Create a new Event Clicking `+` on the right hand corner of the page.Employee can select the start date and the end date with timing,then he can select the type of Leave he wants to apply by using drop down menu and finally he can mention the Reason for applying.


**Add AttendRecord of Employees**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_1.png "Employee Info")
  
**After Adding the Record he can edit or delete it before getting the Permission from the Admin**
![Alt text](https://github.com/pohsun-huang/AttendenceSystem/blob/master/image/Snip20160126_2.png "Employee Info")
  
  
###ACKNOWLEDGEMENTS
The Attendance system relies upon these free and openly available projects:

####BACKEND
- [Jersey](https://jersey.java.net/)
- [Spring security](http://projects.spring.io/spring-security/)
- [Spring framework](https://projects.spring.io/spring-framework/)
- [Spring data jpa](http://projects.spring.io/spring-data-jpa/)
- [Hibernate](http://hibernate.org/orm/)
- [Spring retry](https://github.com/spring-projects/spring-retry)
- [Google client api](https://developers.google.com/api-client-library/java/)

####FRONTEND
- [Angularjs](https://angularjs.org/)
- [Angular-formly](http://angular-formly.com/)
- [Smart-table](http://lorenzofox3.github.io/smart-table-website/)
- [SB Amdin Angular](http://startangular.com/product/sb-admin-angular-theme/)

####REFERENCE
- [Spring Security and Angular JS](https://spring.io/guides/tutorials/spring-security-and-angular-js/)
- [angular-rest-springsecurity](https://github.com/philipsorst/angular-rest-springsecurity)

