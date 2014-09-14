package grails.plugin.mergeconfig

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder as LH

import static grails.plugin.mergeconfig.ConfigurationType.*

@Transactional
class ConfigurationService {

  def grailsApplication
  def messageSource

  Map create(GrailsParameterMap params) {
    if (!configuration.get(params?.key)) {
      return [status: "fail", message: getMessage("configuration.not.exists")]
    }

    def config = new Configuration(
      key: params?.key,
      value: (String) params?.value,
      type: ConfigurationType.valueOf(params?.type),
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

  Map getConfiguration() {
    grailsApplication.config.flatten()
  }

  Object getValueWithType(Configuration config) {
    if (config?.type == INTEGER || config?.type == DOUBLE) {
      return getNumericValue(config)
    } else if (config?.type == BOOLEAN) {
      return (config?.value?.equalsIgnoreCase("true"))
    } else if (config?.type == LIST) {
      def list = []
      config?.value?.split(",")?.each { value ->
        list << value
      }
      return list
    }
    return config?.value
  }

  protected Object getNumericValue(Configuration config) {
    def returnValue = config?.value
    try {
      switch(config?.type) {
        case INTEGER:
          returnValue = Integer.parseInt(config?.value)
          break
        case DOUBLE:
          returnValue = Double.parseDouble(config?.value)
          break
      }
    } catch(NumberFormatException e) {
      // Type is not numeric
      setTypeToString(config)
    }
    return returnValue
  }

  protected Boolean setTypeToString(Configuration config) {
    config?.type = STRING
    config?.save(flush: true)
  }

  protected String getMessage(String key, List params=[]) {
    messageSource.getMessage(key, params.toArray(), LH.locale)
  }
}
