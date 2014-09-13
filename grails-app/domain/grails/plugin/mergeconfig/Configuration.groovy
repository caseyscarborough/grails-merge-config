package grails.plugin.mergeconfig

import org.codehaus.groovy.grails.commons.GrailsApplication

class Configuration {

  String description
  String key
  String value
  String type

  static constraints = {
    key unique: true
  }

  def getValueWithType() {
    def returnValue = value
    if (type == "java.lang.Integer") {
      try {
        returnValue = Integer.parseInt(returnValue)
      } catch(NumberFormatException e) {
        returnValue = value
        type = "java.lang.String"
        save(flush: true)
      }
    }
    return returnValue
  }

  static void merge(GrailsApplication application) {
    application.config.merge(Configuration.load())
  }

  static void add(GrailsApplication application, Configuration config) {
    application.config.put(config?.key, config?.valueWithType)
  }

  static void remove(GrailsApplication application, Configuration c) {
    application.config.remove(c?.key)
    ConfigObject config = new ConfigObject()
    application.config.locations.each { String location ->
      String configFile = location.split("file:")[0]
      config.merge(new ConfigSlurper().parse(new File(configFile).text))
    }
    application.config.put(c?.key, config.get(c?.key))

    c.delete(flush: true)
  }

  static ConfigObject load() {
    def persistedConfig = new ConfigObject()

    Configuration.all.each { Configuration config ->
      persistedConfig.put(config.key, config.value)
    }

    return persistedConfig
  }
}
