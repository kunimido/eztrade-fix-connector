pipeline {
    agent any

    tools {
        jdk 'JDK 1.8'
        maven 'Maven 3.5.0'
    }

    stages {
        stage('Maven build') {
            steps {
                sh 'mvn -B -V -U clean install'
            }
        }
    }
}