# tomcat-secure-webapp
J2EE Web Application with servlets & RESTful resources secured with Realm of Apache Tomcat 7. 

I am using RESTEasy implementation for developing RESTful resources. restasy-spring library is leveraged to integrate with Spring.

User and role data to be maintained in DB. You can use either of Realms (JDBC/MongoDB) depending on your choice/where you maintain the user data.

MySQL DB tables structure:

``` 
USERS
    user_name (PK)
    user_pass

ROLES
    role_name (PK)
    user_name (PK)
```

MongoDB collections structure:

```

user
    _id
    userName
    password
    roles: [
                {
                    _id
                    name
                    description
                },
                {
                    _id
                    name
                    description
                }
            ]
 
 role
    _id
    name
    description
            
``` 

## Steps for running the application
    
    - Create either of the DB and populate it with some dummy values.
    - Configure Tomcat Realm.
    - Build this maven application and then deploy onto Tomcat 7 container. 


### Create database and populate with dummy data

MongoDB:

```
Connect to Mongo shell and type following to create your mongo collections:

use authority;

usr={userName:"prashast", password:”P@ssw0rd”,roles:[{_id:ObjectId("58654db5f374f0d27a5cdc22"),name:"ROLE_USER",description:"User Role"},{_id:ObjectId("58654dc5f374f0d27a5cdc23"),name:"ROLE_ADMIN",description:"Admin Role"}]};
db.user.insert(usr);

role={_id:ObjectId("58654db5f374f0d27a5cdc22"),name:"ROLE_USER",description:"User Role"}
db.role.insert(role);

```


### Configuring Realm
 1. Configure JDBC Realm in Tomcat
 2. Configure MongoDB Realm in Tomcat

#### Configure JDBC Realm in Tomcat:
In this case I added below Realm element under Catalina Engine tag as (assuming database name is authority):

```
<Realm className="org.apache.catalina.realm.JDBCRealm"
        		driverName="org.gjt.mm.mysql.Driver"
   				connectionURL="jdbc:mysql://localhost:3306/authority?user=prashast&amp;password=password&amp;useSSL=false"
       			userTable="users" userNameCol="user_name" userCredCol="user_pass"
   				userRoleTable="roles" roleNameCol="role_name"/>
                
```

More information can be found at: https://tomcat.apache.org/tomcat-7.0-doc/realm-howto.html#JDBCRealm

#### Configure MongoDB Realm in Tomcat
Tomcat doesn't come with standard implementation of Mongo Realm, hence we'll use custom realm implemenation here.
Source for this realm can be found here: https://github.com/tobrien/mongo-realm

Add below Realm element under Catalina Engine tag (assuming database name is authority):

```
<Realm className="com.daprota.m2.realm.MongoDBRealm"
               connectionURL="mongodb://localhost:27017/authority"/>
```

Download jars from below locations and save them to {TOMCAT_HOME}/lib folder.
https://mvnrepository.com/artifact/org.slf4j/slf4j-api/1.7.22
https://oss.sonatype.org/content/repositories/releases/org/mongodb/mongodb-driver/3.0.1/
https://oss.sonatype.org/content/repositories/releases/org/mongodb/mongodb-driver-core/3.0.1/
https://oss.sonatype.org/content/repositories/releases/org/mongodb/bson/3.0.1/
https://github.com/gzugic/mongortom/blob/master/m2-mongodb-realm.jar






### About application security configuration

 1. Apply security in web.xml (login and logout features) to your web application using above created Realm.
 2. Retrieve principal/user object in the Servlet/rest resource.

#### Apply security in web.xml of your web application

Add following code to your web.xml:

```
<security-constraint>
        <web-resource-collection>
            <web-resource-name>ADMIN</web-resource-name>
            <description>Security config that only allows JDBC authenticated users to access the HTTP resource</description>
            <url-pattern>/*</url-pattern>
            <http-method>GET</http-method>
            <http-method>POST</http-method>
        </web-resource-collection>

        <auth-constraint>
            <role-name>*</role-name>
        </auth-constraint>

        
        <user-data-constraint>
            <transport-guarantee>CONFIDENTIAL</transport-guarantee>
        </user-data-constraint>
        

    </security-constraint>

    <login-config>
        <auth-method>FORM</auth-method>
        <realm-name>LDAP Authentication Sample</realm-name>
        <form-login-config>
            <form-login-page>/logon.jsp</form-login-page>
            <form-error-page>/logonError.jsp</form-error-page>
        </form-login-config>
    </login-config>

    <security-role>
        <role-name>*</role-name>
    </security-role>
    
```

Don't forget to create logon.jsp and logonError.jsp files to your webapp folder for login pages.

For more on Web application security read here: https://docs.oracle.com/cd/E13222_01/wls/docs61/webapp/web_xml.html#1017885

#### Retrieve user/principal object in Servlet/REST resource:

Use following line of code to obtain Principal object from ServletRequest object:

```
request.getUserPrincipal().getName();
```
