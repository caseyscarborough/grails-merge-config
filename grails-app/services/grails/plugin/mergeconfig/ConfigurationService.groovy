package grails.plugin.mergeconfig

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder as LH

@Transactional
class ConfigurationService {

  def grailsApplication
  def messageSource

  Map getCurrentConfiguration() {
    Map currentConfiguration = [:]
    grailsApplication.config.flatten().each { config ->
      if (!config?.key?.toString()?.startsWith("dataSource")) {
        currentConfiguration[(config?.key?.toString())] = config?.value
      }
    }
    return currentConfiguration.sort { it.key }
  }

  Map create(GrailsParameterMap params) {
    def existingItem = grailsApplication.config.flatten().get(params?.key)
    println existingItem

    println params?.key

    if (existingItem == null) {
      return [status: "fail", message: getMessage("configuration.not.exists")]
    }

    def config = new Configuration(
      key: params?.key,
      value: (String) params?.value,
      type: params?.type,
      description: params?.description
    )

    if (!config.save(flush: true)) {
      def error = config?.errors?.fieldError
      def message
      try {
        message = getMessage("configuration.${error?.field}.${error?.code}")
      } catch(NoSuchMessageException e) {
        message = getMessage("configuration.unknown.error")
      }
      return [status: "fail", message: message]
    }

    Configuration.add(grailsApplication, config)
    return [status: "success", data: [config: config]]
  }

  Map delete(Long id) {
    def config = Configuration.get(id)

    if (!config) {
      return [status: "fail", message: getMessage("configuration.not.found")]
    }

    Configuration.remove(grailsApplication, config)
    return [status: "success", data: null]
  }

  protected String getMessage(String key, List params=[]) {
    messageSource.getMessage(key, params.toArray(), LH.locale)
  }
}
