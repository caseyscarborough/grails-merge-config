class MergeConfigGrailsPlugin {
  def version = "0.1"
  def grailsVersion = "2.3 > *"
  def pluginExcludes = [
      "grails-app/views/error.gsp"
  ]

  def title = "Merge Config Plugin"
  def author = "Casey Scarborough"
  def authorEmail = "caseyscarborough@gmail.com"
  def description = '''\
This plugin gives users the ability to manage their Configuration options, typically
held in Config.groovy, from within the application without requiring a restart.
'''

  def documentation = "https://github.com/caseyscarborough/grails-merge-config"
  def license = "MIT"
  def issueManagement = [ system: "GitHub", url: "https://github.com/caseyscarborough/grails-merge-config/issues" ]
  def scm = [ url: "https://github.com/caseyscarborough/grails-merge-config" ]
}
