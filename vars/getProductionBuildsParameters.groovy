/**
 * `getBuildsParameters` method gets builds' parameters list by parameter name
 */
@NonCPS
def call(String env, String jobPath, String jobName, String parameterName, String forcedRevision = '', String versionPrefix = '') {
  String versionFormat = /[v]?\d+([.]\d+){1,3}/
  String revisionFilter = /^${versionFormat}$/

  if (versionPrefix != '') {
    revisionFilter = /^${versionPrefix}${versionFormat}$/
  }

  return getBuildsParameters(env, jobPath, jobName, parameterName, forcedRevision, revisionFilter)
}
