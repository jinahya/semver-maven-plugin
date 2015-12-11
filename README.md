# semver-maven-plugin
[![Dependency Status](https://www.versioneye.com/user/projects/566aa44f43cfea00310001f6/badge.svg?style=flat)](https://www.versioneye.com/user/projects/566aa44f43cfea00310001f6)
a plugin checks whether project's version compiles [semantic versioning](http://semver.org/).
## usages
Add this plugin.
```xml
<plugin>
  <groupId>com.github.jinahya</groupId>
  <artifactId>semver-maven-plugin</artifactId>
</plugin>
```
## constraints
## level 1: entire version string
The entire version string(`@{project.version}`) is checked for pattern matching with follwing expression.
```
(\d+)\.(\d+)\.(\d+)(-([^\+]*))?(\+(.+))?
-----  -----  -----  ---------    -----
X     .Y     .Z     -R           +M
```
## level 2
### Normal versions
Each normal versions(`X`, `Y`, and `Z`) checked by following constraints
 * Must be non-negative integers.
 * Must not contain leading zeros. Note that [single `0` is valid](https://github.com/mojombo/semver/issues/185).
### Pre-release version
 * Each dot separated identifiers must match `[0-9A-Za-z-]+`.
 * When there are only numeric characters, no leading zeros are allowed.
### Build metadata
 * Each dot separated identifiers must match `[0-9A-Za-z-]+`.
 * Leading zeros are allowed even if there are only numeric characters.
