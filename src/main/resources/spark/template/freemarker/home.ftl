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
          <h2>Players to Challenge</h2>
          </br>
          <#list allUsers?keys as name>
              <#if name != currentUser && !allUsers[name].isInGame()>
                  <a href = "/game?otherPlayer=${name}"> ${name} </a>
                  </br>
              </#if>
          </#list>
          <#if gameList??>
          <h2>Games to watch</h2>
          </br>
          <#list gameList?keys as game>
              <a href = "/replay/game?gameID=${game}"> #${game}: ${gameslist[game + ""].getReplayHelper().getRed()} vs ${gameslist[game + ""].getReplayHelper().getWhite()}</a><br>
          </#list>
      </#if>
      <#else>
          <#include "message.ftl" />
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
