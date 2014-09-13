package grails.plugin.mergeconfig

import org.codehaus.groovy.grails.commons.GrailsApplication

class Configuration {

  String description
  String key
  String value
  Class type

  static constraints = {
    description nullable: true
  }

  static void merge(GrailsApplication application) {
    def mergedConfig = new ConfigObject()
    def currentConfig = application.config
    def persistedConfig = Configuration.load()

    mergedConfig.putAll(currentConfig.merge(persistedConfig))
    application.config = mergedConfig
  }

  static ConfigObject load() {
    def persistedConfig = new ConfigObject()

    Configuration.all.each { Configuration config ->
      persistedConfig.put(config.key, config.value)
    }

    return persistedConfig
  }
}
