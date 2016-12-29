<html>
<head>
    <script type="text/javascript">
        function sayHello(){
            alert("hello");
        }
    </script>
</head>
<body>
<h2>J2EE Web Application secured by JDBC Realm</h2>

<h3>Welcome :: </h3>
<%=session.getAttribute("user")%>

<input type="button" value="hello" onclick="sayHello();"/>

</body>
</html>
