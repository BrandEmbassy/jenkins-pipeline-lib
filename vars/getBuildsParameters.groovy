/**
 * `getBuildsParameters` method gets builds' parameters list by parameter name
 */
@NonCPS
def call(String env, String jobPath, String jobName, String parameterName) {
  String revisionFilter = /.*/
  if (revisionFilter == /.*/ && env in ['staging', 'prod']) {
    revisionFilter = /^[v]?\d+([.]\d+)+$/
  }

  Set<String> builds = []
  def job = jenkins.model.Jenkins.instance.getItem(jobPath).getJob(jobName)
  job.builds.each { build ->
    if (build.getResult() && hudson.model.Result.SUCCESS.isWorseOrEqualTo(build.getResult())) {
      def buildEnv = build.getEnvironment(hudson.model.TaskListener.NULL)
      def buildRevision = buildEnv.get(parameterName) ?: ''
      if (buildRevision ==~ revisionFilter) {
        builds.add(buildRevision)
      }
    }
  }
  return builds.toList()
}
