/**
 * `sortBuildRevisions` method sorts list of semantic revisions from newest to oldest
 */
@NonCPS
def call(List versions) {
  return versions.collectEntries{
    [(it=~/\\d+|\\D+/).findAll().collect{it.padLeft(5,'0')}.join(''),it]
  }.sort().values().reverse()
}
