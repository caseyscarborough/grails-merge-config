package grails.plugin.mergeconfig

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder as LH

import static grails.plugin.mergeconfig.ConfigurationType.*

@Transactional
class ConfigurationService {

  def grailsApplication
  def messageSource

  Map create(GrailsParameterMap params) {
    if (!grailsApplication.config.flatten().get(params?.key)) {
      return [status: "fail", message: getMessage("configuration.not.exists")]
    }

    def config = new Configuration(
        key: params?.key,
        value: (String) params?.value,
        type: ConfigurationType.valueOf(params?.type),
        description: params?.description
    )

    if (configurationHasTypeMismatch(config)) {
      return [status: "fail", message: getMessage("configuration.type.${config?.type?.simpleName?.toLowerCase()}.mismatch")]
    }

    if (!config.save(flush: true)) {
      def error = config?.errors?.fieldError
      def message
      try {
        message = getMessage("configuration.${error?.field}.${error?.code}")
      } catch (NoSuchMessageException e) {
        message = getMessage("configuration.unknown.error")
      }
      return [status: "fail", message: message]
    }

    Configuration.merge(grailsApplication)
    return [status: "success", data: [config: config?.toMap()]]
  }

  Map delete(Long id) {
    def config = Configuration.get(id)

    if (!config) {
      return [status: "fail", message: getMessage("configuration.not.found")]
    }

    Configuration.remove(grailsApplication, config)
    return [status: "success", data: null]
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
    if (config?.value?.isNumber()) {
      switch (config?.type) {
        case INTEGER:
          return Integer.parseInt(config?.value)
        case DOUBLE:
          return Double.parseDouble(config?.value)
      }
    }
    setTypeToString(config)
    return config?.value
  }

  protected Boolean setTypeToString(Configuration config) {
    config?.type = STRING
    config?.save(flush: true)
  }

  protected Boolean configurationHasTypeMismatch(Configuration config) {
    def valid = false
    if (config?.type == BOOLEAN) {
      if (config?.value != "true" && config?.value != "false") {
        valid = true
      }
    } else if (config?.type == DOUBLE) {
      try {
        Double.parseDouble(config?.value)
      } catch (NumberFormatException e) {
        valid = true
      }
    } else if (config?.type == INTEGER) {
      try {
        Integer.parseInt(config?.value)
      } catch (NumberFormatException e) {
        valid = true
      }
    }
    return valid
  }

  protected String getMessage(String key, List params = []) {
    messageSource.getMessage(key, params.toArray(), LH.locale)
  }
}
