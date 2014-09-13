package grails.plugin.mergeconfig

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class ConfigurationService {

  Map create(GrailsParameterMap params) {
    def config = new Configuration(
      key: params?.key,
      value: params?.value,
      class: Class.forName(params?.class),
      description: params?.description
    )

    if (!config.save(flush: true)) {
      def message = new StringBuilder()
      message.append("One or more errors occurred creating the configuration item:\n")
      config.errors.each { error ->
        message.append("- ${error}")
      }
      return [status: "fail", message: message]
    }

    return [status: "success", data: [config: config]]
  }
}
