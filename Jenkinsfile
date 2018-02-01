@Library('jenkins-shared-libraries')

def REPO_NAME  = 'carlspring/derby-maven-plugin'
def SERVER_ID  = 'carlspring-oss-snapshots'
def SERVER_URL = 'https://dev.carlspring.org/nexus/content/repositories/carlspring-oss-snapshots/'

pipeline {
    agent {
        docker {
            args  '-v /tmp/.m2:/tmp/.m2'
            image 'strongboxci/alpine:jdk8-mvn-3.5'
        }
    }
    options {
        timeout(time: 2, unit: 'HOURS')
        disableConcurrentBuilds()
    }
    stages {
        stage('Building...')
        {
            steps {
                withMaven(maven: 'maven-3.5', mavenSettingsConfig: 'a5452263-40e5-4d71-a5aa-4fc94a0e6833', mavenLocalRepo: '/tmp/.m2')
                {
                    sh "mvn -U clean install -Dmaven.test.failure.ignore=true"
                }
            }
        }
        stage('Code Analysis') {
            steps {
                withMaven(maven: 'maven-3.5', mavenSettingsConfig: 'a5452263-40e5-4d71-a5aa-4fc94a0e6833', mavenLocalRepo: '/tmp/.m2')
                {
                    script {
                        if(env.BRANCH_NAME == 'master') {
                            withSonarQubeEnv('sonar') {
                                // requires SonarQube Scanner for Maven 3.2+
                                sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.4.0.905:sonar"
                            }
                        }
                        else {
                            if(env.BRANCH_NAME.startsWith("PR-"))
                            {
                                withSonarQubeEnv('sonar') {
                                    def PR_NUMBER = env.CHANGE_ID
                                    echo "Triggering sonar analysis in comment-only mode for PR: ${PR_NUMBER}."
                                    sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.4.0.905:sonar" +
                                       " -Psonar-github" +
                                       " -Dsonar.github.repository=${REPO_NAME}" +
                                       " -Dsonar.github.pullRequest=${PR_NUMBER}"
                                }
                            }
                            else
                            {
                                echo "This step is skipped for branches other than master or PR-*"
                            }
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            when {
                expression { BRANCH_NAME == 'master' && (currentBuild.result == null || currentBuild.result == 'SUCCESS') }
            }
            steps {
                script {
                    withMaven(maven: 'maven-3.5', mavenSettingsConfig: 'a5452263-40e5-4d71-a5aa-4fc94a0e6833', mavenLocalRepo: '/tmp/.m2')
                    {
                        sh "mvn deploy -Dmaven.test.skip=true -DaltDeploymentRepository=${SERVER_ID}::default::${SERVER_URL}"
                    }
                }
            }
        }
    }
    post {
        always {
            // Email notification
            script {
                def email = new org.carlspring.jenkins.notification.email.Email()
                if(BRANCH_NAME == 'master') {
                    email.sendNotification()
                } else {
                    email.sendNotification(null, false, null, [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']])
                }
            }
        }
    }
}

