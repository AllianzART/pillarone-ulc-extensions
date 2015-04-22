grails.project.plugins.dir = "../local-plugins/RiskAnalyticsUlcExtensions-master"
grails.project.dependency.resolver = "maven"

grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits "global" // inherit Grails' default dependencies
    log "warn"

    repositories {
        grailsHome()
        mavenLocal()
        mavenRepo (name:"zh-artisan-test" , url:"http://zh-artisan-test.art-allianz.com:8085/nexus/content/groups/public/") {
            updatePolicy System.getProperty('snapshotUpdatePolicy') ?: 'daily'
        }
        mavenRepo "https://ci.canoo.com/nexus/content/repositories/public-releases"
        grailsCentral()
        mavenCentral()
    }

    String ulcVersion = "7.2.0.9"

    plugins {
        runtime ":release:3.0.1", {
            excludes "groovy"
        }
        compile "com.canoo:ulc:${ulcVersion}"
        test ":code-coverage:1.2.6"
    }

    dependencies {
        compile('jfree:jfreechart:1.0.12')

        //required for ulc tests
        test 'org.mortbay.jetty:jetty:6.1.21', 'org.mortbay.jetty:jetty-plus:6.1.21'
        test 'org.mortbay.jetty:jetty-util:6.1.21', 'org.mortbay.jetty:jetty-naming:6.1.21'
        test 'hsqldb:hsqldb:1.8.0.10'
    }
}

grails.project.repos.default = "pillarone"

grails.project.dependency.distribution = {
    String password = ""
    String user = ""
    String scpUrl = "dummy"
    try {
        Properties properties = new Properties()
        properties.load(new File("${userHome}/deployInfo.properties").newInputStream())

        user = properties.get("user")
        password = properties.get("password")
        scpUrl = properties.get("url")
    } catch (Throwable t) {
    }
    remoteRepository(id: "pillarone", url: scpUrl) {
        authentication username: user, password: password
    }
}

coverage {
    exclusions = [
            'models/**',
            '**/*Test*',
            '**/com/energizedwork/grails/plugins/jodatime/**',
            '**/grails/util/**',
            '**/org/codehaus/**',
            '**/org/grails/**',
            '**GrailsPlugin**',
            '**TagLib**'
    ]

}
