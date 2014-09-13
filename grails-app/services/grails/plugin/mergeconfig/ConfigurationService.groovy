package grails.plugin.mergeconfig

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.context.i18n.LocaleContextHolder as LH

@Transactional
class ConfigurationService {

  def grailsApplication
  def messageSource

  Map create(GrailsParameterMap params) {
    def config = new Configuration(
      key: params?.key,
      value: (String) params?.value,
      type: params?.type,
      description: params?.description
    )

    if (!config.save(flush: true)) {
      def error = config?.errors?.fieldError
      def message = messageSource.getMessage("configuration.${error?.field}.${error?.code}", [].toArray(), LH.locale)
      return [status: "fail", message: message]
    }

    Configuration.merge(grailsApplication)
    return [status: "success", data: [config: config]]
  }
}
