package grails.plugin.mergeconfig

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ConfigurationController {

  static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

  def configurationService

  def index() {
    def configs = Configuration.list(sort: 'key')
    [configs: configs, configsCount: configs.size(), types: [String, Integer]]
  }

  def save() {
    renderResult(configurationService.create(params))
  }

  protected renderResult(result) {
    switch(result.status) {
      case "success":
        response.status = 200
        break
      case "fail":
        response.status = 400
        break
      case "error":
        response.status = 500
        break
    }
    render result as JSON
  }
}
