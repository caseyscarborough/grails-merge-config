<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main">
  <title>Configuration Management</title>
  <asset:javascript src="configuration.js" />
</head>

<body>
  <h1>Configuration Management</h1>
  <span id="configuration-save-url" style="display:none">${createLink(controller: 'configuration', action: 'save')}</span>
  <span id="configuration-delete-url" style="display:none">${createLink(controller: 'configuration', action: 'delete')}</span>
  <hr>
  <h3>List</h3>
  <div id="config-list">
    <g:render template="configurationList" model="[configs: configs, types: types]" />
    <p id="no-config-records"<g:if test="${configsCount != 0}"> style="display:none"</g:if>><g:message code="configuration.no.records" /></p>
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
  <button class="btn btn-primary" id="configuration-create-button">Save</button>
  <hr>
  <h3>Current Grails Configuration</h3>
  <ul>
    <g:each in="${currentConfig}" var="config">
      <li><strong>${config?.key}</strong> - ${config?.value}</li>
    </g:each>
  </ul>
  <script>
    $(function() { Configuration.init(${configsCount}) })
  </script>
</body>
</html>