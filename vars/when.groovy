import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

/**
 * `when` statement that can be used instead of `if` statement to mark
 * stage as skipped if passed @condition.
 *
 * @param condition Boolean expression
 * @param body Code to be executed if @condition is `true`
 */
def call(boolean condition, body) {
  def config = [:]
  body.resolveStrategy = Closure.OWNER_FIRST
  body.delegate = config

  if (condition) {
    body()
  } else {
    echo "Stage ${STAGE_NAME} skipped"
    Utils.markStageSkippedForConditional(STAGE_NAME)
  }
}

