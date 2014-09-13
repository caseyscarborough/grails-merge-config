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
    if (type == "Integer") {
      try {
        returnValue = Integer.parseInt(returnValue)
      } catch(NumberFormatException e) {
        returnValue = value
        type = "String"
        save(flush: true)
      }
    } else if (type == "Boolean") {
      return (value.equalsIgnoreCase("true"))
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
    def config = Configuration.reloadFromFiles(application)
    application.config.remove(c?.key)
    application.config.put(c?.key, config.flatten().get(c?.key))

    c.delete(flush: true)
  }

  static ConfigObject load() {
    def persistedConfig = new ConfigObject()
    Configuration.all.each { Configuration config ->
      persistedConfig.put(config.key, config.value)
    }
    return persistedConfig
  }

  private static ConfigObject reloadFromFiles(GrailsApplication application) {
    ConfigObject config = new ConfigObject()
    config.merge(new ConfigSlurper().parse(new File("grails-app/conf/Config.groovy").text))
    application.config.locations.each { String location ->
      String configFile = location.split("file:")[0]
      config.merge(new ConfigSlurper().parse(new File(configFile).text))
    }
    config
  }
}
