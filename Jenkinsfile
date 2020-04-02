pipeline{
    agent {
        docker {
            image 'openjdk:11-jdk'
            args '--network centos_default'
        }
    }

    stages {
        stage("Build"){
            steps {
                sh "./mvnw compile"
            }
        }

        stage("Test"){
            steps {
                sh "./mvnw test"
            }
        }

        stage("Functional tests :: Staging Latest"){
            steps {
               sh "./mvnw install -Pdeploy,latest"
            }
        }
    }
}