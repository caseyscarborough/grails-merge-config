import grails.plugin.mergeconfig.Configuration

class MergeConfigGrailsPlugin {
  // the plugin version
  def version = "0.1"
  // the version or versions of Grails the plugin is designed for
  def grailsVersion = "2.4 > *"
  // resources that are excluded from plugin packaging
  def pluginExcludes = [
      "grails-app/views/error.gsp"
  ]

  def title = "Merge Config Plugin" // Headline display name of the plugin
  def author = "Casey Scarborough"
  def authorEmail = "caseyscarborough@gmail.com"
  def description = '''\
This plugin gives users the ability to manage their Configuration options, typically
held in Config.groovy, from within the application without requiring a restart.
'''

  // URL to the plugin's documentation
  def documentation = "https://github.com/caseyscarborough/grails-merge-config"

  def license = "MIT"

  // Location of the plugin's issue tracker.
  def issueManagement = [ system: "GitHub", url: "https://github.com/caseyscarborough/grails-merge-config/issues" ]

  // Online location of the plugin's browseable source code.
  def scm = [ url: "https://github.com/caseyscarborough/grails-merge-config" ]
}
