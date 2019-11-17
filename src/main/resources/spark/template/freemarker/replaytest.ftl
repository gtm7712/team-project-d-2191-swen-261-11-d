<div class="page">
  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">
    <#if error??>
        <p> ${error} </p>
    </#if>
    <!-- Provide a message to the user, if supplied. -->

      <#list allGames?keys as games>
          <#if games.isGameOver()>
              <a href = "/replay"> ${games.toString()} </a>
              </br>
          </#if>
      </#list>

  </div>

</div>
</body>

</html>
