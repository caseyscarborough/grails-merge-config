<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main">
  <title>Configuration Management</title>
  <asset:javascript src="configuration.js" />
</head>
<body>
  <h1>Configuration Management</h1>
  <span id="configuration-save-url"   style="display:none">${createLink(controller: 'configuration', action: 'save')}</span>
  <span id="configuration-delete-url" style="display:none">${createLink(controller: 'configuration', action: 'delete')}</span>
  <hr>
  <h3>List</h3>
  <g:render template="configurationList" />
  <hr>
  <h3>New</h3>
  <g:render template="configurationNew" />
  <button class="btn btn-primary" id="configuration-create-button">Save</button>
  <script>
    $(document).ready(function() {
      Configuration.init(${configsCount});
    });
  </script>
</body>
</html>