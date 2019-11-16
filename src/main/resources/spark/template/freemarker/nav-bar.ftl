 <div class="navigation">
  <#if currentUser??>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
    <form id="signin" action="/signin" method="get">
      <a href="#" onclick="event.preventDefault(); signin.submit();">Sign In</a>
    </form>
  </#if>
  <form id="replay" action="/replayl" method="GET">
    <a href="#" onclick="event.preventDefault(); replay.submit();">Load Replay</a>
  </form>
 </div>
