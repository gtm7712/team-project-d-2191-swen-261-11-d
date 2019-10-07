<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <div class="body">
    <#if logIN??>
        <p>
            ${logIN}
        </p>
    </#if>
    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <form name="userForm" action = "/signin" method = "post" onsubmit = "return validateUsername()">
        Username:
        <input type = "text" name = "username"/>
        <input type = "submit" value = "Sign in"/>
    </form>

  </div>

</div>
</body>

<script>

  function validateUsername() {
    var uname = document.forms["userForm"]["username"].value;
    if (uname == "" || uname == " ") {
      alert("Username cannot be empty");
      return false;      
    }
    if (!uname.match(/^[\w\-\s]+$/)) {
      alert ("Username must contain only alphanumeric characters.");
      return false;
    }
  }

</script>

</html>
