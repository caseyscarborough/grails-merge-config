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