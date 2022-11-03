/**
 * `getBuildsParameters` method gets builds' parameters list by parameter name
 */
@NonCPS
def call(String env, String jobPath, String jobName, String parameterName, String forcedRevision = '') {
  String revisionFilter = /.*/

  if (forcedRevision != '' && env in ['staging', 'prod']) {
    revisionFilter = /^[v]?\d+([.]\d+){1,3}$/
  }

  if (revisionFilter == /.*/ && env in ['staging', 'prod']) {
    revisionFilter = /^[v]?\d+([.]\d+){1,2}$/
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

  return builds.toList()
}
