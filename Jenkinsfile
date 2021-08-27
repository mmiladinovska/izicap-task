pipeline {
    agent any
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }
    stages {
        stage ("Git checkout") {
                steps {
                    echo "Git checkout"
                    git 'https://github.com/mmiladinovska/izicap-task.git'
                }
            }
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