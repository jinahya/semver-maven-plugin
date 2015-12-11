# semver-maven-plugin
[![Dependency Status](https://www.versioneye.com/user/projects/566aa44f43cfea00310001f6/badge.svg?style=flat)](https://www.versioneye.com/user/projects/566aa44f43cfea00310001f6)
[![Build Status](https://travis-ci.org/jinahya/semver-maven-plugin.svg?branch=develop)](https://travis-ci.org/jinahya/semver-maven-plugin)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.jinahya/semver-maven-plugin.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.jinahya%22%20a%3A%22semver-maven-plugin%22)
[![Domate via Paypal](https://img.shields.io/badge/donate-paypal-blue.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_cart&business=A954LDFBW4B9N&lc=KR&item_name=GitHub&amount=5%2e00&currency_code=USD&button_subtype=products&add=1&bn=PP%2dShopCartBF%3adonate%2dpaypal%2dblue%2epng%3aNonHosted)
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
<hr/>
[![Domate via Paypal](https://img.shields.io/badge/donate-paypal-blue.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_cart&business=A954LDFBW4B9N&lc=KR&item_name=GitHub&amount=5%2e00&currency_code=USD&button_subtype=products&add=1&bn=PP%2dShopCartBF%3adonate%2dpaypal%2dblue%2epng%3aNonHosted)
