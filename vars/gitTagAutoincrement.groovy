/**
 * `gitTagAutoincrement` method get last tag from projectGitRepo, filtering by buildVersion (for example a.b.X) and
 *  increment 3rd version by one. Then return new tag as string.
 *
 *  These formats are supported (lastTag -> newTag):
 *    1.1.2 -> 1.1.3
 *    1.1.2.1 -> 1.1.3
 *    1.1 -> 1.1.1
 *
 * @param projectGitRepo String Repository from which tags are loaded
 * @param buildVersion Current master build (in format a.b)
 */
def call(String projectGitRepo, String buildVersion) {
  return sh(
    script: """\
  
      git ls-remote --tags --sort=\"-version:refname\" git@github.com:${projectGitRepo} \"${buildVersion}*\" \
            | head -1 \
            | awk '/${buildVersion}[.]*/{print \$2}' \
            | sed -E 's/refs\\/tags\\///' \
            | awk -F'.' '{\$3+=1}{print \$0RT}' OFS='.' ORS='' \
            | sed -E 's/(([0-9]+[.]{0,1}){3})(.*)/\\1/' \
            | sed -E 's/[.]\$//' \
            | (grep ^ || echo \"${buildVersion}.0\")
      
    """.stripIndent(),
    returnStdout: true
  ).trim()
}

