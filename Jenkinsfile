pipeline {
     agent {
            docker { image 'openjdk:7-jdk' }
        }

    stages {
        stage('Build') {
            steps {
                sh './mvnw compile'
            }
        }
        stage('Test') {
            steps {
                 sh './mvnw test'
            }
        }
    }
}