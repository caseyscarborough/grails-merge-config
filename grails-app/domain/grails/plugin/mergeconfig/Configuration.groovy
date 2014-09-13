package grails.plugin.mergeconfig

class Configuration {

  String description
  String key
  String value
  Class type

  static constraints = {
    description nullable: true
  }

  static void merge() {
    def application = new Configuration().domainClass.grailsApplication

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
