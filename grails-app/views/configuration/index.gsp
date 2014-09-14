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
    <div id="config-list">
      <table id="config-table" class="table table-hover"<g:if test="${configsCount == 0}"> style="display:none"</g:if>>
        <thead>
        <tr>
          <th>Key</th>
          <th>Value</th>
          <th>Description</th>
          <th>Type</th>
          <th>Options</th>
        </tr>
        </thead>
        <tbody id="config-table-body">
        <g:each in="${configs}" var="config">
          <tr id="config-${config?.id}">
            <td><strong>${config?.key}</strong></td>
            <td>${config?.valueAsType}</td>
            <td>${config?.description ?: "None"}</td>
            <td>${config?.type?.simpleName}</td>
            <td><a href="#" class="configuration-delete" onclick="Configuration.remove(${config?.id})">Delete</a></td>
          </tr>
        </g:each>
        </tbody>
      </table>
      <p id="no-config-records"<g:if test="${configsCount != 0}"> style="display:none"</g:if>><g:message code="configuration.no.records" /></p>
    </div>
  <hr>
  <h3>New</h3>
    <div id="config-new" class="row">
      <div class="col-md-3">
        <div class="form-group">
          <label for="new-config-key">Key</label>
          <input class="form-control" id="new-config-key" placeholder="Key" type="text">
        </div>
      </div>
      <div class="col-md-3">
        <div class="form-group">
          <label for="new-config-value">Value</label>
          <input class="form-control" id="new-config-value" placeholder="Value" type="text">
        </div>
      </div>
      <div class="col-md-4">
        <div class="form-group">
          <label for="new-config-description">Description (Optional)</label>
          <input class="form-control" id="new-config-description" placeholder="Description" type="text">
        </div>
      </div>
      <div class="col-md-2">
        <div class="form-group">
          <label for="new-config-type">Type</label>
          <select class="form-control" id="new-config-type">
            <g:each in="${types}" var="type">
              <option id="${type}" value="${type}">${type.simpleName}</option>
            </g:each>
          </select>
        </div>
      </div>
    </div>
  <button class="btn btn-primary" id="configuration-create-button">Save</button>
  <script>
    $(document).ready(function() {
      Configuration.init(${configsCount});
    });
  </script>
</body>
</html>