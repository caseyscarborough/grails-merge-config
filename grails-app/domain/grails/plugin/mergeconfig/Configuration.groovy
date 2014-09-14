package grails.plugin.mergeconfig

import org.codehaus.groovy.grails.commons.GrailsApplication
import static grails.plugin.mergeconfig.ConfigurationType.STRING

class Configuration {

  String key
  String value
  String description
  ConfigurationType type

  def configurationService
  static transients = ['configurationService']

  static constraints = {
    description nullable: true
    key unique: true
  }

  static mapping = {
    // Key is a reserved word in some SQL dialects
    key column: "key_name"
  }

  Object getValueAsType() {
    configurationService.getValueWithType(this)
  }

  Map toMap() {
    def config = [
        id: id,
        key: key,
        value: valueAsType,
        description: description ?: "None",
        type: type?.toMap()
    ]
    return config
  }

  static void merge(GrailsApplication application) {
    application.config.merge(Configuration.reload(application))
    application.config.merge(Configuration.update())
  }

  static void remove(GrailsApplication application, config) {
    config?.delete(flush: true)
    application.config.remove(config?.key)
    Configuration.merge(application)
  }

  private static ConfigObject update() {
    def configObject = new ConfigObject()
    Configuration.all.each { Configuration config ->
      def string = (config.type == STRING) ? "${config?.key} = \"${config?.value}\"" : "${config?.key} = ${config?.value}\n"
      configObject.merge(new ConfigSlurper().parse(string))
    }
    configObject
  }

  private static ConfigObject reload(GrailsApplication application) {
    ConfigObject config = new ConfigObject()
    config.merge(new ConfigSlurper().parse(application.classLoader.loadClass("Config")))
    application.config.locations.each { String location ->
      String configFile = location.split("file:")[0]
      config.merge(new ConfigSlurper().parse(new File(configFile).text))
    }
    config
  }
}
