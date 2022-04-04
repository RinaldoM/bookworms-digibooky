pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'jdk-17.0.2'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
    }

    post {
        always {
            publishCoverage adapters: [jacocoAdapter('**/target/site/jacoco/jacoco.xml')]
            junit '**/target/surefire-reports/*.xml'
        }
    }
}