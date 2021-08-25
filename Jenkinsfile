pipeline {
    agent any
    stages {
        stage ("Build") {
            steps {
                echo "Build"
                sh "mvn clean package"
            }
        }
        stage("Test") {
            steps {
                echo "Test.."
                sh "mvn test"
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying...."
                sh "mvn install"
            }
        }
}
}