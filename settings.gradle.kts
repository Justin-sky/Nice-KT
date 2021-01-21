


pluginManagement {
    repositories {
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven(url = "https://plugins.gradle.org/m2/")
        gradlePluginPortal()
    }
}

include("subProjects:nice-log")
findProject(":subProjects:nice-log")?.name = "nice-log"
include("subProjects:nice-tools")
findProject(":subProjects:nice-tools")?.name = "nice-tools"
include("subProjects:nice-crypto")
findProject(":subProjects:nice-crypto")?.name = "nice-crypto"
include("subProjects:nice-scaffold")
findProject(":subProjects:nice-scaffold")?.name = "nice-scaffold"
include("apps:app-gateway")
findProject(":apps:app-gateway")?.name = "app-gateway"
include("apps:app-login")
findProject(":apps:app-login")?.name = "app-login"
include("apps:app-gameserver")
findProject(":apps:app-gameserver")?.name = "app-gameserver"
include("lib-core")
include("apps:lib-core")
findProject(":apps:lib-core")?.name = "lib-core"
include("apps:app-allinone")
findProject(":apps:app-allinone")?.name = "app-allinone"
include("apps:app-api")
findProject(":apps:app-api")?.name = "app-api"
