# Shared library for Jenkins scripted pipelines

## Jenkins configuration

In section _Administration_ > _System Configuration_ > _Global Pipeline Libraries_ configure new library:

- _Name_: some desired name of library, this name can be used in pipelines to include library using `@Library` 
  annotation;
- _Default version_: branch or tag identifier, e.g. `master`;
- _Load implicitly_: check if you want to load this library implicitly to all scripts, so they do not need to load it 
  using `@Library` annotation;
- _Allow default version to be overridden_: allow to override _Default version_ using `@Library` annotation;
- _Retrieval method_
  - check _Modern SCM_;
  - check _Git_: **DO NOT USE** _GitHub source_, as there is currently bug;
  - _Project Repository_: `https://github.com/BrandEmbassy/infra-ci-lib`;
  - _Credentials_: for private repository use _Username with password_ credentials, if you don't prepared the
    credentials in advance, save current settings **BEFORE** you add credentials, add them in separate configuration 
    step;


## Library

### statement `when`

Statement `when` can be used instead of simple `if` statement inside of `stage` block, to execute statements only if
given expression evaluates `true`. Anyway it will mark parent `stage` as skipped.

Example:

```groovy
stage('test') {
  when (SKIP_TESTS == 'false') {
    build(job: 'unit-tests')
  }
}
```

Stage `test` will be still visible to pipeline graph and Blue Ocean, but it will be marked as skipped, so it will not
be displayed as green with duration.

