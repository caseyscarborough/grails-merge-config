<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main">
  <title>Configuration Management</title>
  <asset:javascript src="configuration.js" />
</head>

<body>
  <h1>Configuration Management</h1>
  <hr>
  <h3>List</h3>
  <div id="config-list">
    <g:render template="configurationList" model="[configs: configs, types: types]" />
    <g:if test="${configsCount == 0}">
      <p id="no-config-records"><g:message code="configuration.no.records" /></p>
    </g:if>
  </div>
  <hr>
  <h3>New</h3>
  <div class="row">
    <div class="col-md-4">
      <div class="form-group">
        <label for="new-config-description">Description</label>
        <input class="form-control" id="new-config-description" placeholder="Description" type="text">
      </div>
    </div>
    <div class="col-md-4">
      <div class="form-group">
        <label for="new-config-key">Key</label>
        <input class="form-control" id="new-config-key" placeholder="Key" type="text">
      </div>
    </div>
    <div class="col-md-2">
      <div class="form-group">
        <label for="new-config-value">Value</label>
        <input class="form-control" id="new-config-value" placeholder="Value" type="text">
      </div>
    </div>
    <div class="col-md-2">
      <div class="form-group">
        <label for="new-config-type">Type</label>
        <select class="form-control" id="new-config-type">
          <g:each in="${types}" var="type">
            <option value="${type.name}">${type.name}</option>
          </g:each>
        </select>
      </div>
    </div>
  </div>
  <button class="btn btn-primary" id="configuration-create-button" data-url="${createLink(controller: 'configuration', action: 'save')}">Save</button>
  <hr>
  <h3>Current Grails Configuration</h3>
  <g:each in="${grailsApplication.config}" var="config">
    ${config}
  </g:each>
  <script>
    $(function() { Configuration.init() })
  </script>
</body>
</html>