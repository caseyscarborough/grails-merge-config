<tr id="config-${config?.id}">
  <td><strong>${config?.description}</strong></td>
  <td><strong>${config?.key}</strong></td>
  <td>${config?.valueWithType}</td>
  <td>${config?.type}</td>
  <td><a href="#" class="configuration-delete" onclick="Configuration.remove(${config?.id})">Delete</a></td>
</tr>