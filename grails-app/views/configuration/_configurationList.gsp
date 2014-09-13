<table id="config-table" class="table table-hover"<g:if test="${configsCount == 0}"> style="display:none"</g:if>>
  <thead>
  <tr>
    <th>Description</th>
    <th>Key</th>
    <th>Value</th>
    <th>Type</th>
    <th>Options</th>
  </tr>
  </thead>
  <tbody id="config-table-body">
    <g:each in="${configs}" var="config">
      <g:render template="configurationItem" model="[config: config, types: types]" />
    </g:each>
  </tbody>
</table>