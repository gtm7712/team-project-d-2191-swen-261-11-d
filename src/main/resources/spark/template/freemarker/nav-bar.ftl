 <div class="navigation">
  <#if currentUser??>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
    <form action = "./signin" method = "post">
        Username:
        <input type = "text" name = "username"/>
        <input type = "submit" value = "sign in"/>
    </form>
  </#if>
 </div>
