#AttendanceSystem
---

A sample project to demonstrate how a web app can be built using a Spring / AngularJs stack. The frontend is based on Angular and the backend is composed of JSON REST web services based on Spring / JPA, secured with Spring Security.

### Release notes
Check them here: [Release notes](https://github.com/infinitiessoft/AttendenceSystem/blob/master/RELEASENOTES.md)

###USAGE
The Complete instructions for using AttendanceSystem is found [here](http://infinitiessoft.github.io/AttendenceSystem/)

###INSTALLATION
1. The Attendance System can be downloaded from the [Release](https://github.com/infinitiessoft/AttendenceSystem/releases)
2. Extracting the source from the Attendance System tarball is a simple matter of untarring:
```
$ tar xvf attendance-X.X.X.tar
```
3. Editing the configuration files under `PREFIX/WEB-INF/system.properties`
```
$ vi PREFIX/WEB-INF/system.properties
```
Attributes:
- pageSize: The size of a page of output.
- smtp.host: Server that will send to email.
- smtp.port: Port to connect to the SMTP server.
- smtp.username: SMTP username. 
- smtp.password: SMTP password.
- mail.url: the link added in email send by the Attendance System that is used to link the Attendance System website.
- mail.header: the email header.
- mail.footer: the email footer.
- google.calendar.service.account.p12: The Google calendar service account credential.
- google.calendar.application.name: The name of the client application accessing the Google Calendar service.
- google.calendar.service.account.email: The email address to Google Calendar account.
- google.calendar.account.user: The Google Calendar service account.
- google.calendar.id: The Google Calendar service account id.
- db.username: The database username.
- db.password: The database password.
- db.driver: The driver used to connect to database.
- db.url: database: The address point to the database to which you with to connect.
4.Moving the Attendance System .war directory to `%CATALINA_HOME%\webapps`
```
$ mv attendance %CATALINA_HOME%\webapps.
```


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


