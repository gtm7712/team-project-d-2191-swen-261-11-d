<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">
  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">
    <#if error??>
        <p> ${error} </p>
    </#if>
    <!-- Provide a message to the user, if supplied. -->
      <#if currentUser??>
          <#list allUsers?keys as name>
              <#if name != currentUser && !allUsers[name].isInGame()>
                  <a href = "/game?otherPlayer=${name}"> ${name} </a>
                  </br>
              </#if>
          </#list>
      <#else>
          <#include "message.ftl" />
      </#if>
      <#if gameList??>
          <#list gameList?keys as game>
              ${game}
              <#if gameList.get(game).getGameStatus()>
                  <a href = "/replay/game?gameID=${game}"> ${game} </a>
                  </br>
              </#if>
          </#list>
      </#if>

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
