# tomcat-secure-webapp
J2EE Web Application with servlets & RESTful resources secured with JDBC Realm of Apache Tomcat 7.

I am using RESTEasy implementation for developing RESTful resources. User and role data is maintained in MySQL DB.
restasy-spring library is leveraged to integrate with Spring.

DB tables structure:

``` 
USERS
    user_name (PK)
    user_pass

ROLES
    role_name (PK)
    user_name (PK)
```

This application does following:
 1. Configure JDBC Realm in Tomcat
 2. Apply security in web.xml (login and logout features) to your web application using above created Realm.
 3. Retrieve principal/user object in the Servlet/rest resource.

##### Configure JDBC Realm in Tomcat:
In this case I added below Realm element under Catalina Engine tag as:

```<Realm className="org.apache.catalina.realm.JDBCRealm"
        		driverName="org.gjt.mm.mysql.Driver"
   				connectionURL="jdbc:mysql://localhost:3306/authority?user=prashast&amp;password=password&amp;useSSL=false"
       			userTable="users" userNameCol="user_name" userCredCol="user_pass"
   				userRoleTable="roles" roleNameCol="role_name"/>
```

More information can be found at: https://tomcat.apache.org/tomcat-7.0-doc/realm-howto.html#JDBCRealm

##### Apply security in web.xml of your web application

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

##### Retrieve user/principal object in Servlet/REST resource:

Use following line of code to obtain Principal object from ServletRequest object:

```
request.getUserPrincipal().getName();
```