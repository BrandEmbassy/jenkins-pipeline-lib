/**
 * `updateStringBuildParameter` method updates build parameter of currently running build
 */
@NonCPS
def call(String parameterName, String parameterValue) {
    List<ParameterValue> newParams = new ArrayList<>();
    newParams.add(new StringParameterValue(parameterName, parameterValue))
    currentBuild.rawBuild.replaceAction(currentBuild.rawBuild.getAction(ParametersAction.class).createUpdated(newParams))
}
