/**
 * `getBuildsParameters` method gets builds' parameters list by parameter name
 */
@NonCPS
def call(String env, String jobPath, String jobName, String parameterName, String forcedRevision = '', String forcedRevisionFilter = '') {
  String revisionFilter = /.*/

  if (forcedRevision != '') {
    revisionFilter = /^[v]?\d+([.]\d+){1,3}$/
  }

  if (revisionFilter == /.*/ && env in ['staging', 'prod']) {
    revisionFilter = /^[v]?\d+([.]\d+){1,3}$/
  }

  // Some of the jobs have a different revision format (e.g. "@brandembassy/cxone-channel-guide@2.0.66")
  if (forcedRevisionFilter != '') {
    revisionFilter = forcedRevisionFilter
  }

  Set<String> builds = []
  def job = jenkins.model.Jenkins.instance.getItem(jobPath).getJob(jobName)

  for (build in job.builds) {
    if (build.getResult() && hudson.model.Result.SUCCESS.isWorseOrEqualTo(build.getResult())) {
      def buildEnv = build.getEnvironment(hudson.model.TaskListener.NULL)
      def buildRevision = buildEnv.get(parameterName) ?: ''

      if (forcedRevision == '' && buildRevision ==~ revisionFilter) {
        builds.add(buildRevision)
      } else if (forcedRevision == buildRevision && forcedRevision ==~ revisionFilter) {
        builds.add(buildRevision)
        break
      }
    }
  }

  if (builds.isEmpty()){
    error("Could not resolve any build with parameter ${parameterName} for ${jobName}")
  }

  return builds.toList()
}
