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
          <option value="${type}">${type.simpleName}</option>
        </g:each>
      </select>
    </div>
  </div>
</div>